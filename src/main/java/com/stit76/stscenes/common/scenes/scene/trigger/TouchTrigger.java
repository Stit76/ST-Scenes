package com.stit76.stscenes.common.scenes.scene.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class TouchTrigger extends Trigger{
    public Vec3 point_1;
    public Vec3 point_2;
    public String player_nick = "";
    public static final Codec<TouchTrigger> TOUCH_TRIGGER_CODEC = RecordCodecBuilder.create(instance -> // Given an instance
            instance.group(Codec.STRING.fieldOf("scene_name").forGetter(TouchTrigger::getName),
                Codec.BOOL.fieldOf("canuse").forGetter(TouchTrigger::isCanUse),
                Codec.STRING.fieldOf("player_name").forGetter(TouchTrigger::getPlayer_nick),
                Codec.STRING.fieldOf("trigger_uuid").forGetter(TouchTrigger::getStringUUID),
                Vec3.CODEC.fieldOf("point_1").forGetter(TouchTrigger::getPoint_1),
                Vec3.CODEC.fieldOf("point_2").forGetter(TouchTrigger::getPoint_2)
            ).apply(instance, TouchTrigger::new)
    );
    public TouchTrigger(String name,boolean canUse,String player_nick,String trigger_uuid, Vec3 point_1,Vec3 point_2) {
        this.name = name;
        this.uuid = UUID.fromString(trigger_uuid);
        this.setCanUse(canUse);
        this.point_1 = point_1;
        this.point_2 = point_2;
        this.player_nick = player_nick;
    }
    public TouchTrigger(String player, Vec3 point_1, Vec3 point_2) {
        this.point_1 = point_1;
        this.point_2 = point_2;
        this.player_nick = player;
    }
    public TouchTrigger() {
    }

    @Override
    protected UUID getLastPlayer(MinecraftServer server,Level level) {
        if(this.player_nick != null){
            if(player_nick.isEmpty()){
                for(Player player : server.getPlayerList().getPlayers()){
                    if(player != null && point_1 != null & point_2 != null){
                        if(point_2.x < point_1.x){
                            double point_1_old = point_1.x;
                            point_1 = new Vec3(point_2.x,point_1.y,point_1.z);
                            point_2 = new Vec3(point_1_old,point_2.y,point_2.z);
                        }
                        if(point_2.y < point_1.y){
                            double point_1_old = point_1.y;
                            point_1 = new Vec3(point_1.x,point_2.y,point_1.z);
                            point_2 = new Vec3(point_2.x,point_1_old,point_2.z);
                        }
                        if(point_2.z < point_1.z){
                            double point_1_old = point_1.z;
                            point_1 = new Vec3(point_1.x,point_1.y,point_2.z);
                            point_2 = new Vec3(point_2.x,point_2.y,point_1_old);
                        }
                        if( (int) player.getX() >= (int) point_1.x && (int) player.getZ() >= (int) point_1.z && (int) player.getY() >= (int) point_1.y){
                            if( (int)player.getX() <= (int) point_2.x && (int) player.getZ() <= (int) point_2.z && (int) player.getY() <= (int)point_2.y){
                                return player.getUUID();
                            }
                        }
                    }
                }
            } else {
                Player player = server.getPlayerList().getPlayerByName(this.getPlayer_nick());
                if(player != null && point_1 != null & point_2 != null){
                    if(point_2.x < point_1.x){
                        double point_1_old = point_1.x;
                        point_1 = new Vec3(point_2.x,point_1.y,point_1.z);
                        point_2 = new Vec3(point_1_old,point_2.y,point_2.z);
                    }
                    if(point_2.y < point_1.y){
                        double point_1_old = point_1.y;
                        point_1 = new Vec3(point_1.x,point_2.y,point_1.z);
                        point_2 = new Vec3(point_2.x,point_1_old,point_2.z);
                    }
                    if(point_2.z < point_1.z){
                        double point_1_old = point_1.z;
                        point_1 = new Vec3(point_1.x,point_1.y,point_2.z);
                        point_2 = new Vec3(point_2.x,point_2.y,point_1_old);
                    }
                    if( (int) player.getX() >= (int) point_1.x && (int) player.getZ() >= (int) point_1.z && (int) player.getY() >= (int) point_1.y){
                        if( (int)player.getX() <= (int) point_2.x && (int) player.getZ() <= (int) point_2.z && (int) player.getY() <= (int)point_2.y){
                            return player.getUUID();
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String type() {
        return Component.translatable("scene_customizer.touch_trigger.name").getString();
    }

    @Override
    public TriggerType<?> iType() {
        return Triggers.TOUCH_TRIGGER;
    }

    @Override
    protected boolean update(MinecraftServer server, Level level) {
        Vec3 point_1 = this.point_1;
        Vec3 point_2 = this.point_2;
        if(this.player_nick != null){
            if(player_nick.isEmpty()){
                for(Player player : server.getPlayerList().getPlayers()){
                    if(player != null && point_1 != null & point_2 != null){
                        if(point_2.x < point_1.x){
                            double point_1_old = point_1.x;
                            point_1 = new Vec3(point_2.x,point_1.y,point_1.z);
                            point_2 = new Vec3(point_1_old,point_2.y,point_2.z);
                        }
                        if(point_2.y < point_1.y){
                            double point_1_old = point_1.y;
                            point_1 = new Vec3(point_1.x,point_2.y,point_1.z);
                            point_2 = new Vec3(point_2.x,point_1_old,point_2.z);
                        }
                        if(point_2.z < point_1.z){
                            double point_1_old = point_1.z;
                            point_1 = new Vec3(point_1.x,point_1.y,point_2.z);
                            point_2 = new Vec3(point_2.x,point_2.y,point_1_old);
                        }
                        if( (int) player.getX() >= (int) point_1.x && (int) player.getZ() >= (int) point_1.z && (int) player.getY() >= (int) point_1.y){
                            if( (int)player.getX() <= (int) point_2.x && (int) player.getZ() <= (int) point_2.z && (int) player.getY() <= (int)point_2.y){
                                return true;
                            }
                        }
                    }
                }
            } else {
                Player player = server.getPlayerList().getPlayerByName(this.getPlayer_nick());
                if(player != null && point_1 != null & point_2 != null){
                    if(point_2.x < point_1.x){
                        double point_1_old = point_1.x;
                        point_1 = new Vec3(point_2.x,point_1.y,point_1.z);
                        point_2 = new Vec3(point_1_old,point_2.y,point_2.z);
                    }
                    if(point_2.y < point_1.y){
                        double point_1_old = point_1.y;
                        point_1 = new Vec3(point_1.x,point_2.y,point_1.z);
                        point_2 = new Vec3(point_2.x,point_1_old,point_2.z);
                    }
                    if(point_2.z < point_1.z){
                        double point_1_old = point_1.z;
                        point_1 = new Vec3(point_1.x,point_1.y,point_2.z);
                        point_2 = new Vec3(point_2.x,point_2.y,point_1_old);
                    }
                    if( (int) player.getX() >= (int) point_1.x && (int) player.getZ() >= (int) point_1.z && (int) player.getY() >= (int) point_1.y){
                        if( (int)player.getX() <= (int) point_2.x && (int) player.getZ() <= (int) point_2.z && (int) player.getY() <= (int)point_2.y){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public String getPlayer_nick() {
        return player_nick;
    }
    public void setPlayer_nick(String player_nick) {
        this.player_nick = player_nick;
    }
    public Vec3 getPoint_1() {
        if(point_1 != null){
            return point_1;
        }
        return new Vec3(0,0,0);
    }
    public Vec3 getPoint_2() {
        if (point_2 != null) {
            return point_2;
        }
        return new Vec3(0, 0, 0);
    }
}
