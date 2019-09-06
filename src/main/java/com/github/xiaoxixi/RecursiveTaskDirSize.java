package com.github.xiaoxixi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class RecursiveTaskDirSize {


    public static class DirSizeTask extends RecursiveTask<Long> {

        private final File file;

        public DirSizeTask(final File file) {
            this.file = Objects.requireNonNull(file);
        }

        @Override
        protected Long compute() {
            if (file.isFile()) {
                return file.length();
            }
            final List<DirSizeTask> tasks = new ArrayList<>();
            final File[] childs = file.listFiles();
            for (final File child : childs) {
                final DirSizeTask task = new DirSizeTask(child);
                task.fork();
                tasks.add(task);
            }
            long size = 0;
            for (final DirSizeTask task : tasks) {
                size += task.join();
            }
            return size;
        }
    }

    public static Long sizeOf(final File file) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        try {
            return forkJoinPool.invoke(new DirSizeTask(file));
        } finally {
            forkJoinPool.shutdown();
        }
    }

    public static void main(String[] args) {
        File file = new File("F:\\Work\\Develop\\fresh-fruit");
        Long start = System.currentTimeMillis();
        long size = sizeOf(file);
        //calc size of file cost:2324 ms, the size of file is:810570987bytes.
        System.out.println("calc size of file cost:" + (System.currentTimeMillis()-start) + " ms, the size of file is:" + size + "bytes.");
    }
}