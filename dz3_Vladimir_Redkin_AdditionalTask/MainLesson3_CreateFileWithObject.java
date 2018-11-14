package ru.geekbrains.java3.Lesson3;
import ru.geekbrains.java3.Lesson3.client.Students;

import java.io.*;

public class MainLesson3_CreateFileWithObject {
    public static void main(String[] args) {
        String fileName="123.txt";
        Students student =new Students(19,"Boby");

        try {
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(student);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }




    }
}
