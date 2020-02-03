package temp;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 借助额外类替代链表结构，使用集合来存放。
 * 1.开始遍历
 * 2.逐个遍历拦截器
 * 3.维护拦截器
 *
 * 缺点：
 * 1.使用了单例生命周期被延长，容易造成内存泄漏。
 *
 */
public class Chain {
    /**
     * 获取单例对象
     *
     * @return
     */
    public static Chain getInstance() {
        return Handler.chain;
    }

    private static class Handler {
        private static Chain chain = new Chain();
    }

    private Chain() {
    }

    private List<Interceptor> interceptors = new ArrayList<>();

    /**
     * 添加拦截器
     *
     * @param interceptor
     */
    public void addInterceptor(Interceptor interceptor) {
        if (interceptor != null) {
            interceptors.add(interceptor);
        }
    }

    /**
     * 获取下一个拦截器
     *
     * @param interceptor
     * @return
     */
    public Interceptor getNextInterceptor(Interceptor interceptor) {
        if (interceptor == null) {
            if (interceptors.size() > 0) {
                return interceptors.get(0);
            }
            return null;
        }
        int index = interceptors.indexOf(interceptor);
        if (index >= interceptors.size()) {
            return null;
        }
        if (index == -1) {
            return interceptors.get(0);
        }
        return interceptors.get(index);
    }

    public Response star(Request request) {
        if (!interceptors.isEmpty()) {
           return interceptors.get(0).intercept(request);
        }
        return null;
    }
}
