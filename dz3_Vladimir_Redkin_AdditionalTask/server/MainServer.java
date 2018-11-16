package ru.geekbrains.java3.Lesson3.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    final String IP = "localhost";
    final static int PORT = 1889;
    static ServerSocket server = null;
    static Socket socket;


    public static void main(String[] args) {

        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started.");

            while (true) {
                socket = server.accept();
                System.out.println("Client connected.");
                //new ClientHandler(socket,server); // прием объекта без использования файла
                new ClientHandler_2(socket,server);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }





    }
}
