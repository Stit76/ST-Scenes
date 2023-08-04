package com.stit76.stscenes.networking.packet.synchronization;

import com.stit76.stscenes.common.scenes.data.ScenesData;
import com.stit76.stscenes.common.scenes.scene.Scene;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Supplier;

public class SetScenesDataServerC2SPacket {
    private List<Scene> sceneList;
    public SetScenesDataServerC2SPacket(List<Scene> sceneList){
        this.sceneList = sceneList;
    }

    public SetScenesDataServerC2SPacket(FriendlyByteBuf buf){
        this.sceneList = buf.readJsonWithCodec(ScenesData.CODEC_SCENES);
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeJsonWithCodec(ScenesData.CODEC_SCENES,this.sceneList);
    }

    public boolean handler(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Logger LOGGER = LogManager.getLogger();
            try {
                ServerPlayer sender = supplier.get().getSender();
                MinecraftServer server = sender.getServer();
                ScenesData scenesData = ScenesData.manage(server);
                scenesData.setScenesList(this.sceneList);
                LOGGER.info("Saved scenes: " + scenesData.getScenesList().size());
            } catch (Exception e) {
                LOGGER.error("Error saving scenes! Error code: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
