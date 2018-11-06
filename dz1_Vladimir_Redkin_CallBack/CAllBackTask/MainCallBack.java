package ru.geekbrains.java3.lesson1.CAllBackTask;

public class MainCallBack {
    public static void main(String[] args) {

        PhoneBox book = new PhoneBox();
        book.add("Ivanov", "123");
        book.add("Ivanov", "124");
        book.add("Ivanov", "125");
        book.add("Petrov", "444");
        book.add("Petrov", "445");
        book.add("Petrov", "446");

        book.findString("Ivanov");
        book.findString("Petrov");
        book.findString("Petrasdovjhk");

    }
}
