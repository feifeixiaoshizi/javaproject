package com.example.proxy.invokers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class RealTeachInvocation implements InvocationHandler {
    //封装了真实的对象
    Object realObject;

    public RealTeachInvocation(Object realObject) {
        this.realObject = realObject;
    }

    //proxy是代理对象   method是方法对象
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //通过反射调用真实的对象的方法
        return method.invoke(realObject,args);
    }
}
