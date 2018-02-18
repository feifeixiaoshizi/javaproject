package com.example.observer.withgneric1;

/**
 * Created by jianshengli on 2018/2/16.
 * 负责实现了IObserver接口的观察者。具体的扩展子类可以根据自己的需求再扩展
 *
 * //1.泛型定义在哪里就在哪里传入这是类型值，在父类定义就在父类的类名后面传入，即使子类继承自父类也不能替父类传递。
 *  InterfaceAdapterGenericFactory extends InterfaceAdapterGeneric.Factory<IStudy, ITeach>（√）
 *  InterfaceAdapterGenericFactory<IStudy, ITeach> extends InterfaceAdapterGeneric.Factory（X）
 *
 *   //2.表示明确父类中泛型的具体类型，但是子类自己也有自己的泛型
 *   InterfaceAdapterGenericFactory<T, R> extends InterfaceAdapterGeneric.Factory<IStudy, ITeach>(√)
 *
 *   //3.表示子类定义时也不能明确父类的泛型的具体的类型，所以子类也有继续声明父类中声明的类型，到使用时在明确。
 *   InterfaceAdapterGenericFactory<T, R> extends InterfaceAdapterGeneric.Factory<T, R>(√)
 *
 *   //4.在定义的时候不能使用泛型通配符？，在具体使用（实例化的时候才可以使用泛型通配符），在子类继承父类的时候，如果能确定父类中的泛型的具体类型，
 *   就给父类传递具体的类型，如果不能确定，就在子类中也定义泛型类型，然后把变量传递给父类的泛型类型变量，在父类中的泛型变量中不能传递泛型通配符？。
 *
 *   public abstract class BaseObservable<T extends IObserver> extends Observable<T>(√)
 *   public abstract class BaseObservable extends Observable<IObserver>(√)
 *   public abstract class BaseObservable  extends Observable<？ extends IObserver>(x)
 *
 *
 *
 *
 *
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
 *
 *
 *
 */


//通过的具体的实现类去明确泛型的具体类型，定义一个通用的带泛型的观察者模式。
public abstract class BaseObservable<T extends IObserver> extends Observable<T> {

    public void notifyObserver() {
        //线程安全问题，一定是用--来进行遍历的操作。
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).update(this, null);
        }


    }

    //能用--不用++
    public void notifyObserver(Object args) {
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).update(this, null);
        }


    }


}
