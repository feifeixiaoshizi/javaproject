package com.example.generic.clazz;

/**
 * Created by jianshengli on 2019/3/3.
 */

public class GenericClassField<T> {
    private T data;

    public GenericClassField(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
