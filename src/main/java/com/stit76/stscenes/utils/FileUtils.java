package com.stit76.stscenes.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    public static List<File> getDirectoryFiles(String folderPath) {;
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                return Arrays.stream(files).toList();
            }
        }
        return new ArrayList<>();
    }
}
