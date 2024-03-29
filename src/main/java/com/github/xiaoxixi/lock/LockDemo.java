package com.github.xiaoxixi.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    private int i = 0;

    Lock lock = new ReentrantLock(); // 可重入锁，不排他，多人
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
        LockDemo demo = new LockDemo();
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
