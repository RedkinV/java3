package ru.geekbrains.java3.Lesson4;

public class MainTreads {
    static volatile String currentLetter = "A";
    static Object o = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                printA();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                printB();
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                printC();
            }
        });


        t1.start();
        t2.start();
        t3.start();
    }

    public static void printA() {
        synchronized (o) {
            for (int i = 0; i < 5; i++) {
                while (!currentLetter.equals("A")) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(currentLetter);
                currentLetter = "B";
                o.notifyAll();
            }
        }
    }

    public static void printB() {
        synchronized (o) {
            for (int i = 0; i < 5; i++) {
                while (!currentLetter.equals("B")) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(currentLetter);
                currentLetter = "C";
                o.notifyAll();
            }
        }
    }

    public static void printC() {
        synchronized (o) {
            for (int i = 0; i < 5; i++) {
                while (!currentLetter.equals("C")) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(currentLetter);
                currentLetter = "A";
                o.notifyAll();
            }
        }
    }
}
