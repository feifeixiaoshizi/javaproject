package temp;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 自己的思路实现okhttp的拦截器做法
 * Req->Req1->Req2->
 * Res<-Res2<-Res<-
 * 链表结构实现-->Chain单例模（集合结构实现）-->Chain作为方法参数实现版（okhttp的实现版）
 */
public interface Interceptor {
    Response intercept(Request request);
}
