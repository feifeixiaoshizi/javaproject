package com.example.responsibilitychain.iterfaceway;

import java.io.IOException;

/**
 * Created by Administrator on 2017/12/20 0020.
 拦截器2在拦截器1的基础上做好自己的任务，然后通过Chain交个下一个拦截器去做其他的未完成的任务。
 */

public class Interceptor2 implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request.setPramters(request.getPramters()+"2");
        //把任务通过Chain传递给下一个拦截器对象（****）
        Response response= chain.proceed(request);
        response.setResponse(response.getResponse()+"_2");
        return response;
    }
}
