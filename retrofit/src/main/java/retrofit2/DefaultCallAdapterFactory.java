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
import java.lang.reflect.Type;
import javax.annotation.Nullable;

/**
 * Creates call adapters for that uses the same thread for both I/O and application-level
 * callbacks. For synchronous calls this is the application thread making the request; for
 * asynchronous calls this is a thread provided by OkHttp's dispatcher.
 * 范型结合Type来处理，范型通配符？可以作为范型中的多态。（****）
 */
final class DefaultCallAdapterFactory extends CallAdapter.Factory {
  static final CallAdapter.Factory INSTANCE = new DefaultCallAdapterFactory();

  /**
   *
   * @param returnType 方法返回类型 例如：Call<String>
   * @param annotations 方法上的注解
   * @param retrofit Retrofit对象
   * @return 适配器对象
   */
  @Override public @Nullable CallAdapter<?, ?> get(
      Type returnType, Annotation[] annotations, Retrofit retrofit) {
    //如果原始类型不是Call直接返回Call<String>
    if (getRawType(returnType) != Call.class) {
      return null;
    }

    //返回类型中的响应类型 Call<String> 返回type为String
    final Type responseType = Utils.getCallResponseType(returnType);
    return new CallAdapter<Object, Call<?>>() {
      @Override public Type responseType() {
        return responseType;
      }

      @Override public Call<Object> adapt(Call<Object> call) {
        return call;
      }
    };
  }
}
