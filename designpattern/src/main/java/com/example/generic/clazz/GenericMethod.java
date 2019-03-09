package com.example.generic.clazz;

import com.example.generic.GenericWildcard;

/**
 * Created by jianshengli on 2019/3/3.
 */

public class GenericMethod<T> {

    public GenericWildcard<T> getGenericWildcard() {
        return genericWildcard;
    }

    private GenericWildcard<T> genericWildcard;


    public GenericMethod(GenericWildcard<T> genericWildcard) {
        this.genericWildcard = genericWildcard;
    }

    public static  <T> GenericMethod<T> get() {
        GenericWildcard<T> data = (GenericWildcard<T>) getData();
        return new GenericMethod(data);

    }


    public static GenericWildcard<?> getData() {
        return new GenericWildcard("");
    }

}
