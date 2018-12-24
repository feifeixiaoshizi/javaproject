package com.example.flyweight;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/12/19 0019.
 * 享元模式重在复用，复用就是缓存起来。
 *
 * 1: 接口中封装要重复使用的方法。
 * 2：复用接口的具体实现对象。
 * 3：线程安全问题
 * 4：内部状态和外部状态。
 * 5：核心在与复用，可以使用集合或者链表实现缓存对象。
 *
 */

public class FlyWeightFactory {
    //享元的核心
    private static ConcurrentHashMap<String, FlyWeight> allFlyWeight = new ConcurrentHashMap<String, FlyWeight>();

    public static FlyWeight getFlyWeight(String name) {
        if (allFlyWeight.get(name) == null) {
            synchronized (allFlyWeight) {
                if (allFlyWeight.get(name) == null) {
                    FlyWeight flyWeight = new ConcreteFlyWeight(name);
                    //ConcurrentHashMap的put方法是线程安全的，但是整个方法而言不是线程安全的，所以要加synchronized关键字。
                    allFlyWeight.put(name, flyWeight);
                }
            }
        }
        return allFlyWeight.get(name);
    }

}
