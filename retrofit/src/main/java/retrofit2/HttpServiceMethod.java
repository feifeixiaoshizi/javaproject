/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;

import static retrofit2.Utils.methodError;

/** Adapts an invocation of an interface method into an HTTP call.
 * 1。封装了RequestFactory负责根据参数创建一个Request对象
 * 2。封装了okhttpCallFactory创建一个Call对象负责发起请求
 * 3。封装了Call的Adapter负责对创建的Call对象进行变换
 * 4。封装了Converter负责对响应结果进行转化
 * */
final class HttpServiceMethod<ResponseT, ReturnT> extends ServiceMethod<ReturnT> {
  private final RequestFactory requestFactory;
  private final okhttp3.Call.Factory callFactory;
  private final CallAdapter<ResponseT, ReturnT> callAdapter;
  private final Converter<ResponseBody, ResponseT> responseConverter;
  /**
   * Inspects the annotations on an interface method to construct a reusable service method that
   * speaks HTTP. This requires potentially-expensive reflection so it is best to build each service
   * method only once and reuse it.
   */
  static <ResponseT, ReturnT> HttpServiceMethod<ResponseT, ReturnT> parseAnnotations(
      Retrofit retrofit, Method method, RequestFactory requestFactory) {
    //根据method从Retrofit中获取一个CallAdapter对象（*****）
    CallAdapter<ResponseT, ReturnT> callAdapter = createCallAdapter(retrofit, method);
    Type responseType = callAdapter.responseType();
    if (responseType == Response.class || responseType == okhttp3.Response.class) {
      throw methodError(method, "'"
          + Utils.getRawType(responseType).getName()
          + "' is not a valid response body type. Did you mean ResponseBody?");
    }
    if (requestFactory.httpMethod.equals("HEAD") && !Void.class.equals(responseType)) {
      throw methodError(method, "HEAD method must use Void as response type.");
    }

    Converter<ResponseBody, ResponseT> responseConverter =
        createResponseConverter(retrofit, method, responseType);

    okhttp3.Call.Factory callFactory = retrofit.callFactory;
    return new HttpServiceMethod<>(requestFactory, callFactory, callAdapter, responseConverter);
  }

  private static <ResponseT, ReturnT> CallAdapter<ResponseT, ReturnT> createCallAdapter(
      Retrofit retrofit, Method method) {
    Type returnType = method.getGenericReturnType();
    Annotation[] annotations = method.getAnnotations();
    try {
      //noinspection unchecked（带范型类型的转型）（*****）
      return (CallAdapter<ResponseT, ReturnT>) retrofit.callAdapter(returnType, annotations);
    } catch (RuntimeException e) { // Wide exception range because factories are user code.
      throw methodError(method, e, "Unable to create call adapter for %s", returnType);
    }
  }

  private static <ResponseT> Converter<ResponseBody, ResponseT> createResponseConverter(
      Retrofit retrofit, Method method, Type responseType) {
    Annotation[] annotations = method.getAnnotations();
    try {
      return retrofit.responseBodyConverter(responseType, annotations);
    } catch (RuntimeException e) { // Wide exception range because factories are user code.
      throw methodError(method, e, "Unable to create converter for %s", responseType);
    }
  }



  private HttpServiceMethod(RequestFactory requestFactory, okhttp3.Call.Factory callFactory,
      CallAdapter<ResponseT, ReturnT> callAdapter,
      Converter<ResponseBody, ResponseT> responseConverter) {
    this.requestFactory = requestFactory;
    this.callFactory = callFactory;
    this.callAdapter = callAdapter;
    this.responseConverter = responseConverter;
  }

  @Override ReturnT invoke(Object[] args) {
    //创建Call对象并负责调用适配器方法进行转化为另外一个对象（*****）
    return callAdapter.adapt(
        new OkHttpCall<>(requestFactory, args, callFactory, responseConverter));
  }
}
