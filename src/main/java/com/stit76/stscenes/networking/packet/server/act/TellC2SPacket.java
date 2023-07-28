package com.stit76.stscenes.networking.packet.server.act;

import com.stit76.stscenes.common.entity.AbstractSTNPC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.function.Supplier;

public class TellC2SPacket {
    private UUID entityUUID;
    private String text;
    private String player_name;
    public TellC2SPacket(UUID entityUUID, String text, String player_name){
        this.entityUUID = entityUUID;
        this.text = text;
        this.player_name = player_name;
    }

    public TellC2SPacket(FriendlyByteBuf buf){
        this.entityUUID = buf.readUUID();
        this.text = buf.readUtf();
        this.player_name = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUUID(this.entityUUID);
        buf.writeUtf(this.text);
        buf.writeUtf(this.player_name);
    }

    public boolean handler(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Logger LOGGER = LogManager.getLogger();
            ServerPlayer playerClient = context.getSender();
            ServerLevel level = playerClient.getLevel();
            Entity entity = level.getEntity(this.entityUUID);
            ServerPlayer player = level.getServer().getPlayerList().getPlayerByName(this.player_name);
            if(player != null){
                if(entity != null){
                    if(entity instanceof AbstractSTNPC) {
                        player.sendSystemMessage(Component.nullToEmpty("[" + ((AbstractSTNPC) entity).visualData.getName() + "] " + text), false);
                    } else {
                        player.sendSystemMessage(Component.nullToEmpty("[" + entity.getName().getString() + "] " + text), false);
                    }
                } else {
                    LOGGER.error("Creature with UUID:" + this.entityUUID.toString() + " was not found");
                }

            } else {
                LOGGER.error("A player named "+this.player_name+" has not been found or is not connected to the server");
            }
        });
        return true;
    }
}
