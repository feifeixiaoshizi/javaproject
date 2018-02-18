package com.example.proxy.invokers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class TeachInvocation implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //也可以没有真实的对象，代理就可以直接完成。
        return "i am a test method!";
    }
}
