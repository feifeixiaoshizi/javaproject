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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

/**
 * Adapts a {@link Call} with response type {@code R} into the type of {@code T}. Instances are
 * created by {@linkplain Factory a factory} which is
 * {@linkplain Retrofit.Builder#addCallAdapterFactory(Factory) installed} into the {@link Retrofit}
 * instance.
 * 1.Call<T>的适配器，把Call<T>转化为另外一个对象
 * 2.适配器模式+抽象工厂设计模式（内部类）
 * 3。主要是对Call对象进行转化，把Call对象转化为另外一个对象，本质上实现目标接口封装Call对象。
 * 4。多个范型参数的范型接口。（范型参数：成员变量 成员方法 成员方法（参数 ，返回值））
 */
public interface CallAdapter<R, T> {
  /**
   * Returns the value type that this adapter uses when converting the HTTP response body to a Java
   * object. For example, the response type for {@code Call<Repo>} is {@code Repo}. This type
   * is used to prepare the {@code call} passed to {@code #adapt}.
   * <p>
   * Note: This is typically not the same type as the {@code returnType} provided to this call
   * adapter's factory.
   * 响应结果类型
   */
  Type responseType();

  /**
   * Returns an instance of {@code T} which delegates to {@code call}.
   * <p>
   * For example, given an instance for a hypothetical utility, {@code Async}, this instance would
   * return a new {@code Async<R>} which invoked {@code call} when run.
   * <pre><code>
   * &#64;Override
   * public &lt;R&gt; Async&lt;R&gt; adapt(final Call&lt;R&gt; call) {
   *   return Async.create(new Callable&lt;Response&lt;R&gt;&gt;() {
   *     &#64;Override
   *     public Response&lt;R&gt; call() throws Exception {
   *       return call.execute();
   *     }
   *   });
   * }
   * </code></pre>
   * 核心代码：
   * 通过抽象方法定义把参数类型转化为返回值类型，在方法的具体实现里实现封装参数实现返回值类型接口的具体操作。（****）
   *
   */
  T adapt(Call<R> call);

  /**
   * Creates {@link CallAdapter} instances based on the return type of {@linkplain
   * Retrofit#create(Class) the service interface} methods.
   * 1。定义一个工厂负责生产CallAdapter实例对象，定义在CallAdapter内部，表明仅仅和CallAdatpter关联。
   * 2。提供了抽象方法生成CallAdapter。
   */
  abstract class Factory {
    /**
     * Returns a call adapter for interface methods that return {@code returnType}, or null if it
     * cannot be handled by this factory.
     * 重要的分析：
     * 1。函数返回值的特点：CallAdapter<?, ?>返回带范型通配符的CallAdapter对象。
     *    1。1 Object o = "kk"； Object o = 1; 可以看成是一个多态。o可以接收任意类型的对象。
     *    1。2 CallAdapter<?, ?> c1 = CallAdapter<String, String>;
     *        CallAdapter<?, ?> c2 = CallAdapter<Long, Long>;
     *    c可以看成是通过范型通配符？范型接口实现了多态。
     *    //通过CallAdapter<?, ?> 统一接收后还可以再进行向下转型。
     *    CallAdapter<String, String> c3 = (CallAdapter<String, String>)c1;
     * 2。函数参数的特点
     *    参数中传入了Retrofit对象，负责了生成具体的CallAdapter对象。
     *
     */
    public abstract @Nullable CallAdapter<?, ?> get(Type returnType, Annotation[] annotations,
        Retrofit retrofit);

    /**
     * Extract the upper bound of the generic parameter at {@code index} from {@code type}. For
     * example, index 1 of {@code Map<String, ? extends Runnable>} returns {@code Runnable}.
     */
    protected static Type getParameterUpperBound(int index, ParameterizedType type) {
      return Utils.getParameterUpperBound(index, type);
    }

    /**
     * Extract the raw class type from {@code type}. For example, the type representing
     * {@code List<? extends Runnable>} returns {@code List.class}.
     */
    protected static Class<?> getRawType(Type type) {
      return Utils.getRawType(type);
    }
  }
}
