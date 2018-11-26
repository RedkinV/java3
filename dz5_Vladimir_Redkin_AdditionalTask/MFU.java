package ru.geekbrains.java3.Lesson5MFU;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class MFU {

    ReentrantLock printLock1 = new ReentrantLock();
    ReentrantLock scanLock1 = new ReentrantLock();
    ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<>(2);

    public void print(String doc, int n) {
        if (printLock1.tryLock()) {
            try {
                abq.put(doc);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (!abq.isEmpty()) {
                String str=abq.remove();
                System.out.print("Начало печати " + str + "...");
                for (int i = 0; i < 10; i++) {
                    System.out.print("p");
                }
                System.out.println();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Конец печати " + str);
            }
            printLock1.unlock();
        } else {
            try {
                abq.put(doc);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public void scan(String doc, int n) {
        scanLock1.lock();
        System.out.print("Начало сканирования " + doc+"...");
        for (int i = 0; i < 10; i++) {
            System.out.print("s");
        }
        System.out.println();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Конец сканирования " + doc);
        scanLock1.unlock();
    }

    public static void main(String[] args) {
        MFU mfu = new MFU();

        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                int finalI = i;
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        mfu.print("Doc" + finalI, finalI);
                    }
                });
            } else {
                int finalI = i;
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        mfu.scan("Doc" + finalI, finalI);
                    }
                });

            }
        }

        pool.shutdown();
    }

}