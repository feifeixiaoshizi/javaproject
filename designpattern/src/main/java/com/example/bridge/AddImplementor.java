package com.example.bridge;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class AddImplementor implements Implementor {
    @Override
    public void showName() {
        System.out.println(" i am a add !");
    }

    public int add(int a,int b){
        return a+b;

    }

}
