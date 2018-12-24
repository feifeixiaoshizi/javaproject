package com.example.component.leafmayexception;

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

    @Override
    public void addChild(Component component) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeChild(int index) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeChild(Component component) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    @Override
    public Component getChildAt(int index) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }
}
