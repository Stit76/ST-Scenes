package com.stit76.stscenes.utils;

import com.stit76.stscenes.STScenes;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
    public static List<JarEntry> getDirectoryFilesInJar(String folderPath,String folderInJar) {
        String jarFilePath = folderPath;
        List<JarEntry> fileList = new ArrayList<>();
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                int index = entry.getName().indexOf(folderInJar);
                if(index >= 0){
                    if(entry.isDirectory()){
                        if(StringUtils.countMatches(entry.getName(), String.valueOf('/')) == StringUtils.countMatches(folderInJar, String.valueOf('/')) + 1){
                            fileList.add(entry);
                        }
                    } else {
                        if(StringUtils.countMatches(entry.getName(), String.valueOf('/')) == StringUtils.countMatches(folderInJar, String.valueOf('/'))){
                            fileList.add(entry);
                        }
                    }
                }
            }
            jarFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }
    public static boolean isJarPath(String path){
        return StringUtils.right(path, 4).equals(".jar");
    }
    public static Path getSkinDirectory(){
        String modId = STScenes.MODID;
        ModContainer modContainer = ModList.get().getModContainerById(modId)
                .orElseThrow(() -> new RuntimeException("Error 195! Mod folder not found!"));
        Path modPath = modContainer.getModInfo().getOwningFile().getFile().getFilePath();
        if(!isJarPath(modPath.toString())){
            Path assetsPath = modPath.resolve("assets");
            Path stscenesPath = assetsPath.resolve("stscenes");
            Path texturesPath = stscenesPath.resolve("textures");
            Path widePlayerPath = texturesPath.resolve("entity").resolve("player");
            return widePlayerPath;
        }
        return modPath;
    }
}
