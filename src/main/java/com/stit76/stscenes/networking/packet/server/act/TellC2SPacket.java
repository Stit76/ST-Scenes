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

import java.util.*;
import java.util.function.Supplier;

public class TellC2SPacket {
    private static String[] colors = new String[]{
            "§1","§2","§3","§4","§5","§6","§7","§8","§9","§a","§b","§c","§d","§e","§f"
    };
    private UUID entityUUID;
    private String nameColor;
    private String text;
    private String player_name;
    public TellC2SPacket(UUID entityUUID,String nameColor, String text, String player_name){
        this.entityUUID = entityUUID;
        this.nameColor = nameColor;
        this.text = text;
        this.player_name = player_name;
    }

    public TellC2SPacket(FriendlyByteBuf buf){
        this.entityUUID = buf.readUUID();
        this.nameColor = buf.readUtf();
        this.text = buf.readUtf();
        this.player_name = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUUID(this.entityUUID);
        buf.writeUtf(this.nameColor);
        buf.writeUtf(this.text);
        buf.writeUtf(this.player_name);
    }

    public boolean handler(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Logger LOGGER = LogManager.getLogger();
            ServerPlayer playerClient = context.getSender();
            ServerLevel level = playerClient.serverLevel();
            Entity entity = level.getEntity(this.entityUUID);
            ServerPlayer player = level.getServer().getPlayerList().getPlayerByName(this.player_name);
            String nameCol = "";
            for (int i = 0; i < colors.length; i++) {
                if(colors[i].equals(this.nameColor)) {
                    nameCol = this.nameColor;
                }
            }
            if(player != null){
                if(entity != null){
                    if(entity instanceof AbstractSTNPC) {
                        player.sendSystemMessage(Component.nullToEmpty(nameCol+"[" + ((AbstractSTNPC) entity).visualData.getName() + "]§f " + text), false);
                    } else {
                        player.sendSystemMessage(Component.nullToEmpty(nameCol+"[" + entity.getName().getString() + "]§f " + text), false);
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
