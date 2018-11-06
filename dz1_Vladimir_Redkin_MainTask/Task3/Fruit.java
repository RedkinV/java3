package ru.geekbrains.java3.lesson1.Task3;

public class Fruit {
    private String name;
    private float weight;

    public Fruit(String name, float weight) {
        this.name = name;
        this.weight = weight;
    }
    public Fruit(){

    }

    public float getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return  name + ", " + weight ;
    }
}
