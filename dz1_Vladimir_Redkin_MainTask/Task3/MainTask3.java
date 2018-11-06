package ru.geekbrains.java3.lesson1.Task3;

public class MainTask3 {

    public static void main(String[] args) {
        Apple a1=new Apple("apple",1f);
        Apple a2=new Apple("apple",1f);
        Apple a3=new Apple("apple",1f);
        Orange o1=new Orange("orange",1.5f);
        Orange o2=new Orange("orange",1.5f);
        Orange o3=new Orange("orange",1.5f);
        Box<Apple> box1=new Box("box1",new Apple());
        Box box2=new Box("box2",new Orange()); //  получается, что объект можно объявить и без параметра <Orange>....
        Box box3=new Box("box3",new Apple());
        Box<Apple> box4=new Box("box4",new Orange());


        box1.addFruit(a1);
        box1.addFruit(a2);
        box1.addFruit(a3);

        //box2.addFruit(a1);
        box2.addFruit(o1);
        box2.addFruit(o2);
        box2.addFruit(a3);
        box2.addFruit(o3);

        System.out.println(box1);
        System.out.println(box2);
        System.out.println(box3);
        System.out.println(box4);

        System.out.println("Вес Box1: "+box1.getWeight());
        System.out.println("Вес Box2: "+box2.getWeight());
        System.out.println("Вес Box3: "+box3.getWeight());
        System.out.println("Вес Box4: "+box4.getWeight());


        box3.pour(box1);
        box3.pour(box2);
        box4.pour(box3);

        System.out.println(box1);
        System.out.println(box2);
        System.out.println(box3);
        System.out.println(box4);

        System.out.println("Сравниваем вес");
        System.out.println("Box1 vs Box3: "+box3.compare(box1));

    }
}
