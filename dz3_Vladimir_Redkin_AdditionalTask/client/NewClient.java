package ru.geekbrains.java3.Lesson3.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class NewClient {
    String IP ;
    int PORT ;
    DataOutputStream out;
    ObjectInputStream ios;
    ObjectOutputStream oos;
    String fileName="123.txt";

    public NewClient(String IP, int PORT) {
        this.IP = IP;
        this.PORT = PORT;

        System.out.println("Creating object Bob..");
        Students student = new Students(10, "Bob");

        try {
            Socket socket = new Socket(IP, PORT);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ios = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                System.out.println("Sending object");
                oos.writeObject(student);
                student = (Students) ios.readObject();
                student.info();
                break;

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }
}
