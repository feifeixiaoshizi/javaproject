package com.example.singleton;

/**
 * 单例工具类
 *
 * @param <T>
 */
public abstract class Singleton<T> {
    private T instance;

    protected abstract T create();

    public synchronized T get() {
        if (instance == null) {
            instance = create();
        }
        return instance;
    }
}
