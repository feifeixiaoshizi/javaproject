package com.example.responsibilitychain.collection;

/**
 * Created by Administrator on 2017/12/20 0020.
 *
 * 1: 在接口中定义行为
 * 2：传递的工作交给FilterChain来实现（集合遍历）。
 * 3：注意是递归的执行。
 *
 */

public interface Filter {

    public void doFilter(String input ,FilterChain chain);
}
