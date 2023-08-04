package com.stit76.stscenes.common.scenes.scene.trigger;

import com.stit76.stscenes.common.scenes.scene.Scene;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class Trigger {
    public String name = "New trigger";
    private boolean сanUse = true;
    public abstract TriggerType<?> type();
    public boolean isTrue(MinecraftServer server){
        if(update(server) & сanUse){
            return true;
        }
        return false;
    }
    protected boolean update(MinecraftServer server) {
        return false;
    }
    public boolean isCanUse(){
        return this.сanUse;
    }
    protected void setCanUse(boolean bool){
        this.сanUse = bool;
    }
    public void deactivate(){setCanUse(false);}
    public void activate(){setCanUse(true);}
}
