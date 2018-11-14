package ru.geekbrains.java3.Lesson3.client;


import java.io.*;
import java.net.Socket;

public class MainClient {
    final static String IP = "localhost";
    final static int PORT = 1889;



    public static void main(String[] args) {
        try {
            //new NewClient(IP,PORT); // вариант 1 - передеча объекта без использования файла
            new NewClient_2(IP,PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
