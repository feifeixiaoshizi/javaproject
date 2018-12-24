package com.example.ThreadPool;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/12/22 0022.
 * 1: 线程池对象
 * 2：任务队列
 * 3: 执行任务的方法
 * 4: 考虑线程安全问题（多个线程都调用Dispatcher中的方法，要考虑到线程安全问题）
 * 5：调度器应该是单例的。
 * <p>
 * final的好处：
 * <p>
 * final关键字提高了性能。JVM和Java应用都会缓存final变量。
 * final变量可以安全的在多线程环境下进行共享，而不需要额外的同步开销。（因为不可变索引安全）
 * 使用final关键字，JVM会对方法、变量及类进行优化。
 * 不可变类创建不可变类要使用final关键字。不可变类是指它的对象一旦被创建了就不能被更改了。
 * String是不可变类的代表。不可变类有很多好处，譬如它们的对象是只读的，可以在多线程环境下安全的共享，不用额外的同步开销等等。
 * <p>
 * <p>
 * 线程池：负责提供执行任务的线程，以及池中线程共享的任务。（*****）作用：调度线程去有序的执行任务。
 * ThreadPoolExecutor的参数
 * corePoolSize：核心线程数
 * maximumPoolSize：最大线程数
 * keepAliveTime：线程存活时间
 * unit：时间单位
 * workQueue：任务队列
 * threadFactory：线程工厂，负责创建Thread对象
 * handler：超过线程池最大处理能力后的拒绝处理。
 * public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,
 * BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory,RejectedExecutionHandler handler)
 * <p>
 * 但是如果把BlockingQueue<Runnable>设置为只能存放0个任务，就相当于把线程池的共享任务架空了，仅仅负责提供线程，而且任务
 * 可以立即执行，而任务共享的任务就交给外部去负责了，线程池仅仅负责立即执行任务。
 * <p>
 * 线程池：线程 + 任务。（*****)
 */

public class Dispatcher1 {
    //线程池对象
    private ExecutorService executor;
    //任务队列是不可以变（就算使用反射也不能改变）
    private final Queue<Runnable> runningTasks = new ArrayBlockingQueue<Runnable>(100);
    //等待执行的队列
    private final Queue<Runnable> readyTasks = new ArrayBlockingQueue<Runnable>(100);
    private int maxRequetCount = 64;


    public Dispatcher1() {
    }


    public synchronized ExecutorService executor() {
        if (executor == null) {
            //创建一个缓存线程池，全部都是非核心线程，每个线程最多可以存活60s。
            //同步队列就是放一个必须有一个立即消费，否则就会阻塞，直到消费的来了。
            executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadPoolExecutor.DiscardPolicy());

        }
        return executor;
    }

    public synchronized void enqueue(Runnable runnable) {
        runnable = new RemoveRunnable(runnable, this);
        //如果正在执行队列的size小于最大请求数，
        if (runningTasks.size() < maxRequetCount) {
            //添加到正在执行的队列
            runningTasks.add(runnable);
            //调用线程池执行任务
            executor().execute(runnable);
        } else {
            //添加到等待任务队列里面
            readyTasks.add(runnable);
        }
    }

    private void finished(Runnable runnable, boolean promoteCalls) {
        synchronized (this) {
            if (!runningTasks.remove(runnable)) throw new AssertionError("Call wasn't in-flight!");
            if (promoteCalls) promoteCalls();

        }

    }

    //每次执行完一个任务，都要调用该方法来利用刚空闲的方法来执行等待队列中的任务。
    private void promoteCalls() {
        if (runningTasks.size() >= maxRequetCount) return; // Already running max capacity.
        if (readyTasks.isEmpty()) return; // No ready calls to promote.
        for (Iterator<Runnable> i = readyTasks.iterator(); i.hasNext(); ) {
            Runnable call = i.next();
            i.remove();
            runningTasks.add(call);
            //开始执行任务
            executor().execute(call);
            if (runningTasks.size() >= maxRequetCount) return; // Reached max capacity.
        }
    }

    /**
     * 1.封装Runnable,在Runnable执行完毕后，调用Dispatcher从队列中移除该任务
     * 2.使用final修饰成员变量，保证线程安全
     * 3.封装Dispather1，在任务执行完毕后，调用Dispatcher1来释放该任务。
     */
    private static final class RemoveRunnable implements Runnable {
        private final Runnable runnable;
        private final Dispatcher1 dispatcher;

        public RemoveRunnable(Runnable runnable, Dispatcher1 dispatcher) {
            this.runnable = runnable;
            this.dispatcher = dispatcher;
        }


        @Override
        public void run() {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dispatcher.finished(this, true);
            }
        }

        /**
         * 避免装饰带来的不能向下转型的问题。
         * @return
         */
        public Runnable getWrappedRunnable() {
            return this.runnable;
        }
    }


}
