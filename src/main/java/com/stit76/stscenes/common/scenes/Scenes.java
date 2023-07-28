package com.stit76.stscenes.common.scenes;

import com.stit76.stscenes.common.scenes.scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Scenes {
    public static List<Scene> sceneList = new ArrayList<>();
    public static Scene sceneOnUUID(UUID uuid){
        for (int i = 0; i < sceneList.size(); i++) {
            if(sceneList.get(i).UUID == uuid){
                return sceneList.get(i);
            }
        }
        return null;
    }
    public static UUID UUIDGenerator(){
        return UUID.randomUUID();
    }
}
