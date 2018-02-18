package com.example.adapter;

import com.example.adapter.adapter.impltype.TeacherAdapter;
import com.example.adapter.adapter.interfaces.ITeach;
import com.example.adapter.adapter.withgeneric.InterfaceAdapterGeneric;
import com.example.adapter.adapter.withgeneric.InterfaceAdapterGenericFactory;
import com.example.adapter.adapter.withmethod.InterfaceAdapter;
import com.example.adapter.adapter.withmethod.InterfaceAdapterFactory;
import com.example.adapter.adapter.withouttype.IAdapter;
import com.example.adapter.adapter.withouttype.IAdapterImpl;
import com.example.adapter.adapter.interfaces.IStudy;
import com.sun.org.apache.bcel.internal.generic.ISUB;

import java.util.List;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class Main {

    public static void main(String[] args) throws Exception {

        IStudy iStudy = new Student();

        //统一出口（handle（）），不实现目标接口的适配器（适配器即可是目标接口）。适配器封装了具体的被适配者。
        //不再面向具体变化的被适配者，而是统一面对不变的适配方法。
        IAdapter iAdapter = getAdapter(iStudy);
        iAdapter.handle(null);

        //通过适配器把一个对象变化为另外一个对象。（实际上是另外一个对象聚合了该对象）
        InterfaceAdapterFactory interfaceAdapterFactory = new InterfaceAdapterFactory();
        InterfaceAdapter interfaceAdapter = interfaceAdapterFactory.get();
        ITeach iTeach = interfaceAdapter.adapt(iStudy);
        iTeach.teach();

        //通过适配器把一个对象变化为另外一个对象。（实际上是另外一个对象聚合了该对象）
        InterfaceAdapterGenericFactory interfaceAdapterGenericFactory = new InterfaceAdapterGenericFactory();
        InterfaceAdapterGeneric<IStudy,ITeach> interfaceAdapterGeneric =  interfaceAdapterGenericFactory.get();
        ITeach iTeach1 =  interfaceAdapterGeneric.adapt(iStudy);
        iTeach1.teach();


        //适配器中没有方法，但是实现了目标接口。为了类型变换。
        TeacherAdapter teacherAdapter = new TeacherAdapter();
        teacherAdapter.setiStudy(iStudy);
        ITeach iTeach2= teacherAdapter;
        iTeach2.teach();



    }


    public static IAdapter getAdapter(IStudy iStudy) {
        IAdapterImpl iAdapter = new IAdapterImpl();
        iAdapter.iStudy = iStudy;
        return iAdapter;
    }




}
