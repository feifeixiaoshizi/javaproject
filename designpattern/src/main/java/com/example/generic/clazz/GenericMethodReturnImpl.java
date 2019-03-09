package com.example.generic.clazz;

/**
 * Created by jianshengli on 2019/3/3.
 */

public  class GenericMethodReturnImpl extends  GenericMethodReturn<String>{

   @Override
   public String get() {
      return "i am return type!";
   }
}
