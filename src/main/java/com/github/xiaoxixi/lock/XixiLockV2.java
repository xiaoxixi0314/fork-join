package com.github.xiaoxixi.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * 模拟锁的实现， 可重入锁
 * 公平锁：先进入的线程应该先执行
 * 非公平锁：先进入的不一定先执行
 * 本例子是非公平锁
 *
 * synchronized  与 lock 有什么区别
 * CAS：比较和交换
 *
 * synchronized：关键字，c语言实现，CAS机制
 * lock: jdk 实现，CAS机制
 *
 *
 */
public class XixiLockV2 implements Lock {


    // 原子操作
    private AtomicReference<Thread> owner = new AtomicReference<>();

    // 线程等待列表，用于资源被占用时的等待线程列表
    private LinkedBlockingQueue<Thread> waiter = new LinkedBlockingQueue<>();
    /**
     * 实现加锁
     * 可重入
     * 所有线程都可抢某个资源
     */
    @Override
    public void lock() {
        // 当前资源有没有被抢
        // 如果是null，则占用,如果占用不成功，则放入等待列表
        while(!owner.compareAndSet(null, Thread.currentThread())){
            //
            waiter.add(Thread.currentThread());
            // 让没有抢到资源的线程等待
            LockSupport.park();
            // 说明该线程被唤醒了，可以从等待列表中删除了
            waiter.remove(Thread.currentThread());
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
        // 如果当前线程持有资源，则释放,唤醒等待的线程
        if(owner.compareAndSet(Thread.currentThread(), null)){
            // 通知等待的线程
            Object[] waiters = waiter.toArray();
            for (Object object: waiters) {
                Thread next = (Thread) object;
                // 唤醒等待列表
                LockSupport.unpark(next);
            }
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
