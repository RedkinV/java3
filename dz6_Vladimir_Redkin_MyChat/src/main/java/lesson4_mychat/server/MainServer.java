package lesson4_mychat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;
import org.apache.log4j.Logger;

public class MainServer {
    private Vector<ClientHandler> clients;
    private volatile LinkedList<String> savedMsgsLinkedList = null;
    Timer timer;
    long delay = 5000;
    long period = 50000;
    Logger logger;

    public MainServer() {
        final String IP = "localhost";
        final int PORT = 1889;
        ServerSocket server = null;
        Socket socket = null;
        clients = new Vector<ClientHandler>();
        savedMsgsLinkedList = new LinkedList<String>();
        timer = new Timer();
        logger=Logger.getLogger("");



        try {
            AuthService.connect();
            server = new ServerSocket(PORT);
            //System.out.println("Server started.");
            logger.info("Server started.");

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!clients.isEmpty() || !savedMsgsLinkedList.isEmpty()) {
                        saveMsgsToDB();
                        //System.out.println("Time is up...saving to DB");
                        logger.info("Time is up...saving to DB");
                    }
                }
            }, delay, period);

            while (true) {
                socket = server.accept();
                //System.out.println("Client connected.");
                logger.info("Client connected.");
                new ClientHandler(this, socket);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    public boolean isInBlackList(String nick, ArrayList<String> blacklist) {
        for (String s : blacklist) {
            if (nick.equals(s)) return true;
        }
        return false;
    }

    public void broadCastMsg(String msg, ClientHandler c) {

        if (c != null) {
            ArrayList<String> blacklist = AuthService.getBlacklistByNick(c.getNick());
            for (ClientHandler o : clients) {

                if (!isInBlackList(o.getNick(), blacklist)) {
                    o.sendMsg(msg);
                }
            }
        } else {
            for (ClientHandler o : clients) {
                o.sendMsg(msg);
            }
        }
    }

    public void saveMsg(String msg) { // сделал этот метод для дальнейшей обработки сообщения. Задел на будущее, так сказать
        savedMsgsLinkedList.add(msg);
    }

    public void saveMsgsToDB() {
        AuthService.saveMsgsToDB(savedMsgsLinkedList);
        savedMsgsLinkedList.clear();
    }

    public void retrieveAndSendMsgsFromDB(ClientHandler clH) {
        LinkedList<String> strLL;
        strLL = AuthService.retrieveMsgs();

        Iterator it = savedMsgsLinkedList.iterator();
        while (it.hasNext()) {
            strLL.addLast((String) it.next());
        }

        it = strLL.iterator();
        while (it.hasNext()) {
            clH.sendMsg((String) it.next());
        }
    }


    public void sendPrivateMsg(ClientHandler clHnd, String nickTo, String msg) {

        ArrayList<String> blacklist = AuthService.getBlacklistByNick(nickTo);
        for (ClientHandler c : clients) {
            if (c.nick.equals(nickTo)) {
                if (!isInBlackList(clHnd.getNick(), blacklist)) {
                    c.sendMsg(msg);
                    clHnd.sendMsg(msg);
                    return;
                } else {
                    clHnd.sendMsg("You are banned.");
                    return;
                }
            }
        }
        clHnd.sendMsg("No such nick in the chat");
    }


    public void subscribe(ClientHandler c) {
        clients.add(c);
        broadcastClientList();
        retrieveAndSendMsgsFromDB(c);
        logger.info("Client "+c.getNick()+" subscribed.");
    }

    public void unsubscribe(ClientHandler c) {
        clients.remove(c);
        broadcastClientList();
        if (clients.isEmpty() && !savedMsgsLinkedList.isEmpty()) saveMsgsToDB();
        logger.info("Client "+c.getNick()+" unsubscribed.");
    }

    public boolean haskNick(String chNick) {
        for (ClientHandler c : clients) {
            if (c.nick.equals(chNick)) return true;
        }
        return false;
    }

    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientList ");
        for (ClientHandler c : clients) {
            sb.append(c.getNick() + " ");
        }
        String str = sb.toString();
        broadCastMsg(str, null);
    }


}

