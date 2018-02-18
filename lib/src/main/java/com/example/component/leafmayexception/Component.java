package com.example.component.leafmayexception;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public abstract class Component {
    protected String name;

    public Component(String name) {
        this.name = name;
    }
    public abstract void doSonthing();

    public abstract void  addChild(Component component);

    public abstract void removeChild(int index);
    public abstract void removeChild(Component component);

    public abstract Component getChildAt(int index);
}
