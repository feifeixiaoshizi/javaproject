package com.example.responsibilitychain.collection;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class Filter1 implements Filter {
    @Override
    public void doFilter(String input, FilterChain chain) {
        input+="1";
        chain.doNextFilter(input,chain);
    }
}
