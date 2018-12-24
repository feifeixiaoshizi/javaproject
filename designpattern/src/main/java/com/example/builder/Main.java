package com.example.builder;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class Main {

    public static void main(String[] args) throws Exception {

        Build.Builder builder = Build.builder();
        Build build = builder.setAge(12).setName("张三").build();
        System.out.println("name:"+build.getName()+"age:"+build.getAge());

        Build1.Builder builder1 = Build1.builder();
        Build1 build1 = builder1.setAge(13).setName("zhangsan").build();
        System.out.println("name:"+build1.getName()+"age:"+build1.getAge());

        //通过把build1实例对象传递给Builder，再次修改这个实例对象。
        Build1.Builder builder2 = Build1.builder(build1);
        Build1 build2 = builder2.setAge(14).build();
        System.out.println("name:"+build2.getName()+"age:"+build2.getAge());


    }


}
