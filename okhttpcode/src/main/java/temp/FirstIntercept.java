package temp;

import okhttp3.Request;
import okhttp3.Response;

public class FirstIntercept implements Interceptor {
    @Override
    public Response intercept(Request request) {
        return Chain.getInstance().getNextInterceptor(this).intercept(request);
    }
}
