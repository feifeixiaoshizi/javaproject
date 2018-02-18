package com.example.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2017/12/19 0019.
 * 1：定义观察者和被观察者
 * 2：可以分为推送和拉取两种方式获取数据
 * 3：增加控制变量
 */

public class ObserverNative {

    public static void main(String[] args) throws Exception {

        MyObservable observable = new MyObservable();
        Observer observer = new MyObserver();

        observable.addObserver(observer);
        //先调用setChanged设置改变了的标记，才能通知观察者。
        observable.setChanged();
        observable.notifyObservers("i am a push method  ");
        //每次都要先改变标记再执行通知方法。
        observable.setChanged();
        observable.notifyObservers();

    }

    static class  MyObservable extends Observable{
        @Override
        protected synchronized void setChanged() {
            super.setChanged();
        }

        public synchronized String provide(){
            return "i am a get  method ";
        }

    }

    static class MyObserver implements Observer{

        @Override
        public void update(Observable o, Object arg) {
            if(arg!=null){
                System.out.println("o "+o.toString()+"arg  "+arg.toString());
                System.out.println();

            }else {
                get(o);
            }
        }

        private void get(Observable o){
            if (o instanceof MyObservable){
                String get = ((MyObservable) o).provide();
                System.out.println("get:"+get);
            }
        }



    }

}
