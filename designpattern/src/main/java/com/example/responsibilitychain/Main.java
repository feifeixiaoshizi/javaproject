package com.example.responsibilitychain;



import com.example.responsibilitychain.chain.IResponsibility;
import com.example.responsibilitychain.chain.Responsibility1;
import com.example.responsibilitychain.chain.Responsibility2;
import com.example.responsibilitychain.collection.Filter;
import com.example.responsibilitychain.collection.Filter1;
import com.example.responsibilitychain.collection.Filter2;
import com.example.responsibilitychain.collection.FilterChain;
import com.example.responsibilitychain.iterfaceway.Interceptor;
import com.example.responsibilitychain.iterfaceway.Interceptor1;
import com.example.responsibilitychain.iterfaceway.Interceptor2;
import com.example.responsibilitychain.iterfaceway.Interceptor3;
import com.example.responsibilitychain.iterfaceway.RealChain;
import com.example.responsibilitychain.iterfaceway.Request;
import com.example.responsibilitychain.iterfaceway.Response;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class Main {

    public static void main(String[] args) throws Exception {
        //通过指针实现的责任链模式
        IResponsibility responsibility1 = new Responsibility1();
        IResponsibility responsibility2 = new Responsibility2();

        responsibility1.setNextSuccessor(responsibility2);
        responsibility1.handle("s");

        //使用的集合实现方式
        FilterChain filterChain = new FilterChain();
        Filter filter1 = new Filter1();
        Filter filter2 = new Filter2();
        filterChain.addFilter(filter1).addFilter(filter2);
        filterChain.doNextFilter("start",filterChain);


        //使用了仿照okhttp的实现方式
        Request request = new Request();
        request.setPramters("");
        Interceptor interceptor1 = new Interceptor1();
        Interceptor interceptor2 = new Interceptor2();
        Interceptor interceptor3 = new Interceptor3();
        ArrayList interceptors = new ArrayList();
        interceptors.add(interceptor1);
        interceptors.add(interceptor2);
        interceptors.add(interceptor3);
        RealChain realChain = new RealChain(request,0,interceptors);
        Response response = realChain.proceed(request);
        System.out.println("finallyresponse:"+response.getResponse());
        /*
response:123
finallyresponse:123_2_1

日志分析：
可知拦截器从1-3-》都执行完毕后response为123，
然后3返回结果后，又3--》1递归回1，对response的值再次进行修改。

        * */


    }


}
