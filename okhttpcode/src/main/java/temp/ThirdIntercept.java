package temp;

import okhttp3.Request;
import okhttp3.Response;

public class ThirdIntercept extends  AbstractInterceptor {

    public ThirdIntercept(AbstractInterceptor next) {
        super(next);
    }

    @Override
    public Response intercept(Request request) {
        return next.intercept(request);
    }
}
