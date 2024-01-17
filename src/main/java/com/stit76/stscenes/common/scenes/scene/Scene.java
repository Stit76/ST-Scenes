package com.stit76.stscenes.common.scenes.scene;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stit76.stscenes.common.entity.AbstractSTNPC;
import com.stit76.stscenes.common.scenes.scene.act.Act;
import com.stit76.stscenes.common.scenes.scene.act.compoundact.CAct;
import com.stit76.stscenes.common.scenes.scene.act.ActCodecs;
import com.stit76.stscenes.common.scenes.scene.trigger.Trigger;
import com.stit76.stscenes.common.scenes.scene.trigger.Triggers;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.server.behaviourData.ResetBehaviorDataNPCSC2SPacket;
import net.minecraft.core.UUIDUtil;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class Scene {
    public Logger LOGGER = LogManager.getLogger();
    private UUID lastPlayer = java.util.UUID.randomUUID();
    public UUID UUID;
    private String name;
    private String date;
    public List<Act> acts = new ArrayList<>();
    public int activeAct = 0;
    public int tick = 0;
    private boolean loop = false;
    private boolean restTriggers = false;
    private boolean active = false;
    public List<Trigger> triggers = new ArrayList<>();
    public static final Codec<Scene> SCENE_CODEC = RecordCodecBuilder.create(instance -> // Given an instance
            instance.group(
                    Codec.STRING.fieldOf("uuid").forGetter(Scene::getUUIDString),
                    UUIDUtil.CODEC.fieldOf("lastPlayer").forGetter(Scene::getLastPlayer),
                    Codec.STRING.fieldOf("name").forGetter(Scene::getName),
                    Codec.STRING.fieldOf("date").forGetter(Scene::getDate),
                    Codec.INT.fieldOf("active_act").forGetter(Scene::getActiveAct),
                    Codec.INT.fieldOf("tick").forGetter(Scene::getTick),
                    Codec.BOOL.fieldOf("active").forGetter(Scene::isActive),
                    Codec.BOOL.fieldOf("loop").forGetter(Scene::isLoop),
                    Codec.BOOL.fieldOf("restTriggers").forGetter(Scene::isRestTriggers),
                    ActCodecs.LIST_CODEC.fieldOf("act").forGetter(Scene::getActs),
                    Triggers.LIST_CODEC.fieldOf("trigger").forGetter(Scene::getTriggers)
            ).apply(instance, Scene::new)
    );
    public Scene(String name){
        this.UUID = java.util.UUID.randomUUID();
        this.name = name;
        MinecraftForge.EVENT_BUS.register(this);
    }
    public Scene(String UUID,UUID lastPlayer, String name, String date, int activeAct, int tick, boolean active, boolean loop, boolean restTriggers, List<Act> acts, List<Trigger> triggers){
        this.UUID = java.util.UUID.fromString(UUID);
        this.setName(name);
        this.setDate(date);
        this.activeAct = activeAct;
        this.tick = tick;
        this.active = active;
        this.acts = acts;
        this.triggers = triggers;
        this.loop = loop;
        this.restTriggers = restTriggers;
        this.lastPlayer = lastPlayer;
        MinecraftForge.EVENT_BUS.register(this);
    }
    public void start(){
        active = true;
    }
    public void stop() {
        tick = 0;
        activeAct = 0;
        active = false;
        if (this.restTriggers) {
            deactivateTriggers();
        }
        resetNpcsBehavior();
    }
    private void resetNpcsBehavior(){
        List<UUID> uuids = new ArrayList<>();
        for(Act act : this.acts){
            if(act instanceof CAct){
                try {
                    uuids.add(((CAct) act).entityUUID);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        SimpleNetworkWrapper.sendToServer(new ResetBehaviorDataNPCSC2SPacket(uuids));
    }
    public void updateDate(){
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        this.setDate(timeStamp);
    }
    private void setDate(String date){
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getName() {return this.name;}
    public void setName(String name){
        try {
            this.name = name;
        } catch (Exception e) {
            System.out.println("Setting name error " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void addAct(CAct act){
        this.acts.add(act);
    }
    public void removeAct(int line){this.acts.remove(line);}
    public List<Act> getActs(){return this.acts;}
    public void setActs(List<Act> acts){this.acts = acts;}
    public int getActiveAct() {return activeAct;}
    public int getActiveTriggers(){
        int j = 0;
        for (int i = 0; i < this.triggers.size(); i++) {
            if(this.triggers.get(i).isCanUse()){
                j++;
            }
        }
        return j;
    }
    public void activateTriggers(){
        for (int i = 0; i < this.triggers.size(); i++) {
            this.triggers.get(i).activate();
        }
    }
    public void deactivateTriggers(){
        for (int i = 0; i < this.triggers.size(); i++) {
            this.triggers.get(i).deactivate();
        }
    }

    public void setUUID(java.util.UUID UUID) {
        this.UUID = UUID;
    }
    public void setUUID(String uuid) {
        this.UUID = java.util.UUID.fromString(uuid);
    }

    public java.util.UUID getUUID() {
        return UUID;
    }
    public String getUUIDString() {
        return UUID.toString();
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public int getTick() {return tick;}

    public boolean isActive() {return active;}

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
    public boolean isLoop(){
        return this.loop;
    }
    public void setRestTriggers(boolean restTriggers) {
        this.restTriggers = restTriggers;
    }
    public boolean isRestTriggers(){
        return this.restTriggers;
    }

    public void setLastPlayer(UUID lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public UUID getLastPlayer() {
        return lastPlayer;
    }
}
