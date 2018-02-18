package com.example.responsibilitychain.iterfaceway;


import java.io.IOException;
import java.util.List;

/**
 *
 * Created by Administrator on 2017/12/20 0020.
 *
 * 由Chain来负责把Interceptor串起来。
 * 每一个拦截器都封装一个Chain，该Chain中指定了它的下一个拦截器的索引，每个拦截器都可以访问到所有的拦截器。
 *
 * 每个拦截器执行还是递归的方法，拦截器1---》拦截器2--》最后的拦截器--》返回值给
 * 拦截器2---》返回值拦截器1.
 *
 * Chain：负责的是传递拦截器，如果已经是最后一个拦截器了，据没有必要再使用拦截器传递了，就该结束了。
 *
 *
 * 链子中集合和index是重点，集合负责了所有的节点，index负责要进行的节点的位置。（*****）
 *
 */

public class RealChain implements Interceptor.Chain {
    private List<Interceptor> interceptors;
    private Request request;
    private int index=0;

    public RealChain(Request request,int index,List<Interceptor> interceptors) {
        this.request = request;
        this.index = index;
        this.interceptors = interceptors;
    }

    @Override
    public Request request() {
        return request;
    }

    //负责传递给下一个拦截器
    @Override
    public Response proceed(Request request) throws IOException {
        //return deal(request);
        return deal1(request);
    }


/*
    // Call the next interceptor in the chain.
    RealInterceptorChain next = new RealInterceptorChain(
            interceptors, streamAllocation, httpCodec, connection, index + 1, request);
    Interceptor interceptor = interceptors.get(index);
    Response response = interceptor.intercept(next);
*/


    //负责传递给下一个拦截器
    public Response deal1(Request request)  {
        System.out.println("index:"+index);
        //一定要先改变index值，指向下一个拦截器的位置，然后再执行当前拦截器的拦截方法（****）
        Interceptor.Chain nextChain = new RealChain(request,index+1,interceptors);
        try {
            Interceptor interceptor = interceptors.get(index);
            //每个拦截都需要一个封装了下一个拦截索引的Chain对象。（*****）
            return interceptor.intercept(nextChain);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //负责传递给下一个拦截器
    public Response deal(Request request)  {
        System.out.println("index:"+index);
        Interceptor.Chain nextChain = new RealChain(request,index+1,interceptors);
        try {
            //如果没有拦截器了，就只能自己处理了
            if(index==interceptors.size()){
                Response response =new Response();
                response.setResponse(request.getPramters());
                System.out.println("response:"+response.getResponse());
                return response;
            }
            Interceptor interceptor = interceptors.get(index);
           return interceptor.intercept(nextChain);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
