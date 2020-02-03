package temp;

import okhttp3.Request;
import okhttp3.Response;

public class User {

    public User() {

    }

    public void deal(){
        Request request =null;
        Chain.getInstance().addInterceptor(new FirstIntercept());
        Chain.getInstance().addInterceptor(new SencondIntercept());
        Response response  = Chain.getInstance().star(request);
    }


    /**
     * 通过链表实现
     * 1.缺点抽象类，阻碍继承关系
     * 2.必须存在引用关系，灵活性低
     */
    public void dealWithLink(){
        AbstractInterceptor interceptor = new ThirdIntercept(new ForthIntercept(null));
        Request request = null;
        Response response = interceptor.intercept(request);
    }
}
