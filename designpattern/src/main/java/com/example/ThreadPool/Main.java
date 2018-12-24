package com.example.ThreadPool;


/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class Main {
    static  Dispatcher dispatcher = new Dispatcher();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 90; i++) {
            dispatcher.enqueue(new NameRunnable(i));
        }


    }

    static class NameRunnable implements Runnable {
        private int id;

        public NameRunnable(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("threadName:    " + Thread.currentThread().getName() + "    id:" + id);
            //每次任务执行完毕后，都要主动调用dispatcher的方法来让dispathcher去利用这个刚空闲的线程去执行下一个任务。（***）
            dispatcher.finished(this,true);

        }
    }


}
