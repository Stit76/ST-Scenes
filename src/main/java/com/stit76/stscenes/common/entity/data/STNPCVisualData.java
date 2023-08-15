package com.stit76.stscenes.common.entity.data;

import com.stit76.stscenes.common.entity.AbstractSTNPC;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

public class STNPCVisualData {
    AbstractSTNPC npc;
    public static final EntityDataAccessor<String> URL = SynchedEntityData.defineId(AbstractSTNPC.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(AbstractSTNPC.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> NAME = SynchedEntityData.defineId(AbstractSTNPC.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Boolean> SHOW_NAME = SynchedEntityData.defineId(AbstractSTNPC.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> ALWAYES_SHOW_NAME = SynchedEntityData.defineId(AbstractSTNPC.class, EntityDataSerializers.BOOLEAN);


    public STNPCVisualData(AbstractSTNPC npc){
        this.npc = npc;
    }
    public CompoundTag addAdditionalSaveData(CompoundTag tag) {
        tag.putString("Name", getName());
        tag.putString("Url",getURL());
        tag.putString("Texture",getTexture());
        tag.putBoolean("showName", getShowName());
        tag.putBoolean("showNameOnHover", getAlwaysShowName());
        return tag;
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        this.setName(tag.getString("Name"));
        this.setURL(tag.getString("Url"));
        this.setTexture(tag.getString("Texture"));
        this.setShowName(tag.getBoolean("showName"));
        this.setAlwaysShowName(tag.getBoolean("showNameOnHover"));
    }
    public void setName(String name) {
        if (name != null) {
            this.npc.getEntityData().set(this.NAME,name);
        }
    }
    public String getName() {
        return this.npc.getEntityData().get(this.NAME);
    }

    public void setTexture(String texture) {
        if (texture != null) {
            npc.getEntityData().set(this.TEXTURE,texture.toLowerCase());
        }
    }
    public String getTexture() {
        return this.npc.getEntityData().get(this.TEXTURE);
    }
    public void setURL(String url) {
        if (url != null) {
            npc.getEntityData().set(this.URL,url.toLowerCase());
        }
    }
    public String getURL(){
        return this.npc.getEntityData().get(this.URL);
    }
    public void setShowName(boolean show){
        npc.getEntityData().set(this.SHOW_NAME,show);
    }
    public boolean getShowName(){return npc.getEntityData().get(this.SHOW_NAME);}
    public void setAlwaysShowName(boolean show){
        npc.getEntityData().set(this.ALWAYES_SHOW_NAME,show);
    }
    public boolean getAlwaysShowName(){return npc.getEntityData().get(this.ALWAYES_SHOW_NAME);}

}
