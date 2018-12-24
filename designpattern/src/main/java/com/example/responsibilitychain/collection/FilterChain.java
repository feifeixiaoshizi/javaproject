package com.example.responsibilitychain.collection;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/20 0020.
 *
 * 集合实现方式主要在于index的变化实现Filter的传递。
 * 一定要先改变index的值再执行Filter的doFilter()的方法。
 *
 * 所有的Filter共享一个集合对象。
 *
 */

public class FilterChain {
    private ArrayList<Filter> filters = new ArrayList<>();
    private int index=0;

    public FilterChain addFilter(Filter filter){
        filters.add(filter);
        return this;
    }

    public void doNextFilter(String input ,FilterChain chain){
        System.out.println("input:"+input);
        if(filters.size()==index){
            return;
        }

        //注意递归，注意代码先后顺序。先增加index的值，再执行filter.doFilter(input,chain);
        Filter filter =  filters.get(index);
        index++;
        filter.doFilter(input,chain);

    }

}
