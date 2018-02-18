package com.example.adapter.adapter.withmethod;

import com.example.adapter.adapter.interfaces.IStudy;
import com.example.adapter.adapter.interfaces.ITeach;

/**
 * Created by Administrator on 2017/12/19 0019.
 * 具体的完成适配的适配器类。实现了返回值接口，封装了传递的参数对象。
 *
 */

public class ITeachProxyStudent implements ITeach {
    public IStudy iStudy;
    @Override
    public void teach() {
        iStudy.study();

    }

    public ITeachProxyStudent(IStudy iStudy) {
        this.iStudy = iStudy;
    }
}
