package com.example.component.leafmayexception;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/18 0018.
 *
 * 1: 线程安全
 * 2：管理子Component(增 删 改 查)
 */

public class Composite extends Component {

    private ArrayList<Component> components = new ArrayList<>();


    public Composite(String name) {
        super(name);
    }

    @Override
    public void doSonthing() {
        for (Component component :components){
            component.doSonthing();
        }
    }

    public synchronized void  addChild(Component component){
        components.add(component);
    }

    public synchronized void removeChild(int index){
        components.remove(index);
    }

    public synchronized void removeChild(Component component){
        components.remove(component);
    }

    public synchronized Component getChildAt(int index){
        if(index < components.size()){
            return  components.get(index);
        }
        return null;
    }


}
