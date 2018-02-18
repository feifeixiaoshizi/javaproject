package com.example.observer.custom;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class ConcreteObserver implements Observer {
    @Override
    public void update(Observable observable, Object arg) {
        System.out.println("custom   "+ arg);
    }
}
