/**
 * Copyright (c) 2016-present, RxJava Contributors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package io.reactivex.disposables;

import java.util.*;

import io.reactivex.annotations.NonNull;
import io.reactivex.exceptions.*;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.*;

/**
 * A disposable container that can hold onto multiple other disposables and
 * offers O(1) add and removal complexity.
 *  /*

 //volatitle关键字的例子
 private volatile boolean a;

 //复合操作（线程不安全）
 private void test() {
 if (a) {
 a = false;
 }
 }

 //原子性操作（线程安全）
 private void set(boolean a){
 this.a = a;
 }

 //原子性操作（线程安全）
 private boolean get(){
 return a;
 }


 1.设计原则 （封装 继承 多态）
 单一原则（类的功能尽量单一）
 开闭原则（修改关闭，扩展开放）
 最少知道原则（降低耦合性）
 接口隔离原则（接口最小化）
 依赖倒置原则 （依赖抽象，不依赖细节）
 替换原则 （子类可以替换父类）

 2.类之间的关系
   2.1 类名上：继承 实现
   2.2 类成员变量 ：一对一  一对多  多对一  多对多
   2.3 类的成员方法： 方法的参数  方法的返回值
   2.4 类的内部类：静态内部类 内部类 匿名内部类
   2.5 类的中间者：两个类通过中间者间接发生关系

 3. 实现了Disposalbe接口具备dispose功能，实现了DisposableContainer具备容器功能



 */
public final class CompositeDisposable implements Disposable, DisposableContainer {

    //synchronized是为了线程安全，两次判断是为了效率。（*****）
    //共享变量（需要保证线程安全）
    OpenHashSet<Disposable> resources;


    volatile boolean disposed;

    /**
     * Creates an empty CompositeDisposable.
     */
    public CompositeDisposable() {
    }

    /**
     * Creates a CompositeDisposables with the given array of initial elements.
     * @param resources the array of Disposables to start with
     */
    public CompositeDisposable(@NonNull Disposable... resources) {
        ObjectHelper.requireNonNull(resources, "resources is null");
        this.resources = new OpenHashSet<Disposable>(resources.length + 1);
        for (Disposable d : resources) {
            ObjectHelper.requireNonNull(d, "Disposable item is null");
            this.resources.add(d);
        }
    }

    /**
     * Creates a CompositeDisposables with the given Iterable sequence of initial elements.
     * @param resources the Iterable sequence of Disposables to start with
     */
    public CompositeDisposable(@NonNull Iterable<? extends Disposable> resources) {
        ObjectHelper.requireNonNull(resources, "resources is null");
        this.resources = new OpenHashSet<Disposable>();
        for (Disposable d : resources) {
            ObjectHelper.requireNonNull(d, "Disposable item is null");
            this.resources.add(d);
        }
    }


    /**
     */
    @Override
    public void dispose() {
        if (disposed) {
            return;
        }
        OpenHashSet<Disposable> set;
        //加锁保证resources的安全性，双重判断是为了在安全性的基础上避免重复执行同步中的代码。
        //比如多个线程都阻塞在同步关键字上，当一个线程执行完同步后，已经释放完毕了资源，其他线程进来也就无需在耗费时间来处理了。（*****）
        synchronized (this) {
            //如果disposed为true则返回，如果disposed为false，则设置为true
            if (disposed) {
                return;
            }
            disposed = true;
            set = resources;
            resources = null;
        }

        dispose(set);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    /**
     * Adds a disposable to this container or disposes it if the
     * container has been disposed.
     * @param d the disposable to add, not null
     * @return true if successful, false if this container has been disposed
     */
    @Override
    public boolean add(@NonNull Disposable d) {
        ObjectHelper.requireNonNull(d, "d is null");
        if (!disposed) {
            synchronized (this) {
                if (!disposed) {
                    OpenHashSet<Disposable> set = resources;
                    if (set == null) {
                        set = new OpenHashSet<Disposable>();
                        resources = set;
                    }
                    set.add(d);
                    return true;
                }
            }
        }
        d.dispose();
        return false;
    }

    /**
     * Atomically adds the given array of Disposables to the container or
     * disposes them all if the container has been disposed.
     * @param ds the array of Disposables
     * @return true if the operation was successful, false if the container has been disposed
     */
    public boolean addAll(@NonNull Disposable... ds) {
        ObjectHelper.requireNonNull(ds, "ds is null");
        if (!disposed) {
            synchronized (this) {
                if (!disposed) {
                    OpenHashSet<Disposable> set = resources;
                    if (set == null) {
                        set = new OpenHashSet<Disposable>(ds.length + 1);
                        resources = set;
                    }
                    for (Disposable d : ds) {
                        ObjectHelper.requireNonNull(d, "d is null");
                        set.add(d);
                    }
                    return true;
                }
            }
        }
        for (Disposable d : ds) {
            d.dispose();
        }
        return false;
    }

    /**
     * Removes and disposes the given disposable if it is part of this
     * container.
     * @param d the disposable to remove and dispose, not null
     * @return true if the operation was successful
     */
    @Override
    public boolean remove(@NonNull Disposable d) {
        if (delete(d)) {
            d.dispose();
            return true;
        }
        return false;
    }

    /**
     * Removes (but does not dispose) the given disposable if it is part of this
     * container.
     * @param d the disposable to remove, not null
     * @return true if the operation was successful
     */
    @Override
    public boolean delete(@NonNull Disposable d) {
        ObjectHelper.requireNonNull(d, "Disposable item is null");
        if (disposed) {
            return false;
        }
        synchronized (this) {
            if (disposed) {
                return false;
            }

            OpenHashSet<Disposable> set = resources;
            if (set == null || !set.remove(d)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Atomically clears the container, then disposes all the previously contained Disposables.
     */
    public void clear() {
        if (disposed) {
            return;
        }
        OpenHashSet<Disposable> set;
        synchronized (this) {
            if (disposed) {
                return;
            }

            set = resources;
            resources = null;
        }

        dispose(set);
    }

    /**
     * Returns the number of currently held Disposables.
     * @return the number of currently held Disposables
     */
    public int size() {
        if (disposed) {
            return 0;
        }
        synchronized (this) {
            if (disposed) {
                return 0;
            }
            OpenHashSet<Disposable> set = resources;
            return set != null ? set.size() : 0;
        }
    }

    /**
     * Dispose the contents of the OpenHashSet by suppressing non-fatal
     * Throwables till the end.
     * @param set the OpenHashSet to dispose elements of
     */
    void dispose(OpenHashSet<Disposable> set) {
        if (set == null) {
            return;
        }
        List<Throwable> errors = null;
        Object[] array = set.keys();
        for (Object o : array) {
            if (o instanceof Disposable) {
                try {
                    ((Disposable) o).dispose();
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    if (errors == null) {
                        errors = new ArrayList<Throwable>();
                    }
                    errors.add(ex);
                }
            }
        }
        if (errors != null) {
            if (errors.size() == 1) {
                throw ExceptionHelper.wrapOrThrow(errors.get(0));
            }
            throw new CompositeException(errors);
        }
    }
}
