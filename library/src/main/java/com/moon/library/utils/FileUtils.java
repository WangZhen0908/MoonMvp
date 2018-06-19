package com.moon.library.utils;

import java.io.File;

public final class FileUtils {

    public static boolean deleteAllFiles(String dir) {

        if (dir == null) {
            return false;
        }

        File file = new File(dir);
        if (!file.isDirectory()) {
            return false;
        }

        File[] files = file.listFiles();

        if (files == null) {
            return false;
        }

        for (File f : files) {
            f.delete();
        }

        return file.exists() && file.delete();

    }

}
