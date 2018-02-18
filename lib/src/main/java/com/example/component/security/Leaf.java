package com.example.component.security;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class Leaf extends Component {
    public Leaf(String name) {
        super(name);
    }

    @Override
    public void doSonthing() {
        System.out.println("i am a leaf !");
    }
}
