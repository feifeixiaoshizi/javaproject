package com.example.adapter.adapter.impltype;

import com.example.adapter.adapter.interfaces.IStudy;
import com.example.adapter.adapter.interfaces.ITeach;

/**
 * Created by Administrator on 2017/12/19 0019.
 *
 *
 *适配器实现了目标接口，这样适配器也算是目标接口类型了。我们面向适配器即可。
 *
 * 适配器的核心就在于实现目标接口，封装被适配对象，目的就是为了间接使用被适配者的方法。
 *
 * 目标接口：
 * 1：存在 （实现接口，封装被适配者）
 * 2：不存在 （就是封装被适配的对象，屏蔽掉变化的被适配者）
 *
 * 被适配对象：（入口和出口连个角度）
 * 1：不同的类型对象适配为同一类型的对象。
 * （Springmvc中HandlerAdapter可以看成把不同的类型的Handler对象，都转化为同一类型HandlerAdapter的统一的处理方法）
 * （ps：统一了出口，不管传递过来的是谁，都要按照handle（）方法来处理）
 * 2：同一类型的对象适配为不同类型的对象。
 * （retrofit中的call对象适配为不同类型的对象）
 * （ps：统一了入口，传入的一定是Call类型的对象，可以转化为不同类型的对象）
 *
 *
 */

//经典的实现方式（实现+聚合）
public class TeacherAdapter implements ITeach {
    public IStudy getiStudy() {
        return iStudy;
    }

    public void setiStudy(IStudy iStudy) {
        this.iStudy = iStudy;
    }

    IStudy iStudy;
    @Override
    public void teach() {
        iStudy.study();
    }
}
