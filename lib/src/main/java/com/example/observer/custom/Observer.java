package com.example.observer.custom;

/**
 * Created by Administrator on 2018/1/2 0002.
 * 提供Observable变化是要调用的方法
 */

public interface Observer {
    public void update(Observable observable,Object arg);
}
