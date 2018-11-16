package ru.geekbrains.java3.Lesson3.client;

import java.io.Serializable;

public class Students implements Serializable {
    private int age;
    private String name;

    public Students(int age, String name) {
        this.age = age;
        this.name = name;
    }
    public void info(){
        System.out.println("name= "+name+" age= "+age);
    }

    public void setName(String name) {
        this.name = name;
    }
}
