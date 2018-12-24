package com.example.builder;

/**
 * Created by Administrator on 2017/12/19 0019.
 *
 * 在builder的设计模式中：
 * 1：是没有setXX只有getXX，setXX的操作都交给Builder去完成。
 * 2：需要一个内部类Builder
 * 3：需要提供一个静态方法获取到Builder
 * 4：为了回炉重造，可以在Builder的构造方法里面把该对象传入Builder
 * 5: builder设计模式一般用在不再继承的类上。（******）
 * 6：有继承无Builder，Builder表示就是要创建一个可以使用的具体类。
 */

public class Build {
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    private String name;
    private int age;

    public Build(String name, int age) {
        this.name = name;
        this.age = age;
    }



    public Build(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    public static Builder builder(){
        return  new Builder();
    }


    public static class Builder {
        private String name;
        private int age;

        public Builder() {

        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }


        public Builder setAge(int age) {
            this.age = age;
            return this;
        }


        public Build build (){
            return  new Build(this);
        }

    }

}
