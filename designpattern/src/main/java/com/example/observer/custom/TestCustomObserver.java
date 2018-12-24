package com.example.observer.custom;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2017/12/19 0019.
 * 1：定义观察者和被观察者
 * 2：可以分为推送和拉取两种方式获取数据
 * 3：增加控制变量
 *
 */

public class TestCustomObserver {

    public static void main(String[] args) throws Exception {

        ConcreteObservable observable = new ConcreteObservable();
        ConcreteObserver observer = new ConcreteObserver();
        ConcreteObserver observer1 = new ConcreteObserver();

        observable.registerObserver(observer);
        observable.unRegisterObserver(observer);
        observable.registerObserver(observer1);
        observable.notifyObserver("i am a push method  ");

    }



}
