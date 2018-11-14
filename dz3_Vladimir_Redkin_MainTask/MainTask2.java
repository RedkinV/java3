package ru.geekbrains.java3.Lesson3_mainTask;

import java.io.*;
import java.util.*;


public class MainTask2 {
    public static void main(String[] args) throws IOException {
        //создаем файлы
        String[] fileName={"toJoin_1.txt","toJoin_2.txt","toJoin_3.txt","toJoin_4.txt","toJoin_5.txt"};
        ArrayList<FileOutputStream> alfos=new ArrayList<>();

        for (String s:fileName) {
            alfos.add(new FileOutputStream(s));
        }

        int k=97;// буква a
        for (FileOutputStream fos:alfos) {
            for (int i = 0; i <100 ; i++) {
            fos.write(k);
            }
            k++;
        }

        //объединяем файлы
        ArrayList<InputStream> alfis=new ArrayList<>();
        for (String s:fileName) {
            alfis.add(new FileInputStream(s));
        }
        Enumeration<InputStream> e =  Collections.enumeration(alfis);

        FileOutputStream fosJoined=new FileOutputStream("Joined_1-5.txt");
        SequenceInputStream sequenceInputStream=new SequenceInputStream(e);

        int i;
        while((i=sequenceInputStream.read())!=-1){
            fosJoined.write(i);
        }

    }
}
