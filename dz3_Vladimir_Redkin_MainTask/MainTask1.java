package ru.geekbrains.java3.Lesson3_mainTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainTask1 {
    public static void main(String[] args) throws IOException {
        String fileName="50b.txt";
        File file=new File (fileName);
        if(!file.exists()){
            FileOutputStream fos=new FileOutputStream(fileName);
            for (int i = 0; i <50 ; i++) {
                fos.write(97+(i/10));
            }
        }



        FileInputStream fileInputStream=new FileInputStream("50b.txt");

        int i;
        byte[] bytes=new byte[10];

        while ((i=fileInputStream.read(bytes))!=-1){
            for (byte b:bytes) {
                System.out.print((char)b);
            }
         bytes=new byte[10];
        }
    }
}
