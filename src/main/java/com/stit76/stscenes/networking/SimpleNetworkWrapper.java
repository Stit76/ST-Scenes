package com.stit76.stscenes.networking;

import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.networking.packet.server.act.FollowUpC2SPacket;
import com.stit76.stscenes.networking.packet.server.act.TellC2SPacket;
import com.stit76.stscenes.networking.packet.server.visualData.ChangeNameAlwaysVisibleC2SPacket;
import com.stit76.stscenes.networking.packet.server.visualData.ChangeNameC2SPacket;
import com.stit76.stscenes.networking.packet.server.visualData.ChangeNameVisibleC2SPacket;
import com.stit76.stscenes.networking.packet.server.visualData.ChangeTextureC2SPacket;
import com.stit76.stscenes.networking.packet.synchronization.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class SimpleNetworkWrapper {
    private static SimpleChannel INSTANCE;

    private static int packet_id = 0;
    private static int id(){
        return packet_id++;
    }

    public  static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(STScenes.MODID,"messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        INSTANCE = net;

        net.messageBuilder(ChangeNameC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChangeNameC2SPacket::new)
                .encoder(ChangeNameC2SPacket::toBytes)
                .consumerMainThread(ChangeNameC2SPacket::handler)
                .add();
        net.messageBuilder(ChangeTextureC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChangeTextureC2SPacket::new)
                .encoder(ChangeTextureC2SPacket::toBytes)
                .consumerMainThread(ChangeTextureC2SPacket::handler)
                .add();
        net.messageBuilder(ChangeNameVisibleC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChangeNameVisibleC2SPacket::new)
                .encoder(ChangeNameVisibleC2SPacket::toBytes)
                .consumerMainThread(ChangeNameVisibleC2SPacket::handler)
                .add();
        net.messageBuilder(ChangeNameAlwaysVisibleC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChangeNameAlwaysVisibleC2SPacket::new)
                .encoder(ChangeNameAlwaysVisibleC2SPacket::toBytes)
                .consumerMainThread(ChangeNameAlwaysVisibleC2SPacket::handler)
                .add();
        net.messageBuilder(FollowUpC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FollowUpC2SPacket::new)
                .encoder(FollowUpC2SPacket::toBytes)
                .consumerMainThread(FollowUpC2SPacket::handler)
                .add();
        net.messageBuilder(TellC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TellC2SPacket::new)
                .encoder(TellC2SPacket::toBytes)
                .consumerMainThread(TellC2SPacket::handler)
                .add();
        net.messageBuilder(SetScenesDataServerC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetScenesDataServerC2SPacket::new)
                .encoder(SetScenesDataServerC2SPacket::toBytes)
                .consumerMainThread(SetScenesDataServerC2SPacket::handler)
                .add();
        net.messageBuilder(LoadScenesListToClientC2SPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LoadScenesListToClientC2SPacket::new)
                .encoder(LoadScenesListToClientC2SPacket::toBytes)
                .consumerMainThread(LoadScenesListToClientC2SPacket::handler)
                .add();
        net.messageBuilder(AddSceneToScenesDataC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AddSceneToScenesDataC2SPacket::new)
                .encoder(AddSceneToScenesDataC2SPacket::toBytes)
                .consumerMainThread(AddSceneToScenesDataC2SPacket::handler)
                .add();
        net.messageBuilder(SetSceneInScenesDataC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetSceneInScenesDataC2SPacket::new)
                .encoder(SetSceneInScenesDataC2SPacket::toBytes)
                .consumerMainThread(SetSceneInScenesDataC2SPacket::handler)
                .add();
        net.messageBuilder(SetSceneActiveC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetSceneActiveC2SPacket::new)
                .encoder(SetSceneActiveC2SPacket::toBytes)
                .consumerMainThread(SetSceneActiveC2SPacket::handler)
                .add();

    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message );
    }

    public static <MSG> void sendToAllPlayer(MSG message){
        INSTANCE.send(PacketDistributor.ALL.noArg(), message );
    }
}
