package com.stit76.stscenes.client;
import com.stit76.stscenes.STScenes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {
    public static List<ResourceLocation> getAllPNGFilesInFolder() {
        List<ResourceLocation> files = new ArrayList<>();

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            ResourceLocation fileLocation = new ResourceLocation(STScenes.MODID, "textures/file_" + i + ".png");
            if (FileUtils.class.getResource("/" + fileLocation.getPath()) != null) {
                files.add(fileLocation);
            } else {
                break;
            }
        }

        return files;
    }
}
