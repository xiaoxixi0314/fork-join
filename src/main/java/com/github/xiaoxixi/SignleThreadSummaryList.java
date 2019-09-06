package com.github.xiaoxixi;

public class SignleThreadSummaryList {

    public static void main(String[] args){
        int countList = 10000000;
        Long[] summary = new Long[countList];
        Long start = System.currentTimeMillis();
        for (int i = 1; i <= countList; i ++) {
            summary[i-1] = Long.valueOf(i);
        }
        System.out.println("summary list init over, cost:" + (System.currentTimeMillis() - start) + "ms");

        Long sumSingle = 0L;
        start = System.currentTimeMillis();
        for (int i = 0; i < countList; i ++) {
            sumSingle += summary[i];
        }
        System.out.println("summary calc over, cost:" + (System.currentTimeMillis() - start) + "ms, result is:" + sumSingle);

    }
}
