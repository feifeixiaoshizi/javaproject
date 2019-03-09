package com.example.generic;

/**
 * 范型通配符
 */

public  class GenericWildcard<T> {
    private T str;
    public   String convert(T t){
        return  "";
    }

    public GenericWildcard(T string) {
        this.str = string;
    }
}
