package ru.geekbrains.java3.Lesson5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier cb;
    private Object startLock;
    private CountDownLatch cdl;

    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CyclicBarrier cb, CountDownLatch cdl) {
        this.race = race;
        this.speed = speed;
        this.cb=cb;
        this.cdl=cdl;

        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {

            try {
                System.out.println(this.name + " готовится");
                Thread.sleep(500 + (int) (Math.random() * 800));
                System.out.println(this.name + " готов");
                cb.await();
                cb.await();

            } catch (Exception e) {
                e.printStackTrace();
            }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        cdl.countDown();
        if (cdl.getCount()==3){
            System.out.println(this.name+" Win!");
        }
    }
}
