package ru.geekbrains.java3.lesson1;

import java.util.ArrayList;

public class MainTask2 {
static Object[] objArr={1,2,"aaa","bbb"};
static ArrayList<Object> arrayList;

public static void toArrayList(){
    arrayList=new ArrayList<>();
    for (int i = 0; i <objArr.length ; i++) {
        arrayList.add(objArr[i]);
    }
}

    public static void main(String[] args) {
        toArrayList();
        System.out.println(arrayList);
    }
}
