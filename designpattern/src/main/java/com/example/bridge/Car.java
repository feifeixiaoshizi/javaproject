package com.example.bridge;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class Car extends Abstraction {
    @Override
    public void showName() {
        if(implementor!=null){
            implementor.showName();
        }
    }
}
