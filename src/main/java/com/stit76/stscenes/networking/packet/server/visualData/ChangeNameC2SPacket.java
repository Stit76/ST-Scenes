package com.stit76.stscenes.networking.packet.server.visualData;

import com.stit76.stscenes.common.entity.AbstractSTNPC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ChangeNameC2SPacket {
    private UUID entityUUID;
    private String name;
    public ChangeNameC2SPacket(UUID entityId,String name){
        this.entityUUID = entityId;
        this.name = name;
    }

    public ChangeNameC2SPacket(FriendlyByteBuf buf){
        this.entityUUID = buf.readUUID();
        this.name = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUUID(this.entityUUID);
        buf.writeUtf(this.name);
    }

    public boolean handler(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            Entity entity = level.getEntity(this.entityUUID);

            if(entity instanceof AbstractSTNPC){
                ((AbstractSTNPC) entity).visualData.setName(this.name);
            }
        });
        return true;
    }
}
