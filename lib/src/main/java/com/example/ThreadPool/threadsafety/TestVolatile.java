package com.example.ThreadPool.threadsafety;

import com.example.ThreadPool.Main;

/**
 * Created by jianshengli on 2018/9/6.
 */

public class TestVolatile {

    static boolean ready = true;  //volatile 状态标志变量
    static int count = 1;


    private final static int SIZE = 10000; //创建10个对象，可改变

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < SIZE; i++) {
            test();
        }

        new Thread(){

            @Override
            public void run() {
                ready = false;
                count = 34;

            }
        }.start();

        System.out.println("mainThread end");//调用结束打印，死循环时不打印

    }


    public static void test() throws InterruptedException {
        Thread t2 = new Thread() {

            public void run() {
                while (ready) {

                    System.out.println("thread name :" + Thread.currentThread().getName() + "count:" + count);

                }
                if(!ready){
                    System.out.println("thread name-------> :" + Thread.currentThread().getName() + "ready" + ready);
                }

            }
        };

        t2.start();

    }

}


