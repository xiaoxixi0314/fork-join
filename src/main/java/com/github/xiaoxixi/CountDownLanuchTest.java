package com.github.xiaoxixi;

import java.util.concurrent.CountDownLatch;

public class CountDownLanuchTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch  = new CountDownLatch(10);
        try {
            System.out.println("start create thread");
            for (int i = 1; i <= 10; i++) {
                Thread thread = new Thread(() -> {
                    try {
                        System.out.println(Thread.currentThread().getName());
                        countDownLatch.countDown();
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            }
            System.out.println("create thread over");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
