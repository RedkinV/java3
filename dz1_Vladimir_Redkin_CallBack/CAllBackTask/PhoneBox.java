package ru.geekbrains.java3.lesson1.CAllBackTask;

import java.util.HashMap;
import java.util.HashSet;

public class PhoneBox implements  Callback {
    HashMap<String, HashSet<String>> hm;
    SomeMsg someMsg;

    public PhoneBox() {
        this.hm = new HashMap<>();
        someMsg=new SomeMsg(this);
    }

    public void add(String name, String phone) {
        HashSet<String> hs = hm.getOrDefault(name, new HashSet<>());
        hs.add(phone);
        hm.put(name, hs);
        someMsg.showMsg(name);
    }

    public void findString(String name) {
        if (hm.containsKey(name)) {
            System.out.println(hm.get(name));
        } else {
            System.out.println("такой фамилии нет");
        }
    }

    @Override
    public void callingback(String name) {
        System.out.println("Запись добавлена "+name);
    }
}
