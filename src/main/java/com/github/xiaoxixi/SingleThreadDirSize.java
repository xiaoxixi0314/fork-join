package com.github.xiaoxixi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class SingleThreadDirSize {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleThreadDirSize.class);

    public static long sizeOf(final File file) {
        long size = 0L;
        if (file.isFile()) {
            size =  file.length();
        } else {
            final File[] childrens = file.listFiles();
            for (final File children : childrens) {
                size += sizeOf(children);
            }
        }
        return size;
    }

    private SingleThreadDirSize(){}

    public static void main(String[] args) {
        File file = new File("F:\\Work\\Develop\\fresh-fruit");
        Long start = System.currentTimeMillis();
        long size = sizeOf(file);
        //calc size of file cost:13432 ms, the size of file is:810570987bytes.
        System.out.println("calc size of file cost:" + (System.currentTimeMillis()-start) + " ms, the size of file is:" + size + "bytes.");
    }
}
