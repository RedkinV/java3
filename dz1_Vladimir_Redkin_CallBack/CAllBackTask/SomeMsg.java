package ru.geekbrains.java3.lesson1.CAllBackTask;

public class SomeMsg implements Callback {
   Callback callback;

    public SomeMsg(Callback callback) {
        this.callback = callback;
    }

    public void showMsg(String name){
        callback.callingback(name);
    }

    @Override
    public void callingback(String name) {

    }

}
