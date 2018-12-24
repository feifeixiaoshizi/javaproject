package com.example.ThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
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
 * 一把锁连个条件对象，可以更准确的唤醒对方。避免唤醒无用方。
 *
 */

public class ProductAndConsumer {
    static ArrayBlockingQueue<Product> queue = new ArrayBlockingQueue<Product>(1);
    static ReentrantLock lock = new ReentrantLock();
    static  Condition conditionProduct= lock.newCondition();
    static  Condition conditionConsumer= lock.newCondition();


    public static void main(String[] args) throws Exception {
        Runnable productRunnable = new ProductRunnable();
        Thread product = new Thread(productRunnable);

        product.start();


        Runnable ConsumerRunnable = new ConsumerRunnable();
        Thread consumer1 = new Thread(ConsumerRunnable);
        Thread consumer2 = new Thread(ConsumerRunnable);
        consumer1.start();
        consumer2.start();


    }


    static class Product {
        String name;
        int price;

        public Product(String name, int price) {
            this.name = name;
            this.price = price;
        }
    }


    static class ProductRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                    lock.lock();
                    Product product = new Product("product" + i, i);
                    System.out.println("product:" + product.name);
                    try {
                        queue.put(product);
                        conditionProduct.await();
                        conditionConsumer.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                lock.unlock();
                }

            }


    }

    static class ConsumerRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                    lock.lock();
                    Product product = null;
                    try {
                        product = queue.take();
                        System.out.println("consumer:" + Thread.currentThread().getName() + "product:" + product.name);
                        conditionConsumer.await();
                        conditionProduct.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.unlock();
                }



        }
    }


}
