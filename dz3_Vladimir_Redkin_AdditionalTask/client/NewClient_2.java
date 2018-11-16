package ru.geekbrains.java3.Lesson3.client;

import java.io.*;
import java.net.Socket;

public class NewClient_2 {
    String IP;
    int PORT;
    DataOutputStream out;
    ObjectInputStream ios;
    ObjectOutputStream oos;
    String fileName = "123.txt";


    public NewClient_2(String IP, int PORT) throws IOException {
        this.IP = IP;
        this.PORT = PORT;
        Socket socket = new Socket(IP, PORT);

        FileInputStream fileInputStream = new FileInputStream("123.txt");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName));
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        DataOutputStream dataOutputStreamMsg = new DataOutputStream(socket.getOutputStream());

        byte[] bytes;
        int i;

        while (true) {
            bytes = new byte[10];
            if ((i = fileInputStream.read(bytes)) != -1) {
                dataOutputStreamMsg.writeUTF("/sending");
                System.out.println("sending..." + i+" bytes");
                bufferedInputStream.read();
                dataOutputStream.write(bytes);
                for (byte b : bytes) {
                    System.out.print((char) b);
                }

            } else {
                dataOutputStreamMsg.writeUTF("/end");
                break;
            }
        }

    }
}
