package com.example.responsibilitychain.iterfaceway;

import java.io.IOException;

/**
 * Created by Administrator on 2017/12/20 0020.
 *
 * 拦截器是真正干活的对象，负责设置Request和Response，然后通过Chain递交给下一个拦截器去做其他的任务。
 *
 */

public class Interceptor1 implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request.setPramters(request.getPramters()+"1");
        //通过Chain把拦截器都串联起来，然后逐个执行拦截器里面的方法。
        Response response= chain.proceed(request);
        response.setResponse(response.getResponse()+"_1");
        return response;
    }
}
