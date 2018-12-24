package com.example.adapter;

import com.example.adapter.adapter.interfaces.IStudy;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class Student implements IStudy {
    public  void  job(){
        System.out.println("我是一个学生！");
    }

    @Override
    public void study() {
        System.out.println("i am a student, i will study good!");
    }
}
