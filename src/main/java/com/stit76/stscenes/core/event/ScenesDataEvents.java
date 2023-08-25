package com.stit76.stscenes.core.event;

import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.common.scenes.data.ScenesData;
import com.stit76.stscenes.common.scenes.scene.Scene;
import com.stit76.stscenes.common.scenes.scene.trigger.TouchTrigger;
import com.stit76.stscenes.common.scenes.scene.trigger.Trigger;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.synchronization.LoadScenesListToClientC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = STScenes.MODID)
public class ScenesDataEvents {
    private static void sceneActiveAction(Scene scene){
        scene.tick++;
        if(scene.activeAct != 0){
            if(scene.tick >= scene.acts.get(scene.activeAct-1).delay){
                if(scene.activeAct < scene.acts.size()){
                    if(scene.acts.get(scene.activeAct).start()){
                        scene.activeAct++;
                    } else {
                        scene.LOGGER.error("The scene called \""+scene.getName()+"\" is interrupted by action \""+scene.activeAct+"\"");
                        scene.stop();
                    }
                    scene.tick = 0;
                } else {
                    scene.stop();
                }
            }
        } else {
            if(scene.tick >= 0){
                if(scene.activeAct < scene.acts.size()){
                    if(scene.acts.get(scene.activeAct).start()){
                        scene.activeAct++;
                    } else {
                        scene.LOGGER.error("The scene called \""+scene.getName()+"\" is interrupted by action \""+scene.activeAct+"\"");
                        scene.stop();
                    }
                    scene.tick = 0;
                } else {
                    scene.tick = 0;
                    scene.activeAct = 0;
                    scene.stop();
                }
            }
        }
    }
    @SubscribeEvent
    public static void OnServerTick(TickEvent.ServerTickEvent e){
        ScenesData scenesData = ScenesData.manage(e.getServer());
        for (int i = 0; i < scenesData.getScenesList().size(); i++) {
            Scene scene = scenesData.getScenesList().get(i);
            for (int j = 0; j < scene.triggers.size(); j++) {
                Trigger trigger = scene.triggers.get(j);
                if(trigger.isTrue(e.getServer())){
                    scene.start();
                    scenesData.setDirty();
                }
            }
            if(scene.isActive()){
                SimpleNetworkWrapper.sendToAllPlayer(new LoadScenesListToClientC2SPacket(scenesData.getScenesList()));
                sceneActiveAction(scene);
                scenesData.setDirty();
                SimpleNetworkWrapper.sendToAllPlayer(new LoadScenesListToClientC2SPacket(scenesData.getScenesList()));
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent serverStartedEvent){
        Player logPlayer = serverStartedEvent.getEntity();
        MinecraftServer server = logPlayer.getServer();
        ServerPlayer serverLogPlayer = server.getPlayerList().getPlayer(logPlayer.getUUID());
        ScenesData scenesData = ScenesData.manage(server);
        SimpleNetworkWrapper.sendToPlayer(new LoadScenesListToClientC2SPacket(scenesData.getScenesList()),serverLogPlayer);
    }
}
