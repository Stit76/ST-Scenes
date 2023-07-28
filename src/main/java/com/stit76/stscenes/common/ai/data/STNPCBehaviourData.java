package com.stit76.stscenes.common.ai.data;

import com.stit76.stscenes.common.entity.AbstractSTNPC;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

public class STNPCBehaviourData {
    private AbstractSTNPC npc;
    public STNPCBehaviourData(AbstractSTNPC npc){this.npc = npc;}
    public static final EntityDataAccessor<Boolean> FOLLOW = SynchedEntityData.defineId(AbstractSTNPC.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> FOLLOW_PLAYER = SynchedEntityData.defineId(AbstractSTNPC.class, EntityDataSerializers.STRING);
    public CompoundTag addAdditionalSaveData(CompoundTag tag) {
        tag.putBoolean("Follow", getFollow());
        tag.putString("Follow_player", getFollowPlayer());
        return tag;
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        this.setFollow(tag.getBoolean("Follow"));
        this.setFollowPlayer(tag.getString("Follow_player"));
    }
    public void setFollow(boolean bool){
        this.npc.getEntityData().set(this.FOLLOW,bool);
    }
    public boolean getFollow(){
        return this.npc.getEntityData().get(this.FOLLOW);
    }
    public void setFollowPlayer(String player){
        this.npc.getEntityData().set(this.FOLLOW_PLAYER,player);
    }
    public String getFollowPlayer(){
        return this.npc.getEntityData().get(this.FOLLOW_PLAYER);
    }
}
