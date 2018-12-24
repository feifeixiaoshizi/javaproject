package com.example.ThreadPool.threadsafety;

/**
 * Created by Administrator on 2018/1/31 0031.
 * ThreadLocal
 * 1首先定义共享变量
 * 2然后再把共享变量放入到ThreadLocal中
 *
 */

public class ThreadLocalTest {

    private static  int count;//共享变量

    public static void main(String[] args) throws Exception {

        Thread t;
        Thread threadLocal;
        for(int i=0;i<5;i++){
            t = new Thread(new TestRunnable());
            t.start();

            threadLocal = new Thread(new TheadLocalRunnable());
            threadLocal.start();
        }



    }

    static class TestRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println("ThreadName "+Thread.currentThread().getName()+" count "+count);
            count++;
        }
    }


    static class TheadLocalRunnable implements Runnable{
        //通过ThreadLocal给每个线程单独设置变量值。（*****）把直接共享的变量count变为共享的ThreadLocal对象，来间接实现共享count。
        //ThreadLocal封装了共享变量count，负责了每个线程都使用自己的count变量，实现了线程安全。
        ThreadLocal threadLocal = new ThreadLocal();
        @Override
        public void run() {
            threadLocal.set(count);
            System.out.println("ThreadName "+Thread.currentThread().getName()+" count "+threadLocal.get());
            int count1 = (int) threadLocal.get();
            count1++;
            threadLocal.set(count1);
            System.out.println("ThreadName "+Thread.currentThread().getName()+" count "+threadLocal.get());
            System.out.println("-------------------------------------------------------------------------- ");
        }
    }





}
