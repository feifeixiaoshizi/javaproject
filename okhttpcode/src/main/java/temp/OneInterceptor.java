package temp;

import okhttp3.Response;

public class OneInterceptor implements Interceptor1 {
    @Override
    public Response intercept(Chain chain) {
        //return chain.getNextInterceptor(this).intercept(chain.);
        return null;
    }
}
