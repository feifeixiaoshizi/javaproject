package com.example.adapter.adapter.withmethod;

import com.example.adapter.adapter.interfaces.IStudy;
import com.example.adapter.adapter.interfaces.ITeach;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class InterfaceAdapterFactory extends InterfaceAdapter.Factory {
    @Override
    public InterfaceAdapter get() {

        return new InterfaceAdapter() {
            @Override
            public ITeach adapt(IStudy iStudy) {
                return new ITeachProxyStudent(iStudy);
            }
        };
    }
}
