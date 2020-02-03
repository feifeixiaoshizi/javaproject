/*
 * Copyright (C) 2014 Square, Inc.
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
package okhttp3;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

/**
 * Observes, modifies, and potentially short-circuits requests going out and the corresponding
 * responses coming back in. Typically interceptors add, remove, or transform headers on the request
 * or response.
 */
public interface Interceptor {
  /**
   * 拦截器的核心代码，入参是个封装的对象，返回值是一个响应值。
   * @param chain 拦截器的链子，负责组合拦截器和传递拦截器。
   * @return
   * @throws IOException
   *
   * Chain和Interceptor的关联关系仅仅是一个方法参数对象，降低了Chain和Interceptor的耦合性（******）
   */
  Response intercept(Chain chain) throws IOException;

  /**
   * 拦截器的内部接口，负责管理拦截器，把拦截器链接起来
   */
  interface Chain {
    /***
     *封装Request
     * @return
     */
    Request request();

    /**
     * 核心代码，负责传递拦截器
     * @param request
     * @return
     * @throws IOException
     */
    Response proceed(Request request) throws IOException;

    /**
     * Returns the connection the request will be executed on. This is only available in the chains
     * of network interceptors; for application interceptors this is always null.
     */
    @Nullable Connection connection();

    Call call();

    int connectTimeoutMillis();

    Chain withConnectTimeout(int timeout, TimeUnit unit);

    int readTimeoutMillis();

    Chain withReadTimeout(int timeout, TimeUnit unit);

    int writeTimeoutMillis();

    Chain withWriteTimeout(int timeout, TimeUnit unit);
  }
}
