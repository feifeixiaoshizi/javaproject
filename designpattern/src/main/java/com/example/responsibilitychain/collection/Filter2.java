package com.example.responsibilitychain.collection;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class Filter2 implements Filter {
    @Override
    public void doFilter(String input, FilterChain chain) {
        input+="2";
        chain.doNextFilter(input,chain);
    }
}
