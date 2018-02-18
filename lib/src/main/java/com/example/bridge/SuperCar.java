package com.example.bridge;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class SuperCar extends Abstraction {
    @Override
    public void showName() {
        if(implementor!=null){
            implementor.showName();
        }
    }
}
