package lesson4_mychat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;


public class ClientHandler {
    private MainServer server;
    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    volatile boolean flag = false;
    Logger logger;

    public String getNick() {
        return nick;
    }

    String nick;

    public ClientHandler(final MainServer server, final Socket socket) {
        try {
            this.server = server;
            this.socket = socket;

            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            logger=Logger.getLogger("");

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        while (true) {
                            String msg = in.readUTF();
                            if (msg.startsWith("/auth")) {
                                String[] tokens = msg.split(" ");
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (!server.haskNick(newNick)) {
                                    if (newNick != null) {
                                        sendMsg("/authok "+newNick);
                                        //System.out.println("/authok "+newNick);
                                        logger.info("/authok "+newNick);
                                        nick = newNick;
                                        server.subscribe(ClientHandler.this);
                                        break;
                                    } else {
                                        sendMsg("Wrong logging/password");
                                        //System.out.println("Wrong logging/password");
                                        logger.warn("Wrong logging/password");
                                        break; // тут нужен  для того чтобы закрыть сокет, если не верно ввели пароль,
                                        // а для этого надо обработать команду /end, т.е. надо выйти из цикла
                                    }
                                } else {
                                    sendMsg("This user is in logged already.");
                                    break; // аналогичная ситуация, что и с неверно введенным паролем
                                }
                            }
                            if (msg.startsWith("/reg")) {
                                String[] tokens = msg.split(" ");
                              if(AuthService.addUser(tokens[1], tokens[2],tokens[3])){
                                  sendMsg("/regok");
                                  //System.out.println("/regok");
                                  logger.info("/regok");
                                  break;
                              }
                            } else{
                                sendMsg("Registration failed.");
                                //System.out.println("Registration failed.");
                                logger.warn("Registration failed.");
                                break;
                            }
                        }



                        while (true) {
                            Timer timer;
                            long delay=60000;
                            timer=new Timer();
                            System.out.println("........timer - start");
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {

                                    System.out.println("Time is up, sending /closePlease");
                                    try {
                                        out.writeUTF("/closePlease");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, delay);

                            String msg = in.readUTF();

                            timer.cancel();
                            System.out.println("........timer - stop");

                            if (msg.equals("/end")) {
                                //System.out.println("Close command accepted. Server-STOP.");
                                logger.info("Close command accepted. Server-STOP.");
                                out.writeUTF("/serverClosed");
                                server.unsubscribe(ClientHandler.this);
                                break;
                            }
                            if (msg.startsWith("/w")) {
                                String pattern = ".*\\s(\\w+)\\s(.*)";
                                Pattern p = Pattern.compile(pattern);
                                Matcher m = p.matcher(msg);
                                if (m.find())
                                    server.sendPrivateMsg(ClientHandler.this, m.group(1), nick + ": " + m.group(2));
                            } else if (msg.startsWith("/blacklist")) {
                                String[] tokens = msg.split(" ");
                                AuthService.insertInBlacklist(nick, tokens[1]);
                                sendMsg("You added " + tokens[1] + " to the blacklist.");
                            } else if(msg.startsWith("/chnick")){
                                String[] tokens = msg.split(" ");
                                AuthService.changeNick(nick, tokens[1]);
                                nick=tokens[1];
                                sendMsg(nick+" is changed to " + tokens[1]);
                                server.broadcastClientList();
                            }
                            else {
                                //System.out.println(nick + ": " + msg);
                                logger.info(nick + "sent a msg..");
                                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                Date date = new Date();

                                String s1=nick + " " + dateFormat.format(date) + ": " + msg;
                                server.saveMsg(s1);
                                server.broadCastMsg(s1, ClientHandler.this);
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                            out.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
