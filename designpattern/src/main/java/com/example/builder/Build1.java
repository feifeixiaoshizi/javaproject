package com.example.builder;

/**
 * Created by Administrator on 2017/12/19 0019.
 * 在builder的设计模式中：
 * 1：是没有setXX只有getXX
 * 2：需要一个内部类Builder
 * 3：需要提供一个静态方法获取到Builder
 * 4：为了回炉重造，可以在Builder的构造方法里面把该对象传入Builder
 * 5: builder设计模式一般用在不再继承的类上。（******）
 * 6：有继承无Builder，Builder表示就是要创建一个可以使用的具体类。
 *
 */

public class Build1 {
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    private String name;
    private int age;

    private Build1(String name, int age) {
        this.name = name;
        this.age = age;
    }




    public static Builder builder(){
        return  new Builder();
    }


    //把对象再传递给Builder进行回炉重造。（*****）
    public static Builder builder(Build1 build1){
        return  new Builder(build1);
    }


    public static class Builder {
        private String name;
        private int age;

        public Builder() {

        }

        //可以在原来的基础上构建一个构建者对象，可以在原来的基础上重新构建一个对象。（***）
        public Builder(Build1 build){
            //把现有的属性先传递后来。
            name = build.name;
            age = build.age;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }


        public Builder setAge(int age) {
            this.age = age;
            return this;
        }


        public Build1 build (){
            //构建了新的对象，但是新的对象的属性不是空的，而是来自原来对象的属性。（***）
            return  new Build1(this.name,this.age);
        }

    }

}
