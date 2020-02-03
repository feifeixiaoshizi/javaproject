package temp;

import okhttp3.Response;

/**
 * 一步一步的优化实现和okhttp类似的责任链模式
 * 方法参数关联两个对象，耦合性低。
 */
public interface Interceptor1 {
    Response intercept(Chain chain);
}
