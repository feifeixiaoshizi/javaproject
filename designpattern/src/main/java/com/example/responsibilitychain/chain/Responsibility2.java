package com.example.responsibilitychain.chain;

import com.example.responsibilitychain.chain.IResponsibility;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class Responsibility2 extends IResponsibility {

    @Override
    public void handle(String str) {
        System.out.println("i am a r2!");
        if(successor!=null){
            successor.handle(str);
        }
    }
}
