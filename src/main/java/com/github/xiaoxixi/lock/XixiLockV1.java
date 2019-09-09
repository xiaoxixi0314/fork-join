package com.github.xiaoxixi.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 模拟锁的实现
 * 线程不安全，不可用
 */
public class XixiLockV1 implements Lock {

    // 多线程情况下，thread需要原子操作，线程不安全
    Thread thread = null;

    /**
     * 实现加锁
     * 可重入
     * 所有线程都可抢某个资源
     */
    @Override
    public void lock() {
        // 当前资源有没有被抢
        if(thread == null) {
            thread = Thread.currentThread();
        }


    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        if (thread.equals(Thread.currentThread())) {
            thread = null;
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
