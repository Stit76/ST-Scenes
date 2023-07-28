package com.stit76.stscenes.networking.packet.server.act;

import com.stit76.stscenes.common.entity.AbstractSTNPC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.function.Supplier;

public class FollowUpC2SPacket {
    private UUID entityUUID;
    private boolean follow;
    private String player_name;
    public FollowUpC2SPacket(UUID entityId, String player, boolean follow){
        this.entityUUID = entityId;
        this.follow = follow;
        this.player_name = player;
    }

    public FollowUpC2SPacket(FriendlyByteBuf buf){
        this.entityUUID = buf.readUUID();
        this.follow = buf.readBoolean();
        this.player_name = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUUID(this.entityUUID);
        buf.writeBoolean(this.follow);
        buf.writeUtf(this.player_name);
    }

    public boolean handler(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Logger LOGGER = LogManager.getLogger();
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            Entity entity = level.getEntity(this.entityUUID);

            if(player != null){
                if(entity != null){
                    if(entity instanceof AbstractSTNPC){
                        ((AbstractSTNPC) entity).behaviourData.setFollowPlayer(this.player_name);
                        ((AbstractSTNPC) entity).behaviourData.setFollow(this.follow);
                    }
                } else {
                    LOGGER.error("Creature with UUID:" + this.entityUUID.toString() + " was not found");
                }
            } else {
                LOGGER.error("A player named "+this.player_name +" has not been found or is not connected to the server");
            }
        });
        return true;
    }
}
