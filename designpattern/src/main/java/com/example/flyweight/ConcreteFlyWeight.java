package com.example.flyweight;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class ConcreteFlyWeight implements FlyWeight {

    private String name;

    public ConcreteFlyWeight(String name) {
        this.name = name;
    }

    @Override
    public void action(String externalState) {
        System.out.println(externalState);
    }
}
