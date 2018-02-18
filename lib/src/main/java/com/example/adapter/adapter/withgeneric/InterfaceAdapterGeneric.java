package com.example.adapter.adapter.withgeneric;


/**
 * Created by Administrator on 2017/12/19 0019.
 *
 * 该类是对真实的适配器进行了封装了，其实在adapt（）方法的实现了里面一定存在一个真实的适配器类，
 * 负责实现目标接口并聚合被适配者对象。
 *
 *
 */

//抽象适配器的概念，把一个类型变化为另外一个类型，通过方法屏蔽掉具体的实现方式（实现+聚合）
public interface InterfaceAdapterGeneric<R,T>{
    //其实在这里返回值ITeach的实现类，一定就是一个适配类，负责实现目标接口ITeach并且封装被适配对象IStudy iStudy。
    T adapt(R r);

    abstract class Factory<R,T> {
        /**
         * Returns a call adapter for interface methods that return {@code returnType}, or null if it
         * cannot be handled by this factory.
         */
        public  abstract InterfaceAdapterGeneric<R,T> get();

    }
}
