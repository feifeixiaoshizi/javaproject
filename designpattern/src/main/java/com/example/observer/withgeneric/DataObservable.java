package com.example.observer.withgeneric;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class DataObservable extends Observable<DataObserver> {
    public boolean hasObservers() {
        return !mObservers.isEmpty();
    }

    public void notifyChanged() {
        // since onChanged() is implemented by the app, it could do anything, including
        // removing itself from {@link mObservers} - and that could cause problems if
        // an iterator is used on the ArrayList {@link mObservers}.
        // to avoid such problems, just march thru the list in the reverse order.
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onChanged();
        }
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        notifyItemRangeChanged(positionStart, itemCount, null);
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
        // since onItemRangeChanged() is implemented by the app, it could do anything, including
        // removing itself from {@link mObservers} - and that could cause problems if
        // an iterator is used on the ArrayList {@link mObservers}.
        // to avoid such problems, just march thru the list in the reverse order.
        //由于mObservers集合的动态变化，可能会造成漏通知和重复通知。（******）
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onItemRangeChanged(positionStart, itemCount, payload);
        }
    }

    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        // since onItemRangeInserted() is implemented by the app, it could do anything,
        // including removing itself from {@link mObservers} - and that could cause problems if
        // an iterator is used on the ArrayList {@link mObservers}.
        // to avoid such problems, just march thru the list in the reverse order.
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onItemRangeInserted(positionStart, itemCount);
        }
    }

    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        // since onItemRangeRemoved() is implemented by the app, it could do anything, including
        // removing itself from {@link mObservers} - and that could cause problems if
        // an iterator is used on the ArrayList {@link mObservers}.
        // to avoid such problems, just march thru the list in the reverse order.
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onItemRangeRemoved(positionStart, itemCount);
        }
    }

    public void notifyItemMoved(int fromPosition, int toPosition) {
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onItemRangeMoved(fromPosition, toPosition, 1);
        }
    }


}
