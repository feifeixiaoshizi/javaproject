package com.example.responsibilitychain.iterfaceway;

import java.io.IOException;

/**
 * 最后一个拦截不在需要使用chain来传递而是终止整个拦截器链，递归返回到第一个拦截器。（******）
 */
public class Interceptor3 implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response =new Response();
        Request request = chain.request();
        request.setPramters(request.getPramters()+"3");
        response.setResponse(request.getPramters());
        System.out.println("response:"+response.getResponse());
        return response;
    }
}
