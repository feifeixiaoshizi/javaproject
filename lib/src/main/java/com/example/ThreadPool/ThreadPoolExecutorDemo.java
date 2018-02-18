package com.example.ThreadPool;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/12/19 0019.
 * 多线程线程池的知识点：
 * 1：对顶层的接口只包含一个execute的方法
 * public interface Executor {
         void execute(Runnable command);
   }


   2：具体的关系树，一般使用ThreadPoolExecutor作为线程池
   public interface ExecutorService extends Executor
        public abstract class AbstractExecutorService implements ExecutorService
                        public class ThreadPoolExecutor extends AbstractExecutorService
 *
 *
 *
 *3：ThreadPoolExecutor的参数
 *  corePoolSize：核心线程数
 *  maximumPoolSize：最大线程数
 *  keepAliveTime：线程存活时间
 *  unit：时间单位
 *  workQueue：任务队列
 *  threadFactory：线程工厂，负责创建Thread对象
 *  handler：超过线程池最大处理能力后的拒绝处理。
 *  public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,
 *  BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory,RejectedExecutionHandler handler)
 *
 *
 *4：Executors创建线程池的工具类，内部封装了ThreadPoolExecutor。
 * 提供了四种线程池。
 * 1、FixedThreadPool
 他是一种数量固定的线程池，且任务队列也没有大小限制；
 它只有核心线程，且这里的核心线程也没有超时限制，所以即使线程处于空闲状态也不会被回收，除非线程池关闭；
 *public static ExecutorService newFixedThreadPool(int nThreads) {
         return new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
 }
 2、CachedThreadPool
 无界线程池，可以进行自动线程回收
 他是一种线程数量不固定的线程池；
 它只有非核心线程，且最大线程数为Integer.MAX_VALUE，也就是说线程数可以任意大；
 当池中的线程都处于活动状态时，会创建新的线程来处理任务，否则会利用空闲线程来处理任务；所以，任何添加进来的任务都会被立即执行；
 池中的空闲线程都有超时限制，为60s，超过这个限制就会被回收，当池中的所有线程都处于闲置状态时，都会因超时而被回收，这个时候，她几乎不占用任何系统资源；
 适合做大量的耗时较少的任务；
 public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
 }

 3、SingleThreadExecutor
 只有一个核心线程，所有任务都在同一线程中按序执行，这样也就不需要处理线程同步的问题；
 public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>()));
 }
 4、ScheduledThreadPool
 它的核心线程数量是固定的，而非核心线程是没有限制的，且非核心线程空闲时会被回收；
 适合执行定时任务和具有固定周期的任务
 public class ScheduledThreadPoolExecutor extends ThreadPoolExecutor implements ScheduledExecutorService

 public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
 return new ScheduledThreadPoolExecutor(corePoolSize);
 }


 */

public class ThreadPoolExecutorDemo {
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
        Executor executor = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_MAX_SIZE, 60, TimeUnit.SECONDS, blockingQueue, new ThreadPoolExecutor.DiscardOldestPolicy());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thead test！"+Thread.currentThread().getName());
            }
        };


        Runnable runnable1 = new AsyRunnable(new CallBack() {
            @Override
            public void onReturn(String response) {
               /*
                currentThread：pool-1-thread-2
                callback ：i com a thread pool-1-thread-2
                从日志可以看出：匿名的CallBack对象的创建是在主线程里面，
                但是CallBack对象的onReturn（）方法是在线程池中的线程2中执行。

                */
                System.out.println("currentThread"+Thread.currentThread().getName());
                System.out.println("callback"+response);


            }
        });
        executor.execute(runnable);
        executor.execute(runnable1);
        System.out.println("thead main:"+Thread.currentThread().getName());

    }

    //异步回调的实现原理
    static class  AsyRunnable implements   Runnable{
        CallBack callBack;

        public AsyRunnable(CallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(callBack!=null){
                callBack.onReturn("i com a thread "+Thread.currentThread().getName());
            }
        }
    }


    public interface CallBack{
       void  onReturn(String response);
    }


}
