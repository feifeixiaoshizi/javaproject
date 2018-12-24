package com.example.singleton;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 无锁单例，借助CAS实现。
 * while VS. for
 * 　在编程中，我们常常需要用到无限循环，常用的两种方法是while (1) 和 for (；；)。这两种方法效果完全一样，但那一种更好呢？让我们看看它们编译后的代码：
 * 编译前              编译后
 * while (1)；         mov eax,1
 *                    test eax,eax
 *                   je foo+23h
 *                   jmp foo+18h
 *
 * 编译前              编译后
 * for (；；)；          jmp foo+23h
 * 一目了然，for (；；)指令少，不占用寄存器，而且没有判断跳转，比while (1)好。
 */
public class SingletonAtomic {
    private static final AtomicReference<SingletonAtomic> INSTANCE = new AtomicReference<>();

    public static SingletonAtomic getInstance() {
        //for(; ;)和while（true）的比较，for（;;）比while（true效率更高）
        for (; ; ) {
            SingletonAtomic current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new SingletonAtomic();
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    private SingletonAtomic() {
    }
}
