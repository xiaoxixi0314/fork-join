package com.github.xiaoxixi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class RecursiveTaskSearchList {


    private static class SearchTask extends RecursiveTask<Integer> {

        private static final int THRESHOLD_LENGTH = 1000;

        private int start;
        private int end;
        private Long searchValue;
        private List<Long> searchList;


        public SearchTask(int start, int end, Long searchValue, List<Long> searchList) {
            this.start = start;
            this.end = end;
            this.searchValue = searchValue;
            this.searchList = searchList;
        }

        @Override
        protected Integer compute() {
            if (end - start <= THRESHOLD_LENGTH) {
                // 如果是有序的，可以用二分法
                for(int i = start; i < end; i++) {
                    if (Objects.equals(searchValue, searchList.get(i))) {
                        System.out.println("get the index" + i);
                        return  i;
                    }
                }
                return -1;
            } else {
                int middle = (start + end) /2;
                SearchTask leftTask = new SearchTask(start, middle, searchValue, searchList);
                SearchTask rightTask = new SearchTask(middle, end, searchValue, searchList);
                leftTask.fork();
                rightTask.fork();
                int resultLeft = leftTask.join();
                int resultRight = rightTask.join();
                if (resultLeft >= 0) {
                    return  resultLeft;
                }
                if (resultRight >= 0) {
                    return resultRight;
                }
                return -1;
            }
        }
    }

    public  static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(8);
        int countList = 10000000;
        List<Long> list = new ArrayList<>();
        Long start = System.currentTimeMillis();
        for (int i = 1; i <= countList; i ++) {
            list.add(Long.valueOf(i));
        }
        System.out.println("summary list init over, cost:" + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        SearchTask task = new SearchTask(0, list.size(), 8900098L, list);
        int result = forkJoinPool.invoke(task);
        System.out.println("summary calc use fork join over, cost:" + (System.currentTimeMillis() - start) + "ms, result is:" + result);
    }
}
