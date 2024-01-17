package com.stit76.stscenes.common.scenes.scene.act;

import com.stit76.stscenes.common.scenes.scene.Scene;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;

public abstract class Act {
    public int delay = 0;
    public boolean start(MinecraftServer server, Level level, Scene scene){
        return this.action(server,scene);
    }

    protected abstract boolean action(MinecraftServer server, Scene scene);
    public int getDelay() {
        return delay;
    }
    public abstract ActType<?> type();
    public void onDelayGoing(MinecraftServer server,Scene scene){}
    public void onDelayEnd(MinecraftServer server,Scene scene){}

}
