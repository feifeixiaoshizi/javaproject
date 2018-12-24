package com.example.ThreadPool;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *Callable和Future（具体实现类FutureTask）结合实现从Callable中取值，
 *但是future.get()会阻塞直到取到结果。
 */

public class CallableAndFuture {
    // 阻塞队列最大任务数量
    static final int BLOCKING_QUEUE_SIZE = 20;
    static final int THREAD_POOL_MAX_SIZE = 10;

    static final int THREAD_POOL_SIZE = 6;

    /**
     * 缓冲BaseRequest任务队列
     */
    static ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(
            BLOCKING_QUEUE_SIZE);

    public static void main(String[] args) throws Exception {

       /* public ThreadPoolExecutorDemo( int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
        RejectedExecutionHandler handler)
*/
        ExecutorService executor = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_MAX_SIZE, 60, TimeUnit.SECONDS, blockingQueue, new ThreadPoolExecutor.DiscardOldestPolicy());

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("thead name :"+Thread.currentThread().getName());
                Thread.currentThread().sleep(6000);
                return 1;
            }
        };

       /*
        可以通过FutureTask（Future的具体实现类）封装Callable对象，然后把FutureTask作为Runnable对象执行。

        interface RunnableFuture<V> extends Runnable, Future<V>，实现了Runnable和Future接口，
        即可作为Runnable运行，也可作为Future获取结果值。

        FutureTask实现了RunnableFuture<V>接口。*/
        FutureTask<Integer> future = new FutureTask<Integer>(callable);
        executor.execute(future);

        int value = future.get();
        System.out.println("value:"+value);
        System.out.println("thead main:"+Thread.currentThread().getName());

        //可以通过submit的方式接受Callable参数
        Future<Integer> future1 = executor.submit(callable);
        //阻塞等待取值（***）
        int value1 = future1.get();
        System.out.println("value1:"+value1);
        System.out.println("thead main1:"+Thread.currentThread().getName());


    }




}
