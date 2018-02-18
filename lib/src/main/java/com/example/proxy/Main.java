package com.example.proxy;



import com.example.proxy.interfaces.ITeach;
import com.example.proxy.interfaces.RealTeach;
import com.example.proxy.invokers.RealTeachInvocation;
import com.example.proxy.invokers.TeachInvocation;

import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class Main {

    public static void main(String[] args) throws Exception {

        RealTeach realTeach = new RealTeach();
        RealTeachInvocation riv = new RealTeachInvocation(realTeach);
        TeachInvocation tv = new TeachInvocation();

        try {

            /*Proxy.newProxyInstance()方法的具体的分析：
            * 1：通过Proxy.newProxyInstance（）方法会创建一个继承Proxy类实现目标接口的类的。
            * 2：然后创建一个该类的实例对象并且封装了InvocationHandler对象，负责方法调用的转移。
            * 3：在生成的类里面，都获取到所有的方法的Method对象。
            * 4：在实现目标接口的方法里，会调用InvocationHander对象的invoke（）方法把自身和当前调用的方法的Method对象传递进去。
            * 然后通过调用代理的方法就移交给了InvocationHandler来进行处理。
            * 5：在InvocationHandler里面可以通过反射和真实的对象调用方法，也可以不调用真实的对象的方法，自己去完成具体的实现。
            *
            * */
           ITeach proxy = (ITeach) Proxy.newProxyInstance(realTeach.getClass().getClassLoader(),realTeach.getClass().getInterfaces(),riv);
           ITeach proxy1 = (ITeach) Proxy.newProxyInstance(realTeach.getClass().getClassLoader(),realTeach.getClass().getInterfaces(),tv);
            proxy.teach();
            proxy1.teach();
            String strreal = proxy.getString("wo shi real ");
            String strproxy = proxy1.getString("wo shi proxy ");
            System.out.println("strreal:"+strreal+"strproxy:"+strproxy);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }


    }


}
