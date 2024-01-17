package com.stit76.stscenes.networking;

import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.networking.packet.client.ClearSelectionWindowScreenC2SPacket;
import com.stit76.stscenes.networking.packet.client.SetSelectionWindowScreenC2SPacket;
import com.stit76.stscenes.networking.packet.server.act.*;
import com.stit76.stscenes.networking.packet.server.behaviourData.ChangeImmortalityC2SPacket;
import com.stit76.stscenes.networking.packet.server.behaviourData.ResetBehaviorDataNPCSC2SPacket;
import com.stit76.stscenes.networking.packet.server.visualData.*;
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
        net.messageBuilder(FollowC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FollowC2SPacket::new)
                .encoder(FollowC2SPacket::toBytes)
                .consumerMainThread(FollowC2SPacket::handler)
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
        net.messageBuilder(DeleteSceneFromSceneList.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DeleteSceneFromSceneList::new)
                .encoder(DeleteSceneFromSceneList::toBytes)
                .consumerMainThread(DeleteSceneFromSceneList::handler)
                .add();
        net.messageBuilder(ChangeNPCModelPart.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChangeNPCModelPart::new)
                .encoder(ChangeNPCModelPart::toBytes)
                .consumerMainThread(ChangeNPCModelPart::handler)
                .add();
        net.messageBuilder(TeleportToC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TeleportToC2SPacket::new)
                .encoder(TeleportToC2SPacket::toBytes)
                .consumerMainThread(TeleportToC2SPacket::handler)
                .add();
        net.messageBuilder(GoToPointC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GoToPointC2SPacket::new)
                .encoder(GoToPointC2SPacket::toBytes)
                .consumerMainThread(GoToPointC2SPacket::handler)
                .add();
        net.messageBuilder(LookAtPointC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LookAtPointC2SPacket::new)
                .encoder(LookAtPointC2SPacket::toBytes)
                .consumerMainThread(LookAtPointC2SPacket::handler)
                .add();
        net.messageBuilder(LookAtPointC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LookAtPointC2SPacket::new)
                .encoder(LookAtPointC2SPacket::toBytes)
                .consumerMainThread(LookAtPointC2SPacket::handler)
                .add();
        net.messageBuilder(ChangeImmortalityC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChangeImmortalityC2SPacket::new)
                .encoder(ChangeImmortalityC2SPacket::toBytes)
                .consumerMainThread(ChangeImmortalityC2SPacket::handler)
                .add();
        net.messageBuilder(SetSceneLastPlayerC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetSceneLastPlayerC2SPacket::new)
                .encoder(SetSceneLastPlayerC2SPacket::toBytes)
                .consumerMainThread(SetSceneLastPlayerC2SPacket::handler)
                .add();
        net.messageBuilder(SetSelectionWindowScreenC2SPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SetSelectionWindowScreenC2SPacket::new)
                .encoder(SetSelectionWindowScreenC2SPacket::toBytes)
                .consumerMainThread(SetSelectionWindowScreenC2SPacket::handler)
                .add();
        net.messageBuilder(ClearSelectionWindowScreenC2SPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClearSelectionWindowScreenC2SPacket::new)
                .encoder(ClearSelectionWindowScreenC2SPacket::toBytes)
                .consumerMainThread(ClearSelectionWindowScreenC2SPacket::handler)
                .add();
        net.messageBuilder(ResetBehaviorDataNPCSC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ResetBehaviorDataNPCSC2SPacket::new)
                .encoder(ResetBehaviorDataNPCSC2SPacket::toBytes)
                .consumerMainThread(ResetBehaviorDataNPCSC2SPacket::handler)
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
