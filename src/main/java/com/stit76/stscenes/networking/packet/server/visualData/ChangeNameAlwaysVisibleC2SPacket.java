package com.stit76.stscenes.networking.packet.server.visualData;

import com.stit76.stscenes.common.entity.AbstractSTNPC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ChangeNameAlwaysVisibleC2SPacket {
    private UUID entityUUID;
    private boolean alwaysVisible;
    public ChangeNameAlwaysVisibleC2SPacket(UUID entityId, boolean visible){
        this.entityUUID = entityId;
        this.alwaysVisible = visible;
    }

    public ChangeNameAlwaysVisibleC2SPacket(FriendlyByteBuf buf){
        this.entityUUID = buf.readUUID();
        this.alwaysVisible = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUUID(this.entityUUID);
        buf.writeBoolean(this.alwaysVisible);
    }

    public boolean handler(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            Entity entity = level.getEntity(this.entityUUID);

            if(entity instanceof AbstractSTNPC){
                ((AbstractSTNPC) entity).visualData.setAlwaysShowName(this.alwaysVisible);
            }
        });
        return true;
    }
}
