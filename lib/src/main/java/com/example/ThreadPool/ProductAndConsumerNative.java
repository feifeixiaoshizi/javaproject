package com.example.ThreadPool;

import java.util.ArrayList;

/**
 * 1: 共享的容器资源，负责产品的创建和消费（存放产品）
 * 2：生产者
 * 3：消费者
 * 4：产品
 *
 * 使用wait和notifyall实现的生产者和消费者（*****）
 */

public class ProductAndConsumerNative {


    public static void main(String[] args) throws Exception {
        Resource resource = new Resource();
        CreateRunnable createRunnable = new CreateRunnable(resource);
        ConsumerRunnable consumerRunnable = new ConsumerRunnable(resource);
        Thread create;
        for (int i = 0; i < 5; i++) {
            create = new Thread(createRunnable);
            create.start();
        }

        Thread consumer;
        for (int i = 0; i < 5; i++) {
            consumer = new Thread(consumerRunnable);
            consumer.start();
        }


    }


    static class Product {
        String name;
        int price;

        public Product(String name, int price) {
            this.name = name;
            this.price = price;
        }
    }


    static class Resource {

        private ArrayList<Product> products = new ArrayList<>();

        public synchronized void createProduct() {
            //使用while判断条件（*****）
            while (products.size() ==2) {
                try {
                    wait();//释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Product product = new Product("1", 1);
            products.add(product);
            System.out.println("product a product " + product.name + "size " + products.size());
            notifyAll();
        }


        public synchronized void consumerProduct() {
            while (products.size() ==0) {
                try {
                    wait();//释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Product product = products.remove(0);
            System.out.println("consumer a product " + product.name + "size " + products.size());
            System.out.println("--------------------------------------------------------" );
            notifyAll();
        }


    }


    static class CreateRunnable implements Runnable {
        private Resource resource;

        public CreateRunnable(Resource resource) {
            this.resource = resource;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resource.createProduct();


            }
        }
    }


    static class ConsumerRunnable implements Runnable {
        private Resource resource;

        public ConsumerRunnable(Resource resource) {
            this.resource = resource;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resource.consumerProduct();

            }

        }
    }


}
