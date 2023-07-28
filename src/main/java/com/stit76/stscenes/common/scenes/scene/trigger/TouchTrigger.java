package com.stit76.stscenes.common.scenes.scene.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stit76.stscenes.client.gui.sceneCustomizer.triggerScreens.TouchTriggerScreen;
import com.stit76.stscenes.common.scenes.scene.act.Act;
import com.stit76.stscenes.common.scenes.scene.act.acts.FollowUpAct;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class TouchTrigger extends Trigger{
    public Vec3 point_1;
    public Vec3 point_2;
    public Player player;
    public static final Codec<TouchTrigger> TOUCH_TRIGGER_CODEC = RecordCodecBuilder.create(instance -> // Given an instance
            instance.group(
                Codec.BOOL.fieldOf("canuse").forGetter(TouchTrigger::isCanUse),
                Codec.STRING.fieldOf("player_name").forGetter(TouchTrigger::getPlayerNick),
                Vec3.CODEC.fieldOf("point_1").forGetter(TouchTrigger::getPoint_1),
                Vec3.CODEC.fieldOf("point_2").forGetter(TouchTrigger::getPoint_2)
            ).apply(instance, TouchTrigger::new)
    );
    public TouchTrigger(boolean canUse,String player_nick, Vec3 point_1,Vec3 point_2) {
        this.setCanUse(canUse);
        this.point_1 = point_1;
        this.point_2 = point_2;
        this.player = Minecraft.getInstance().getSingleplayerServer().getPlayerList().getPlayerByName(player_nick);
    }
    public TouchTrigger(Player player, Vec3 point_1,Vec3 point_2) {
        this.point_1 = point_1;
        this.point_2 = point_2;
        this.player = player;
    }
    public TouchTrigger() {
    }
    @Override
    public TriggerType<?> type() {
        return Triggers.TOUCH_TRIGGER;
    }

    @Override
    protected boolean update() {
        Vec3 point_1 = this.point_1;
        Vec3 point_2 = this.point_2;
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
        return super.update();
    }

    public Player getPlayer() {
        return player;
    }
    public Vec3 getPoint_1() {
        if(point_1 != null){
            return point_1;
        }
        return new Vec3(0,0,0);
    }
    public Vec3 getPoint_2() {
        if(point_2 != null){
            return point_2;
        }
        return new Vec3(0,0,0);
    }
    public String getPlayerNick() {
        if(player != null){
            return player.getName().getString();
        }
        return "";
    }
}
