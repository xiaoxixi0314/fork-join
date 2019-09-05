package com.github.xiaoxixi;

import java.io.File;

public class SigleThreadDirSize {

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


        return 0L;
    }
}
