package com.example.bridge;

import com.example.builder.Build;
import com.example.builder.Build1;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class Main {

    public static void main(String[] args) throws Exception {
        Car car = new Car();
        SuperCar superCar = new SuperCar();
        Implementor implementor = new CarImplementor();
        car.setImplementor(implementor);
        car.showName();
        superCar.setImplementor(implementor);
        superCar.showName();

        Add add = new Add();
        Implementor addImplementor = new AddImplementor();
        add.setImplementor(addImplementor);
        int result = add.add(1,5);
        System.out.println("result:"+result);
    }


}
