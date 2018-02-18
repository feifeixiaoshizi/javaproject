package com.example.observer.withgneric1;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class DataObserver implements IObserver{

    @Override
    public void update(Observable observable, Object arg){



    };

    public void onChanged() {
        // Do nothing
    }

    public void onItemRangeChanged(int positionStart, int itemCount) {
        // do nothing
    }

    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        // fallback to onItemRangeChanged(positionStart, itemCount) if app
        // does not override this method.
        onItemRangeChanged(positionStart, itemCount);
    }

    public void onItemRangeInserted(int positionStart, int itemCount) {
        // do nothing
    }

    public void onItemRangeRemoved(int positionStart, int itemCount) {
        // do nothing
    }

    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        // do nothing
    }
}
