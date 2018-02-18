package com.example.ThreadPool.threadsafety;


import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Administrator on 2017/12/19 0019.
 *
 * 线程安全与否，要看同步代码块的范围是否能满足我们当下的需求。
 *
 * 1： 线程安全是站在一个代码块范围内来看的，在小范围内的安全，放入到大范围内不一定是安全的。
 * 2： 线程安全就是在单个线程坏境下执行完的结果应该是和在多个线程环境下执行完的结果是一致的。（*******）
 * 3： 线程的安全是通过锁控制的，安全与否主要分析锁的控制范围。
 *
 线程安全问题产生的原因：
 1，多个线程在操作共享的数据。
 2，操作共享数据的线程代码有多条。（可能其中某条是线程安全的，但是整体上依然不是安全的）

 */

public class TestList {
    static ArrayList list = new ArrayList();
    static Vector<Integer> vector = new Vector<>();
    static Vector<Integer> vectorSafety = new Vector<>();
    static Vector<Integer> vectorSafetySynchronized = new Vector<>();
    static ArrayList listSynchronized = new ArrayList();

    public static void main(String[] args) throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                testArrayList();
                testVector();
                testVectorSafety();
                testVectorSynchronized();
            }
        };
        Thread t;
        for(int i=0;i<1000;i++){
            t = new Thread(runnable);
            t.start();
        }

       Thread.currentThread().sleep(9000);
        //单步的测试
        System.out.println("list  "+list.size());//9897 可以看出不是10000所以是存在线程安全的。
        System.out.println("vector "+vector.size());//10000 永远都是10000是线程安全的。

        //多步测试
        System.out.println("vectorSafety "+vectorSafety.size());
        System.out.println("vectorSafetySynchronized "+vectorSafetySynchronized.size());
        System.out.println("listSynchronized "+listSynchronized.size());

/*
        日志分析：
        list  99903
        vector 100000
        vectorSafety 689
        vectorSafetySynchronized 1
        listSynchronized 1

        单独使用List/Vector的add方法时，List是非线程安全的，但是Vector是线程安全的。

        除了调用List/Vector的add方法还附带了额外的代码，是不能保证整个代码块是线程安全的，仅仅能保证Vector的add代码块是线程安全的，
        此时如果要整个代码块是线程安全的，就需要使用synchronized关键字。

*/



    }


    static public void testArrayList(){
        for(int i=0;i<100;i++){
            //非线程安全
            list.add(1);
        }

    }

    static public void testVector(){
        for(int i=0;i<100;i++){
            //线程安全
            vector.add(1);
        }

    }


    /*

    * 整个代码块不是线程安全的，但是vectorSafety.addElement(1);是大范围内的一个局部的同步块。
    *
    * 对于Vector的addElement（）方法代码块而言是线程安全的，但是对于TestList的testVectorSafety（）方法
    * 代码块而言是非线程安全的，因为多个线程同时调用的时候会存在重复添加数据的问题的，破坏了重复的数据的只添加一次的逻辑，
    * 所以说TestList的testVectorSafety（）方法是线程不安全的。
    *
    * 线程安全就是在单个线程执行完的结果应该是和在多个线程环境下执行完的结果是一致的。（*******）
    *
    * */
    static public void testVectorSafety(){
        int i=0;
        if(i==0){
            if (!vectorSafety.contains(1)) {
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //小范围的同步安全块
                vectorSafety.addElement(1);
            }
        }


    }

    /*
    * 整个代码是线程安全的代码块。
    * */
    static synchronized public void testVectorSynchronized(){
        int i=0;
        if(i==0){
            if (!vectorSafetySynchronized.contains(1)) {
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                vectorSafetySynchronized.addElement(1);
                listSynchronized.add(1);
            }
        }


    }


}
