package com.example.responsibilitychain.chain;

/**
 * Created by Administrator on 2017/12/20 0020.
 *
 *
 *设计模式的顶层一定是抽象的（抽象类/接口）
 *
 * 责任链的设计模式在于：
 * 1：处理事务的抽象方法
 * 2：链子负责连接下一个处理者
 * 3：自己聚合自己类型的对象。
 * 4: 通过链实现责任链的模式，一级一级的传递。
 * 5：抽象的行为。
 */

public abstract class IResponsibility {

    protected  IResponsibility successor;


    public abstract void handle(String str);

    public IResponsibility getNextSuccessor() {
        return successor;
    }

    public void setNextSuccessor(IResponsibility successor) {
        this.successor = successor;
    }
}
