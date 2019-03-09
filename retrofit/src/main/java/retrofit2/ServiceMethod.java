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

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static retrofit2.Utils.methodError;

/***
 *
 * @param <T> 范型类型，范型用于规定抽象方法的返回值类型。
 *           范型类型：
 *           1。范型参数用于类的成员方法的参数类型。
 *           2。范型参数用于类的成员方法的返回值类型。
 *           3。范型参数用于类的成员变量类型。
 *
 *           范型类中的范型参数如何赋值：
 *           1。可以直接对类名进行赋值，即直接在 <>中直接赋值。List<String>
 *           2.通过构造方法中对成员变量的赋值类型，自动推出范型变量的值。
 *
 *
 *
 */
abstract class ServiceMethod<T> {
  //抽象类中的范型静态方法（****）
  static <T> ServiceMethod<T> parseAnnotations(Retrofit retrofit, Method method) {
    RequestFactory requestFactory = RequestFactory.parseAnnotations(retrofit, method);

    Type returnType = method.getGenericReturnType();
    if (Utils.hasUnresolvableType(returnType)) {
      throw methodError(method,
          "Method return type must not include a type variable or wildcard: %s", returnType);
    }
    if (returnType == void.class) {
      throw methodError(method, "Service methods cannot return void.");
    }

    return HttpServiceMethod.parseAnnotations(retrofit, method, requestFactory);
  }

  abstract T invoke(Object[] args);
}
