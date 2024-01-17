package com.stit76.stscenes.common.scenes.scene.trigger;

import com.stit76.stscenes.common.scenes.scene.Scene;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.UUID;

public abstract class Trigger {
    public String name = "New trigger";
    public UUID uuid;
    private boolean сanUse = true;
    public abstract TriggerType<?> iType();
    public Trigger(){
        uuid = UUID.randomUUID();
    }
    public boolean isTrue(Scene scene, MinecraftServer server, Level level){
        if(update(server,level) && сanUse){
            if(getLastPlayer(server,level) != null){
                scene.setLastPlayer(getLastPlayer(server,level));
            }
            return true;
        }
        return false;
    }

    protected abstract UUID getLastPlayer(MinecraftServer server,Level level);

    public abstract String type();
    protected abstract boolean update(MinecraftServer server, Level level);
    public String getName(){
        return this.name;
    }
    public UUID getUUID() {
        return uuid;
    }
    public String getStringUUID() {
        return uuid.toString();
    }
    public boolean isCanUse(){
        return this.сanUse;
    }
    public void setCanUse(boolean bool){
        this.сanUse = bool;
    }
    public void deactivate(){setCanUse(false);}
    public void activate(){setCanUse(true);}

}
