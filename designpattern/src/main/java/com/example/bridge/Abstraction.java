package com.example.bridge;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public abstract class Abstraction {
    protected Implementor implementor;

    public void setImplementor(Implementor implementor) {
        this.implementor = implementor;
    }

    public abstract void showName();
}
