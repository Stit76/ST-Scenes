package com.stit76.stscenes.common.scenes.data;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.stit76.stscenes.common.scenes.scene.Scene;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class    ScenesData extends SavedData {
    private static Logger LOGGER = LogManager.getLogger();
    public static final Codec<List<Scene>> CODEC_SCENES = Scene.SCENE_CODEC.listOf().xmap(ArrayList::new,ImmutableList::copyOf);
    private List<Scene> scenes = new ArrayList<>();

    public void setScenesList(List<Scene> scenes) {
        this.scenes = scenes;
        this.setDirty();
    }

    public List<Scene> getScenesList() {
        return scenes;
    }


    public static ScenesData create() {
        return new ScenesData();
    }
    public static ScenesData load(CompoundTag tag) {
        ScenesData data = create();
        DataResult<List<Scene>> result = CODEC_SCENES.parse(NbtOps.INSTANCE,tag.get("scenes_list"));
        result
                .resultOrPartial(errorMessage -> LOGGER.error("Decode error!"))
                .ifPresent(decodedObject -> data.scenes = decodedObject);
        return data;
    }
    @Override
    public CompoundTag save(CompoundTag tag) {
        final Tag[] tag1 = {null};
        DataResult<Tag> result = CODEC_SCENES.encodeStart(NbtOps.INSTANCE,this.scenes);
        result
                .resultOrPartial(errorMessage -> LOGGER.error("Encode error!"))
                .ifPresent(encodedObject -> tag1[0] = encodedObject);
        tag.put("scenes_list",tag1[0]);
        return tag;
    }
    public static ScenesData manage(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(ScenesData::load, ScenesData::create, "scenes_data");
    }
}
