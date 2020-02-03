package temp;

import okhttp3.Request;
import okhttp3.Response;

public class ForthIntercept extends  AbstractInterceptor {

    public ForthIntercept(AbstractInterceptor next) {
        super(next);
    }

    @Override
    public Response intercept(Request request) {
        return next.intercept(request);
    }
}
