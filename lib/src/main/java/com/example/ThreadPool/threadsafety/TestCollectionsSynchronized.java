package com.example.ThreadPool.threadsafety;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jianshengli on 2018/9/9.
 * <p>
 * 多线程发生的原因：
 * 1可见性（内存模型，主存和线程工作内存）
 * 2有序性（编译器和处理器指令重排）
 * 3原子性（线程调度随机性）
 * <p>
 * 多线程安全问题发生的条件：
 * 1共享资源   2多个线程   3复合操作
 * (发生的必要条件)
 * <p>
 * <p>
 * 解决方法（时间和空间角度结合发生条件）
 * 1共享资源
 * 1.1去掉共享资源
 * threadlocal（空间）
 * 局部变量（空间）
 * 克隆（空间）
 * <p>
 * 1.2使共享资源安全
 * 真正不可变对象 事实不可变对象 无状态对象（仅仅提供方法没有属性）（空间）
 * 包装一层（封装）（空间）
 * 使共享对象内部状态线程安全，但是不能解决所有的问题（委托）（空间）
 * 生产消费模式，从时间上隔离开两个线程，实现共享资源转移（时间）
 * 2多个线程
 * 线程封闭，单个线程
 * 多个线程归为一个线程（Android IntentService 把多个线程的请求都放入到Queue中在loop逐个解决）
 * 3复合操作
 * 同步关键字
 * 锁
 * final+构造方法
 *
 *
 * 使用Collections.synchronizedList（）方法后，遍历时可能会出错。
 *
 *
 */

public class TestCollectionsSynchronized {


    //通过Collections.synchronizedList（）方法返回一个包装了ArrayList的List，通过包装对象来保证安全性。（***）
    static List synchronizedList = Collections.synchronizedList(new ArrayList());

    public static void main(String[] args) throws Exception {


        //初始化数据
        for (int i = 0; i < 10000; i++) {
            //线程安全
            synchronizedList.add(i);
        }
        //测试安全删除和安全读取
        //testSafeRead();
        //testSafeRemove();

        //测试删除和读取
        //testRead();
        //testRemove();

        //测试迭代器
        testIteratorRead();
        testIteratorRemove();

        Thread.currentThread().sleep(90000);
        System.out.println("synchronizedList " + synchronizedList.size());//100000 永远都是100000是线程安全的。


    }

    private static void testSafeRead(){
        //读取数据的线程
        new Thread(new SafeReadRunnable()).start();

    }


    private  static void testSafeRemove(){
        //删除数据的线程
        new Thread(new SafeRemoveRunnalbe()).start();


    }

    static class SafeReadRunnable implements Runnable {

        @Override
        public void run() {
            //共享对象时synchronizedList，且存在复合操作和多个线程。
            //在遍历加了锁，防止遍历过程中集合大小发生变化（****）
            synchronized (synchronizedList) {
                for (int i = 0; i < synchronizedList.size(); i++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //线程安全
                    System.out.println("read:" + synchronizedList.get(i));
                }
            }
        }
    }

    static class SafeRemoveRunnalbe implements Runnable {
        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(1000);
                synchronized (synchronizedList) {
                    synchronizedList.clear();
                    System.out.println("remove:" + synchronizedList.size());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }





    /*--------------------------------------非安全的读取------------------------------******
    *
    * 在遍历的过程中，可能会存在集合大小变化，导致遍历失败。
    * java.lang.IndexOutOfBoundsException: Index: 9, Size: 0
	at java.util.ArrayList.rangeCheck(ArrayList.java:653)
	at java.util.ArrayList.get(ArrayList.java:429)
	at java.util.Collections$SynchronizedList.get(Collections.java:2417)
	at com.example.ThreadPool.threadsafety.TestCollectionsSynchronized$ReadRunnable.run(TestCollectionsSynchronized.java:166)
	at java.lang.Thread.run(Thread.java:745)
    *
    *
    *
    *
    * */

    private static void testRead(){
        //读取数据的线程
        new Thread(new ReadRunnable()).start();
        new Thread(new ReadRunnable()).start();
        new Thread(new ReadRunnable()).start();
        new Thread(new ReadRunnable()).start();
        new Thread(new ReadRunnable()).start();
        new Thread(new ReadRunnable()).start();
        new Thread(new ReadRunnable()).start();

    }


    private  static void testRemove(){
        //删除数据的线程
        new Thread(new RemoveRunnalbe()).start();

    }


    static class ReadRunnable implements Runnable {

        @Override
        public void run() {
                 //存在多线程安全问题，若遍历到i=10时，另外一个线程清空了集合，那再执行get时就会报错。
                for (int i = 0; i < synchronizedList.size(); i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //线程安全
                    System.out.println("read:" + synchronizedList.get(i));
                }

        }
    }

    static class RemoveRunnalbe implements Runnable {
        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(10000);
                synchronizedList.clear();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    /*-----------------------------------------测试迭代器---------------------------------*/



    private static void testIteratorRead(){
        //读取数据的线程
        new Thread(new ReadIteratorRunnable()).start();
        new Thread(new ReadIteratorRunnable()).start();
        new Thread(new ReadIteratorRunnable()).start();
        new Thread(new ReadIteratorRunnable()).start();
        new Thread(new ReadIteratorRunnable()).start();
        new Thread(new ReadIteratorRunnable()).start();


    }


    private  static void testIteratorRemove(){
        //删除数据的线程
        new Thread(new RemoveIteratorRunnalbe()).start();

    }

    static class ReadIteratorRunnable implements Runnable {

        @Override
        public void run() {


            /*

    迭代器使用时发生错误：
    java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:903)
	at java.util.ArrayList$Itr.next(ArrayList.java:853)
	at com.example.ThreadPool.threadsafety.TestCollectionsSynchronized$ReadIteratorRunnable.run(TestCollectionsSynchronized.java:227)
	at java.lang.Thread.run(Thread.java:745)*/

            for(Object integer : synchronizedList){

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //线程安全
                System.out.println("read:" + integer);
            }


        }
    }

    static class RemoveIteratorRunnalbe implements Runnable {
        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(10000);
                synchronizedList.clear();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }









}
