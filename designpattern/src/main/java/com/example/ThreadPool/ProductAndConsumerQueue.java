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
 * 使用阻塞队列实现生产和消费模式
 *
 */

public class ProductAndConsumerQueue {

    //共享资源（保证添加和获取不会出现线程安全问题）
    static ArrayBlockingQueue<Product> queue = new ArrayBlockingQueue<Product>(1);


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
            for (int i = 0; i < 100; i++) {
                    Product product = new Product("product" + i, i);
                    try {
                        queue.put(product);
                        System.out.println("product:" + product.name);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }


    }

    static class ConsumerRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {

                    Product product = null;
                    try {
                        product = queue.take();
                        System.out.println("consumer:" + Thread.currentThread().getName() + "product:" + product.name);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }



        }
    }


}
