package ru.geekbrains.java3.Lesson3.server;

import ru.geekbrains.java3.Lesson3.client.Students;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler_2 {
    Socket socket;
    ServerSocket mainServer;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    String fileName = "123_serv.txt";
    DataInputStream dis;

    public ClientHandler_2(Socket socket, ServerSocket mainServer) throws IOException {
        this.socket = socket;
        this.mainServer = mainServer;

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataInputStream dataInputStreamMsg = new DataInputStream(socket.getInputStream());
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());

        byte[] bytes = new byte[10];

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i;
                    while (true) {
                        String msg=dataInputStreamMsg.readUTF();
                        if (msg.equals("/sending")){
                        i = dataInputStream.read(bytes);
                            System.out.println("received bytes..." + i);
                            fileOutputStream.write(bytes);
                            for (byte b : bytes) {
                                System.out.print((char) b);
                            }
                        }
                        if (msg.equals("/end")){
                            System.out.println("/end received");
                            break;
                        }
                    }
                    ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("123_serv.txt"));
                    Students st=(Students)objectInputStream.readObject();
                    st.info();

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
}
