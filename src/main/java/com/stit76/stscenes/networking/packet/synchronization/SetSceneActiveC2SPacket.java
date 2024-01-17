package com.stit76.stscenes.networking.packet.synchronization;

import com.stit76.stscenes.common.scenes.data.ScenesData;
import com.stit76.stscenes.common.scenes.scene.Scene;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class SetSceneActiveC2SPacket {
    private boolean active;
    private int index;
    private List<Scene> sceneList;
    public SetSceneActiveC2SPacket(boolean active, int index,List<Scene> sceneList){
        this.active = active;
        this.index = index;
        this.sceneList = sceneList;
    }

    public SetSceneActiveC2SPacket(FriendlyByteBuf buf){
        this.active = buf.readBoolean();
        this.index = buf.readInt();
        this.sceneList = buf.readJsonWithCodec(ScenesData.CODEC_SCENES);
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBoolean(active);
        buf.writeInt(this.index);
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
                if(active){
                    scenesData.getScenesList().get(this.index).start();
                } else {
                    scenesData.getScenesList().get(this.index).stop();
                }
                scenesData.setDirty();
                SimpleNetworkWrapper.sendToAllPlayer(new LoadScenesListToClientC2SPacket(scenesData.getScenesList()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }

}
