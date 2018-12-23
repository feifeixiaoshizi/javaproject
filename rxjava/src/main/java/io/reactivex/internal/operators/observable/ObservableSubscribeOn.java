/**
 * Copyright (c) 2016-present, RxJava Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

/**
 Observable<T>中的方法：
 public final Observable<T> subscribeOn(Scheduler scheduler) {
 ObjectHelper.requireNonNull(scheduler, "scheduler is null");
 return RxJavaPlugins.onAssembly(new ObservableSubscribeOn<T>(this, scheduler));
 }

 1.在subscribeOn（）方法里面创建了一个ObservableSubscribeOn对象并且封装了当前的Observable以及新的Observable所需要的Scheduler。
 2.在ObservableSubscribeOn的subscribeActual(final Observer<? super T> observer) 方法中会完成对上一个Observable的订阅，
  2.1 创建上一个Observable对应的Observer，并封装新的Observable所订阅的那个Observer。
  2.2 创建一个Runnable任务交给Scheduler来调度，完成对上一个Observalbe的订阅Observer的行为。
  2.3 可以看出每个方法的操作，都会生成一对Observable和Observer，每一个Observable都对应一个Observer。
  2.4 使用了装饰模式（Observable封装Observable Observer封装Observer），观察者模式 Observable通知Observer。

 *
 * subscribeOn对应的Observable对象
 * @param <T>
 */
public final class ObservableSubscribeOn<T> extends AbstractObservableWithUpstream<T, T> {

    /**
     * 调度器
     */
    final Scheduler scheduler;

    public ObservableSubscribeOn(ObservableSource<T> source, Scheduler scheduler) {
        super(source);
        this.scheduler = scheduler;
    }

    /**
     * 真正的订阅
     * @param observer the incoming Observer, never null
     */
    @Override
    public void subscribeActual(final Observer<? super T> observer) {
        //创建一个新的SubscribeOnObserver封装底层的observer
        final SubscribeOnObserver<T> parent = new SubscribeOnObserver<T>(observer);

        observer.onSubscribe(parent);

        //通过调度器执行订阅任务，传给scheduler一个Runnable对象，Scheduler内部会创建一个Worker来调度Runnable对象，
        //每个Worker内部都有线程（池）负责执行Runnable。（*****）
        parent.setDisposable(scheduler.scheduleDirect(new SubscribeTask(parent)));
    }

    /**
     * 1.实现Observer<T>，封装下流Observer
     * @param <T>
     */
    static final class SubscribeOnObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {

        private static final long serialVersionUID = 8094547886072529208L;
        //下流的Observer对象，把接收到的信息传递给下流。（****）
        final Observer<? super T> downstream;

        final AtomicReference<Disposable> upstream;

        SubscribeOnObserver(Observer<? super T> downstream) {
            this.downstream = downstream;
            this.upstream = new AtomicReference<Disposable>();
        }

        @Override
        public void onSubscribe(Disposable d) {
            DisposableHelper.setOnce(this.upstream, d);
        }

        @Override
        public void onNext(T t) {
            //通知下流Observer
            downstream.onNext(t);
        }

        @Override
        public void onError(Throwable t) {
            downstream.onError(t);
        }

        @Override
        public void onComplete() {
            downstream.onComplete();
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(upstream);
            DisposableHelper.dispose(this);
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        void setDisposable(Disposable d) {
            DisposableHelper.setOnce(this, d);
        }
    }

    /**
     * 订阅任务，交给调度器来执行
     */
    final class SubscribeTask implements Runnable {
        private final SubscribeOnObserver<T> parent;

        SubscribeTask(SubscribeOnObserver<T> parent) {
            this.parent = parent;
        }

        @Override
        public void run() {
            //在调度线程里执行原始的Observable订阅操作（****），在线程里改变了Observable中的执行代码。
            source.subscribe(parent);
        }
    }
}
