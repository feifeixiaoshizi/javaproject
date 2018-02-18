package com.example.adapter.adapter.withgeneric;

import com.example.adapter.adapter.interfaces.IStudy;
import com.example.adapter.adapter.interfaces.ITeach;

/**
 * Created by Administrator on 2017/12/19 0019.
 * <IStudent,ITeach>
 *
 *  //1.泛型定义在哪里就在哪里传入这是类型值，在父类定义就在父类的类名后面传入，即使子类继承自父类也不能替父类传递。
 *  InterfaceAdapterGenericFactory extends InterfaceAdapterGeneric.Factory<IStudy, ITeach>（√）
 *  InterfaceAdapterGenericFactory<IStudy, ITeach> extends InterfaceAdapterGeneric.Factory（X）
 *
 *   //2.表示明确父类中泛型的具体类型，但是子类自己也有自己的泛型
 *   InterfaceAdapterGenericFactory<T, R> extends InterfaceAdapterGeneric.Factory<IStudy, ITeach>(√)
 *
 *   //3.表示子类定义时也不能明确父类的泛型的具体的类型，所以子类也有继续声明父类中声明的类型，到使用时在明确。
 *   InterfaceAdapterGenericFactory<T, R> extends InterfaceAdapterGeneric.Factory<T, R>(√)
 *
 *   泛型要注意定义时和使用时连个阶段。（******）
 *   泛型还有注意整体理解和具体细节理解。
 *   eg：List<Person>      容器A  Person 容器A里面的内容
 *       List<Student>     容器B  Student容器B里面的内容
 *
 *      1：整体上理解
 *      当需要一个 List<Student>容器B时，你就不可以给传递一个List<Person>容器A，
 *      同时当需要一个List<Person>容器A时，你就不可以给传递一个List<Student>容器B。
 *
 *      2：当需要一个 List<?>容器时,表示容器A和容器B都可以接受，就可以传递容器A和容器B了。
 *         当需要一个 List<? extends Person>容器时,表示容器A和容器B都可以接受，也可以传递容器A和容器B了。
 *
 *      3：List<Person>容器A是可以存放任何继承自Person的对象，所以是可以存放Student对象的。
 *
 *      4：容器中的元素是有继承关系的，但是容器与容器之间的继承的关系是通过extends来实现的，List<? extends Person>就可以接受
 *      List<Student>容器，但是List<Person>就不可以接受List<Student>容器。
 *
 *
 *
 */

public class InterfaceAdapterGenericFactory extends InterfaceAdapterGeneric.Factory<IStudy, ITeach> {

    @Override
    public  InterfaceAdapterGeneric<IStudy, ITeach> get() {
        //返回一个适配器对象，在适配器对象的adapt方法里负责真正的适配器的实现，实现接口封装具体对象的操作。（*****）
        return new InterfaceAdapterGeneric<IStudy, ITeach>() {
            @Override
            public ITeach adapt(IStudy iStudy) {
                return new ITeachProxyStudent(iStudy);
            }
        };
    }





}
