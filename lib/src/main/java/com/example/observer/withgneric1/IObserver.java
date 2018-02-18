package com.example.observer.withgneric1;

/**
 * Created by Administrator on 2018/1/2 0002.
 *
 * 观察者的通用接口，被观察者在变化的时候会调用的观察者的方法。
 *
 */

public interface IObserver {

    public void update(Observable observable, Object arg);

}
