package ru.geekbrains.java3.lesson1.Task3;

import java.util.ArrayList;

public class Box <F extends Fruit>{

    private ArrayList<F> elements;

    public String getBoxName() {
        return boxName;
    }

    private String boxName;
    private String fName;

    public String getfName() {
        return fName;
    }

    public Box(String boxName, F fName) {
        this.boxName= boxName;
        elements=new ArrayList<>();
        this.fName=fName.getClass().getSimpleName();
    }


    public float getWeight(){
        float weight=0;

        for (F f: elements) {
            weight+=f.getWeight();
        }
        return weight;
    }
    public void addFruit (F f){
        if (f.getClass().getSimpleName().equals(fName)) elements.add(f);
        else {
            System.out.print("В коробку "+boxName+" положить этот фрукт нельзя->   ");
            System.out.print(boxName+" type: "+ fName);
            System.out.println("    Fruit type: "+f.getClass().getSimpleName());
        }


//        if (elements.isEmpty()) elements.add(f);
//        else {
//            if(f.getClass().equals(elements.get(0).getClass())) elements.add(f);
//            else System.out.println("Не путайте яблоки с апельсинамию");
//        }
    }

    @Override
    public String toString() {
        return boxName + " " + elements ;
    }
    public boolean compare(Box newBox){
        float w1=this.getWeight();
        float w2=newBox.getWeight();
        if (w1==w2) return true;
        return false;
    }

    public ArrayList<F> getElements() {
        return elements;
    }


    public void pour (Box<? extends Fruit> newBox){

        System.out.print("Пересыпаем  "+newBox.getBoxName()+" в "+boxName+"...");
        if(newBox.getfName().equals(fName)) {
            for (Fruit f: newBox.getElements()) {
                elements.add((F) f);
            }
            newBox.getElements().removeAll(newBox.getElements());
            System.out.println("...ок.");
        } else {
            System.out.println("В "+boxName+" пересыпать "+newBox.getfName()+" из коробки "+newBox.getBoxName() +" нельзя.");
        }

//        if(this.elements.isEmpty()) {
//            for (Fruit f:newBox.getElements()) {
//                elements.add((F)f);
//            }
//
//            newBox.getElements().removeAll(newBox.getElements());
//        }
//        if (newBox.getElements().isEmpty()) return;
//        if(elements.get(0).getClass().equals(newBox.getElements().get(0).getClass())){
//            for (Fruit f: newBox.getElements()) {
//                elements.add((F) f);
//            }
//            newBox.getElements().removeAll(newBox.getElements());
//        } else System.out.println("Нельзя пересыпать яблоки в апльсины и наоборот");
    }

}
