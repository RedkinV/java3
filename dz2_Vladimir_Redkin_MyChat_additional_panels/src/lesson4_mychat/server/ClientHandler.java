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


public class ClientHandler {
    private MainServer server;
    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    volatile boolean flag = false;

    public String getNick() {
        return nick;
    }

    String nick;

    public ClientHandler(MainServer server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;

            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

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
                                        System.out.println("/authok "+newNick);
                                        nick = newNick;
                                        server.subscribe(ClientHandler.this);
                                        break;
                                    } else {
                                        sendMsg("Не верный логин/пароль");
                                        System.out.println("Не верный логин/пароль");
                                        break; // тут нужен  для того чтобы закрыть сокет, если не верно ввели пароль,
                                        // а для этого надо обработать команду /end, т.е. надо выйти из цикла
                                    }
                                } else {
                                    sendMsg("Такой пользователь уже в сети");
                                    break; // аналогичная ситуация, что и с неверно введенным паролем
                                }
                            }
                            if (msg.startsWith("/reg")) {
                                String[] tokens = msg.split(" ");
                              if(AuthService.addUser(tokens[1], tokens[2],tokens[3])){
                                  sendMsg("/regok");
                                  System.out.println("/regok");
                                  break;
                              }
                            } else{
                                sendMsg("Не удалось зарегестрировать");
                                System.out.println("Не удалось зарегестрировать");
                                break;
                            }
                        }



                        while (true) {
                            Timer timer;
                            long delay=60000;
//              вариант 2 через встроенный таймер
                            timer=new Timer();
                            System.out.println("........таймер - старт");
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {

                                    System.out.println("Время вышло, отправляю /closePlease");
                                    try {
                                        out.writeUTF("/closePlease");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, delay);

                            String msg = in.readUTF();

                            timer.cancel();
                            System.out.println("........таймер - стоп");

                            if (msg.equals("/end")) {
                                System.out.println("Close command accepted. Server-STOP.");
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
                                sendMsg("Вы добавили " + tokens[1] + " в черный список.");
                            } else if(msg.startsWith("/chnick")){
                                String[] tokens = msg.split(" ");
                                AuthService.changeNick(nick, tokens[1]);
                                nick=tokens[1];
                                sendMsg(nick+" изменен на " + tokens[1]);
                                server.broadcastClientList();
                            }
                            else {
                                System.out.println(nick + ": " + msg);
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

//    public void deleteFromVector() {
//        server.deleteClient(this);
//    }

}
