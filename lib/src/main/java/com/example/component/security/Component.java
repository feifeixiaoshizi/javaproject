package com.example.component.security;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public abstract class Component {
    protected String name;

    public Component(String name) {
        this.name = name;
    }
    public abstract void doSonthing();
}
