package ru.geekbrains.java3.Lesson3_mainTask;

import java.io.*;

public class MainTask3 {
    public static void main(String[] args) {

        String fileName = "bigFile.txt";
        // создаем файл
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                int k = 33;
                for (int i = 0; i < 5000; i++) {
                    if (k > 255) k = 33;
                    for (int j = 0; j < 1800; j++) {
                        fileOutputStream.write(k);
                    }
                    k++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else System.out.println("Файл существует.");

        //выводим странички в консоль
        try {
            long t = System.currentTimeMillis();
            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            byte[] bytes = new byte[1800];
            System.out.println("Загрузка за " + (t - System.currentTimeMillis()) + " млсек");



            int i = 0;
            while (i < 100) { //выводим 100 страниц
                if (bufferedInputStream.read(bytes) != -1) {
                    t = System.currentTimeMillis();
                    for (byte b : bytes) {
                        System.out.print((char) b);
                    }
                    System.out.println();
                    System.out.println("Чтение страницы "+(i+1)+ " за " + (t - System.currentTimeMillis()) + " млсек");
                    i++;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
