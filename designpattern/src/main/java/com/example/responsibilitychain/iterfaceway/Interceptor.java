package com.example.responsibilitychain.iterfaceway;

import java.io.IOException;

/**
 * Created by Administrator on 2017/12/20 0020.
 * 拦截器是真正干活的地方，Chain只是负责把拦截器串联起来。
 *
 * （重要理解）
 * 每个拦截器都负责干好自己的任何，然后通过Chain交给下一个拦截器去干其他的任务。
 *
 *
 */


public interface Interceptor {
    Response intercept(Chain chain) throws IOException;

    //每个一个拦截器里面都包含一个新的Chain，每个Chain里面都包含了所有的拦截器，和当前拦截的Request对象。
    interface Chain {
        Request request();

        Response proceed(Request request) throws IOException;


    }
}

