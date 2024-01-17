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

    public static final EntityDataAccessor<String> HEAD_MODEL = SynchedEntityData.defineId(AbstractSTNPC.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> BODY_MODEL = SynchedEntityData.defineId(AbstractSTNPC.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> ARMS_MODEL = SynchedEntityData.defineId(AbstractSTNPC.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> LEGS_MODEL = SynchedEntityData.defineId(AbstractSTNPC.class, EntityDataSerializers.STRING);



    public STNPCVisualData(AbstractSTNPC npc){
        this.npc = npc;
    }
    public CompoundTag addAdditionalSaveData(CompoundTag tag) {
        tag.putString("Name", getName());
        tag.putString("Url",getURL());
        tag.putString("Texture",getTexture());
        tag.putString("HeadModel",getHeadModel());
        tag.putString("BodyModel",getBodyModel());
        tag.putString("ArmsModel",getArmsModel());
        tag.putString("LegsModel", getLegsModel());
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
        this.setHeadModel(tag.getString("HeadModel"));
        this.setBodyModel(tag.getString("BodyModel"));
        this.setArmsModel(tag.getString("ArmsModel"));
        this.setLegsModel(tag.getString("LegsModel"));
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

    public void setHeadModel(String headModel) {
        this.npc.getEntityData().set(this.HEAD_MODEL,headModel);
    }
    public String getHeadModel(){
        return this.npc.getEntityData().get(this.HEAD_MODEL);
    }
    public void setBodyModel(String bodyModel) {
        npc.getEntityData().set(this.BODY_MODEL,bodyModel);
    }
    public String getBodyModel(){
        return this.npc.getEntityData().get(this.BODY_MODEL);
    }
    public void setArmsModel(String armsModel) {
        npc.getEntityData().set(this.ARMS_MODEL,armsModel);
    }
    public String getArmsModel(){
        return this.npc.getEntityData().get(this.ARMS_MODEL);
    }
    public void setLegsModel(String legsModel) {
        npc.getEntityData().set(this.LEGS_MODEL,legsModel);
    }
    public String getLegsModel(){
        return this.npc.getEntityData().get(this.LEGS_MODEL);
    }

}
