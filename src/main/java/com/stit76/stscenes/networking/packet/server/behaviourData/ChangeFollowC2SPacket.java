package com.stit76.stscenes.networking.packet.server.behaviourData;

import com.stit76.stscenes.common.entity.AbstractSTNPC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ChangeFollowC2SPacket {
    private UUID entityUUID;
    private boolean follow;
    public ChangeFollowC2SPacket(UUID entityId, boolean follow){
        this.entityUUID = entityId;
        this.follow = follow;
    }

    public ChangeFollowC2SPacket(FriendlyByteBuf buf){
        this.entityUUID = buf.readUUID();
        this.follow = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUUID(this.entityUUID);
        buf.writeBoolean(this.follow);
    }

    public boolean handler(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();
            Entity entity = level.getEntity(this.entityUUID);

            if(entity instanceof AbstractSTNPC){
                ((AbstractSTNPC) entity).behaviourData.setFollow(this.follow);
            }
        });
        return true;
    }
}
