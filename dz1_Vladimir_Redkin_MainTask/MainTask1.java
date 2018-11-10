package ru.geekbrains.java3.lesson1;

public class MainTask1 {
    static Object[] arr={1,"dfs"};

    public static void changePos(){
    Object temp;

    if(arr.length<1) {
        System.out.println("Размер массива должен быть больше 1 элемента");
    }
        temp = arr[0];
        arr[0] = arr[1];
        arr[1] = temp;
    }

    public static void main(String[] args) {
        changePos();
        for (int i = 0; i <arr.length ; i++) {
            System.out.println(arr[i]);
        }

    }
}
