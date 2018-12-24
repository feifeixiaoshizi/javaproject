package com.example.observer.custom;

/**
 * Created by Administrator on 2018/1/2 0002.
 * 1：注册观察者
 * 2：取消观察者
 * 3: 通知观察者
 */

public interface Observable {

     void registerObserver(Observer observer);

     void unRegisterObserver(Observer observer);
     void unRegisterAllObserver();

     void notifyObserver();
     void notifyObserver(Object args);

}
