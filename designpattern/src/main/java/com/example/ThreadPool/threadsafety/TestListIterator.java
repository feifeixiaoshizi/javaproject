package com.example.ThreadPool.threadsafety;


import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Administrator on 2017/12/19 0019.
 * <p>
 * 线程安全与否，要看同步代码块的范围是否能满足我们当下的需求。
 * <p>
 * 1： 线程安全是站在一个代码块范围内来看的，在小范围内的安全，放入到大范围内不一定是安全的。
 * 2： 线程安全就是在单个线程坏境下执行完的结果应该是和在多个线程环境下执行完的结果是一致的。（*******）
 * 3： 线程的安全是通过锁控制的，安全与否主要分析锁的控制范围。
 * <p>
 * 线程安全问题产生的原因：
 * 1，多个线程在操作共享的数据。
 * 2，操作共享数据的线程代码有多条。（可能其中某条是线程安全的，但是整体上依然不是安全的）
 *
 *
 * 多线程操作集合和迭代器的问题：
 * 考虑点：1：多个线程仅仅遍历 2：多个线程遍历过程对集合中的元素进行修改 3：多个线程遍历过程中其中有线程对集合进行修改。
 * 4：在遍历过程中修改了引用变量指向对象
 *
 * 1：两个线程可以同时使用迭代器访问集合中的数据
 * 2：两个线程可以同时使用迭代器访问集合中的数据，并且可以修改遍历到的元素。
 * 3：一个线程使用迭代器访问的时候，另外一个线程对集合进行修改就会造成ConcurrentModificationException问题，
 * 可以通过同步锁来控制。
 * 4：引用变量修改后不影响遍历，但是引用指向的内存区内容发生变化后就会影响遍历。
 *
 *
 *
 *
 */

public class TestListIterator {
    static ArrayList<String> list = new ArrayList();
    static ArrayList<String> list1 = new ArrayList();
    static Vector<Integer> vector = new Vector<>();
    static TestIteratorRunnable testIteratorRunnable = new TestIteratorRunnable();
    static TestIteratorUpdateRunnable testIteratorUpdateRunnable = new TestIteratorUpdateRunnable();

    public static void main(String[] args) throws Exception {
        initList();
        Thread t;

        for (int i = 0; i < 3; i++) {
            t = new Thread(testIteratorUpdateRunnable);
            t.start();
        }
        //newThreadToAdd();

    }


    /**
     * Exception in thread "Thread-0" Exception in thread "Thread-2" Exception in thread "Thread-1" java.util.ConcurrentModificationException
     * at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:903)
     * at java.util.ArrayList$Itr.next(ArrayList.java:853)
     * at com.example.ThreadPool.threadsafety.TestListIterator$TestIteratorRunnable.run(TestListIterator.java:58)
     * at java.lang.Thread.run(Thread.java:745)
     * java.util.ConcurrentModificationException
     * at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:903)
     * at java.util.ArrayList$Itr.next(ArrayList.java:853)
     * at com.example.ThreadPool.threadsafety.TestListIterator$TestIteratorRunnable.run(TestListIterator.java:58)
     * at java.lang.Thread.run(Thread.java:745)
     * java.util.ConcurrentModificationException
     * at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:903)
     * at java.util.ArrayList$Itr.next(ArrayList.java:853)
     * at com.example.ThreadPool.threadsafety.TestListIterator$TestIteratorRunnable.run(TestListIterator.java:58)
     * at java.lang.Thread.run(Thread.java:745)
     *
     *在遍历的同时进行另外一个线程添加数据，就会报错,可以通过加同步锁来完成。
     */
    private static void newThreadToAdd() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    synchronized (TestListIterator.class) {
                        list.add(i + "");
                    }
                    try {
                        Thread.currentThread().sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    /**
     * 多个线程是可以同时使用迭代器访问同一个集合对象的
     */
    private static class TestIteratorRunnable implements Runnable {
        @Override
        public void run() {
            synchronized (TestListIterator.class){
            for (String i : list) {
                System.out.println("threadName:" + Thread.currentThread().getName() + "value:" + i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }}
        }
    }


  /**
     * 多个线程是可以同时使用迭代器访问同一个集合对象的,同时在遍历的过程中对单个对象进行修改不影响遍历。
     */
    private static class TestIteratorUpdateRunnable implements Runnable {
        @Override
        public void run() {
            for (String i : list) {
                //引用修改后，依然可以遍历，但是引用指向的内存内容发生变化后就会报错。
                list=null;
                //修改遍历过程中的单个对象，不影响遍历
                i=i+0;
                System.out.println("threadName:" + Thread.currentThread().getName() + "value:" + i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }







    private static void initList() {
        for (int i = 0; i < 100; i++) {
            list.add(i + "");
        }
    }


}
