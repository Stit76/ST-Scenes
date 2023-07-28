package com.stit76.stscenes.networking.packet.server.visualData;

import com.stit76.stscenes.common.entity.AbstractSTNPC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ChangeNameVisibleC2SPacket {
    private UUID entityUUID;
    private boolean visible;
    public ChangeNameVisibleC2SPacket(UUID entityId, boolean visible){
        this.entityUUID = entityId;
        this.visible = visible;
    }

    public ChangeNameVisibleC2SPacket(FriendlyByteBuf buf){
        this.entityUUID = buf.readUUID();
        this.visible = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUUID(this.entityUUID);
        buf.writeBoolean(this.visible);
    }

    public boolean handler(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            Entity entity = level.getEntity(this.entityUUID);

            if(entity instanceof AbstractSTNPC){
                ((AbstractSTNPC) entity).visualData.setShowName(this.visible);
            }
        });
        return true;
    }
}
