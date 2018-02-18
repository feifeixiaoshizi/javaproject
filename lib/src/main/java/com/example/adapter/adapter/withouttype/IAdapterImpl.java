package com.example.adapter.adapter.withouttype;

import com.example.adapter.adapter.interfaces.IStudy;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class IAdapterImpl implements IAdapter {

    //封装被适配者
   public IStudy iStudy;
    @Override
    public void handle(Object value) {
       iStudy.study();
    }
}
