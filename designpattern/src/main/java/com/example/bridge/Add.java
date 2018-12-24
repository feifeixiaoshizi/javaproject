package com.example.bridge;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class Add extends Abstraction {
    @Override
    public void showName() {
        if(implementor!=null){
            implementor.showName();
        }
    }


    public int add(int a, int b){
        if (implementor!=null&& implementor instanceof AddImplementor){
            return  ((AddImplementor) implementor).add(a,b);
        }
        return -1;
    }

}
