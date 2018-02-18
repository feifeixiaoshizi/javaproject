package com.example.proxy.interfaces;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class RealTeach implements ITeach {
    @Override
    public void teach() {
        System.out.println("i am an real Teach!");
    }

    @Override
    public String getString(String input) {
        return input;
    }
}
