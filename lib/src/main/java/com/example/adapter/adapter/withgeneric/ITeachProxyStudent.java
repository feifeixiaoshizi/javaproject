package com.example.adapter.adapter.withgeneric;

import com.example.adapter.adapter.interfaces.IStudy;
import com.example.adapter.adapter.interfaces.ITeach;

/**
 * Created by Administrator on 2017/12/19 0019.
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
