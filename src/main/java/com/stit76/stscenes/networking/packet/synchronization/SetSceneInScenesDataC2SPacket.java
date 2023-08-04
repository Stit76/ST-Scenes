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
import java.util.function.Supplier;

public class SetSceneInScenesDataC2SPacket {
    private Scene sceneAdded;
    private int index;
    private List<Scene> sceneList;
    public SetSceneInScenesDataC2SPacket(int index, Scene sceneAdded, List<Scene> sceneList){
        this.index = index;
        this.sceneAdded = sceneAdded;
        this.sceneList = sceneList;
    }

    public SetSceneInScenesDataC2SPacket(FriendlyByteBuf buf){
        this.index = buf.readInt();
        this.sceneAdded = buf.readJsonWithCodec(Scene.SCENE_CODEC);
        this.sceneList = buf.readJsonWithCodec(ScenesData.CODEC_SCENES);
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(this.index);
        buf.writeJsonWithCodec(Scene.SCENE_CODEC,this.sceneAdded);
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
                scenesData.getScenesList().set(this.index,this.sceneAdded);
                scenesData.setDirty();
                SimpleNetworkWrapper.sendToAllPlayer(new LoadScenesListToClientC2SPacket(scenesData.getScenesList()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
