package com.example.observer.custom;

import java.util.Vector;

/**
 * Created by Administrator on 2018/1/2 0002.
 * 1: 提供集合存储观察者
 * 2：注意线程安全问题
 * 3: 在同步代码块里面不要调用Observer的方法。
 * 4：实现方式借鉴了java.util包下的Observable的实现方式。
 */

public class ConcreteObservable implements Observable {
    private Vector<Observer> observers;

    public ConcreteObservable() {
        observers = new Vector<>();
    }

    @Override
    public synchronized void registerObserver(Observer observer) {
        if (observer == null)
            throw new NullPointerException();
        if (!observers.contains(observer)) {
            observers.addElement(observer);
        }

    }

    @Override
    public synchronized void unRegisterObserver(Observer observer) {
        observers.removeElement(observer);
    }

    @Override
    public synchronized void unRegisterAllObserver() {
        observers.removeAllElements();
    }

    @Override
    public void notifyObserver() {
        notifyObserver(null);
    }


    /**
     * 1：多线程的安全问题：加synchronized是为了安全问题。
     * 2：多线程的性能问题：synchronized控制的范围大小是性能问题。
     */
    @Override
    public void notifyObserver(Object args) {
        //构建一个临时变量，存放要被通知的观察者
        Object[] arrLocal;
        synchronized (this) {
            /*我们从observers中提取的每一个观察者的代码需要同步，但是通知观察者不应该同步。（******）
             */
            arrLocal = observers.toArray();

        }
        //遍历尽量使用i--
        for (int i = arrLocal.length - 1; i >= 0; i--)
            ((Observer) arrLocal[i]).update(this, args);
    }


}
