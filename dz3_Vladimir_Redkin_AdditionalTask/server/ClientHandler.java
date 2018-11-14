package ru.geekbrains.java3.Lesson3.server;

import ru.geekbrains.java3.Lesson3.client.Students;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler {
    Socket socket;
    ServerSocket mainServer;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public ClientHandler(Socket socket, ServerSocket mainServer) {
        this.socket = socket;
        this.mainServer = mainServer;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                        System.out.println("waiting for object...");
                        Students student = (Students)ois.readObject();
                        student.info();
                        student.setName("Mike");
                        System.out.println("sending modified object...");
                        oos.writeObject(student);
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();
    }
}
