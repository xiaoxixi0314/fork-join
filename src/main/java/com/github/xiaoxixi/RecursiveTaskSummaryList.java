package com.github.xiaoxixi;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class RecursiveTaskSummaryList{

    private static class SummaryListTask extends RecursiveTask<Long> {

        private int THREHOLD = 10000; // 阈值
        private int start;
        private int end;
        private Long[] summaryList;

        public SummaryListTask(int start, int end, Long[] summaryList) {
            this.start = start;
            this.end = end;
            this.summaryList = summaryList;
        }

        @Override
        protected Long compute() {
            Long sum = 0L;
            boolean needFork =  end - start > THREHOLD;
            if(needFork) {
                int middle = (start + end)/2;
                SummaryListTask leftTask = new SummaryListTask(start, middle, summaryList);
                SummaryListTask rightTask = new SummaryListTask(middle, end, summaryList);
                leftTask.fork();
                rightTask.fork();
                Long leftResult = leftTask.join();
                Long rightResult = rightTask.join();
                sum = leftResult + rightResult;
            } else {
                for (int i = start; i < end; i++) {
                    sum += summaryList[i];
                }
            }
            return sum;
        }

    }

    public static void main(String[] args){
        ForkJoinPool forkJoinPool = new ForkJoinPool(8);
        int countList = 10000000;
        Long[] summary = new Long[countList];
        Long start = System.currentTimeMillis();
        for (int i = 1; i <= countList; i ++) {
            summary[i-1] = Long.valueOf(i);
        }
        System.out.println("summary list init over, cost:" + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        SummaryListTask task = new SummaryListTask(0, summary.length, summary);
        Long sum = forkJoinPool.invoke(task);
        System.out.println("summary calc use fork join over, cost:" + (System.currentTimeMillis() - start) + "ms, result is:" + sum);

    }
}
