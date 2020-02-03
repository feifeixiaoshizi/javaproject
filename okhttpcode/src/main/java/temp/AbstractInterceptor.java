package temp;

public abstract class AbstractInterceptor implements Interceptor {
    protected AbstractInterceptor next;

    public AbstractInterceptor(AbstractInterceptor next) {
        this.next = next;
    }
}
