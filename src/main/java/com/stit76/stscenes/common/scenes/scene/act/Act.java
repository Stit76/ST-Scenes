package com.stit76.stscenes.common.scenes.scene.act;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.stit76.stscenes.common.scenes.scene.act.acts.TellAct;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.resources.ResourceLocation;
import com.stit76.stscenes.common.scenes.scene.act.acts.FollowUpAct;

import java.util.UUID;

public abstract class Act {
    public UUID entityUUID = UUID.fromString("380df991-f603-344c-a090-369bad2a924a");
    public String object = "Object";
    public String act = "Act";
    public String arg = "Argument";
    public int delay = 0;
    public abstract ActType<?> type();

    public boolean start(){
        return this.action();
    }

    protected boolean action() {
        return false;
    }


    public UUID getEntityUUID() {
        return entityUUID;
    }
    public String getEntityUUIDfromString(){
        return entityUUID.toString();
    }

    public String getObject() {
        return object;
    }

    public String getAct() {
        return act;
    }

    public String getArg() {
        return arg;
    }

    public int getDelay() {
        return delay;
    }
}



