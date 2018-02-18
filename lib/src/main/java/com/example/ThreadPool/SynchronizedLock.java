package com.example.ThreadPool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/12/25 0025.
 * <p>
 * 1：缓存空间
 * 2：生产者
 * 3：消费者
 *
 *
 * wait():之后的代码就不在执行了,直到notify把线程重新唤醒后，再紧接着wait（）后的代码执行。
 * notify（）：之后的代码还要执行，等待同步代码块的方法执行完毕。
 * wait（）和notify的顺序很重要，wait释放同步锁，notify通知等待锁的线程锁来了，去争夺同步锁吧。
 *
 *
 日志分析：
 await() run before
 product:1
 await() run after
 consumer:0

先执行getId（）方法，然后id为0，执行conditionConsumer.await();getId（）阻塞



 线程安全：一定是加锁或者ThreadLocal（时间和空间上两个角度控制）
          加锁：synchronized关键字(隐式锁)    ReentrantLock（显式锁）

 线程间通信：生产者 -消费者模式   等待唤醒机制（wait notify  Condition）  阻塞队列

 线程管理：线程池

 线程任务： 无返回值 Runnable  有返回值 Callable  FutureTask

 *
 */

public class SynchronizedLock {
    static Product product =new Product();
    static ReentrantLock lock = new ReentrantLock();
    static  Condition conditionProduct= lock.newCondition();
    static  Condition conditionConsumer= lock.newCondition();


    public static void main(String[] args) throws Exception {
        Runnable productRunnable = new ProductRunnable(product);
        Runnable consumerRunnable = new ConsumerRunnable(product);

        Thread consumer = new Thread(consumerRunnable);
        consumer.start();

        Thread product = new Thread(productRunnable);
        product.start();


    }

   static class ProductRunnable  implements Runnable{
        private Product product;

        public ProductRunnable(Product product) {
            this.product = product;
        }

        @Override
        public void run() {
                try {
                    product.setId();

                } catch (Exception e) {
                    e.printStackTrace();
                }





        }
    }

   static class ConsumerRunnable  implements Runnable{
        private Product product;

        public ConsumerRunnable(Product product) {
            this.product = product;
        }
        @Override
        public void run()  {
            try {
                product.getId();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    static class Product {
       private int id=0;

        public int getId() throws Exception {
            lock.lock();
            System.out.println("await() run before");
            //如果id为0，则消费者等待
            while (id==0){
                conditionConsumer.await();
                System.out.println("await() run after");
            }
            id--;
            System.out.println("consumer:"+id);
            conditionProduct.signal();
            lock.unlock();
            return id;
        }

        public  void setId() throws Exception {
            lock.lock();
            while (id==1){
              conditionProduct.await();

            }
            id++;
            System.out.println("product:"+id);
            Thread.currentThread().sleep(4000);
            conditionConsumer.signal();
            lock.unlock();
        }

        public Product() {

        }


    }





}
