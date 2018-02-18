package com.example.ThreadPool;
/**
 *
 * 1：缓存空间
 * 2：生产者
 * 3：消费者
 *
 * wait():之后的代码就不在执行了,直到notify把线程重新唤醒后，再紧接着wait()后的代码执行。
 * notify()：之后的代码还要执行，等待同步代码块的方法执行完毕。
 * wait()和notify()的顺序很重要，wait释放同步锁，notify通知等待锁的线程锁来了，去争夺同步锁吧。
 *
 *
 * 生产者和消费者模型是为了线程间的通信，通信用到了共享的资源需要线程安全的支持（*****）
 *
 * 线程安全是为了线程间的数据一致性。（*****）
 *
 */

public class Synchronized {
    static Product product =new Product();

    public static void main(String[] args) throws Exception {
        Runnable productRunnable = new ProductRunnable(product);
        Runnable consumerRunnable = new ConsumerRunnable(product);
        Thread product = new Thread(productRunnable);
        product.start();

        Thread consumer = new Thread(consumerRunnable);
        consumer.start();

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

        public synchronized int getId() throws Exception {
            while (id==0){
                System.out.println("consumner wait() run befor ");
                //调用wait（）方法后，线程阻塞在这里，直到notify（）被唤醒后，紧接着执行wait()下面的代码。（*****）
                wait();
                System.out.println("consumner wait() run after ");
            }
            id--;
            System.out.println("consumer:"+id);
            notifyAll();
            return id;
        }

        public synchronized void setId() throws Exception {
            while (id==1){
                wait();
                System.out.println("product wait() run after ");

            }
            id++;
            System.out.println("product:"+id);
            Thread.currentThread().sleep(5000);
            notifyAll();
        }

        public Product() {

        }

    }

}
