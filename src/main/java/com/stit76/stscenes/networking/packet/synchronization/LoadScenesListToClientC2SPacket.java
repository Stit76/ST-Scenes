package com.stit76.stscenes.networking.packet.synchronization;

import com.stit76.stscenes.client.gui.STScreen;
import com.stit76.stscenes.client.gui.sceneCustomizer.SceneCustomizerScreen;
import com.stit76.stscenes.client.gui.scenesBrowser.ScenesBrowser;
import com.stit76.stscenes.common.item.SceneCustomizer;
import com.stit76.stscenes.common.scenes.data.ScenesData;
import com.stit76.stscenes.common.scenes.scene.Scene;
import com.stit76.stscenes.common.scenes.scene.Scenes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class LoadScenesListToClientC2SPacket {
    private List<Scene> sceneList;
    public LoadScenesListToClientC2SPacket(List<Scene> sceneList){
        this.sceneList = sceneList;
    }

    public LoadScenesListToClientC2SPacket(FriendlyByteBuf buf){
        this.sceneList = buf.readJsonWithCodec(ScenesData.CODEC_SCENES);
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeJsonWithCodec(ScenesData.CODEC_SCENES,this.sceneList);
    }

    public boolean handler(Supplier<NetworkEvent.Context> ctx){
        {
            final AtomicBoolean success = new AtomicBoolean(false);
            ctx.get().enqueueWork(()->{
                Scenes.sceneList = this.sceneList;
                Screen nowScreen = Minecraft.getInstance().screen;
                if(nowScreen instanceof STScreen){
                    Minecraft.getInstance().setScreen(nowScreen);
                }
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                        () -> () -> success.set(true));
            });
            ctx.get().setPacketHandled(true);
            return success.get();
        }
    }
}
