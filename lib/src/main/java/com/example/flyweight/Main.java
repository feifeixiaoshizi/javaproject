package com.example.flyweight;

import com.example.builder.Build;
import com.example.builder.Build1;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class Main {

    public static void main(String[] args) throws Exception {

        FlyWeight flyWeight = FlyWeightFactory.getFlyWeight("test");
        System.out.println("1:"+flyWeight.toString());

        FlyWeight flyWeight1 = FlyWeightFactory.getFlyWeight("test");
        System.out.println("2:"+flyWeight1.toString());
        flyWeight.action("action");
        flyWeight1.action("action1");

    }


}
