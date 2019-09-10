package com.github.xiaoxixi.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemoV2 {

    private int i = 0;

    Lock lock = new XixiLockV2(); // 可重入锁，不排他，多人
//    Lock lock = new ReentrantLock(); // java实现的可重入锁
    private void incr(){
        lock.lock();
        try {
            // 可写代码块
            i ++;
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args   ){
        LockDemoV2 demo = new LockDemoV2();
        for (int k = 0; k < 4; k++) {
            Thread thread = new Thread(() -> {
               for (int j =0; j < 1000; j++) {
                    demo.incr();
               }
            });
            thread.start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(demo.i);
    }
}
