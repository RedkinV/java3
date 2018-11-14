package lesson4_mychat.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class AuthService {
    public static Connection connection;
    public static Statement statement;

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC"); // непонятно, что делает команда и что и кому она передает???
        connection = DriverManager.getConnection("jdbc:sqlite:myDB.db");
        statement = connection.createStatement();
    }


    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String password) {
        String sql = String.format("SELECT nickname FROM USERS WHERE login='%s' AND password='%s'", login, password.hashCode());
        try {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) return rs.getString(1); // можно вызвать вместо 1 "nickname"
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addUser(String login, String password, String nick) {
        String sql = String.format("INSERT INTO USERS (login, password, nickname) VALUES ('%s','%s','%s')", login, password.hashCode(), nick);
//        try {
//            if (statement.executeUpdate(sql) > 0) return true;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            System.out.println("Auth addUser method...");
            PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO USERS (login, password, nickname) VALUES (?,?,?)");
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,nick);
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return true;
    }

    public static ArrayList<String> getBlacklistByNick(String nick) {
        ArrayList<String> arrayList = null;
        String sql = String.format("SELECT t1.nickname, t2.blacklist FROM USERS t1 JOIN BLACKLIST t2 ON t1.nickname=t2.nickname WHERE t1.nickname='%s'", nick);
        try {
            ResultSet rs = statement.executeQuery(sql);
            arrayList = new ArrayList<>();
            while (rs.next()) {
                arrayList.add(rs.getString(2)); // можно вызвать вместо 2 "blacklist"
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void insertInBlacklist(String fromNick, String blNick) {
        String sql = String.format("INSERT INTO BLACKLIST (nickname, blacklist) VALUES ('%s','%s')", fromNick, blNick);
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void saveMsgsToDB(LinkedList<String> msgLList) {
        System.out.print("Adding msgs to DB...");
        try {
            connection.setAutoCommit(false);
            for (String s : msgLList) {
                statement.execute(String.format("INSERT INTO messages (message) VALUES ('%s')", s));
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("done.");

    }
    public static LinkedList<String> retrieveMsgs(){
        LinkedList<String> msgLList=new LinkedList<>();
        try {
            ResultSet rs=statement.executeQuery("SELECT * FROM messages");
            while(rs.next()){
                msgLList.add(rs.getString("message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
     return msgLList;
    }

    public static void changeNick(String nick, String newNick) {
        String sql = String.format("UPDATE users SET nickname='%s' WHERE nickname='%s'", newNick, nick);
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
