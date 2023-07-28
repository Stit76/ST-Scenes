package com.stit76.stscenes.common.scenes.scene.act.acts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stit76.stscenes.common.scenes.scene.act.Act;
import com.stit76.stscenes.common.scenes.scene.act.ActType;
import com.stit76.stscenes.common.scenes.scene.act.Acts;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.server.act.FollowUpC2SPacket;

import java.util.*;

public class FollowUpAct extends Act{
    private String playerName;
    private boolean follow;
    public static final Codec<FollowUpAct> FOLLOWUP_ACT_CODEC = RecordCodecBuilder.create(instance -> // Given an instance
            instance.group(
                    Codec.STRING.fieldOf("uuid").forGetter(Act::getEntityUUIDfromString),
                    Codec.STRING.fieldOf("object").forGetter(Act::getObject),
                    Codec.STRING.fieldOf("act").forGetter(Act::getAct),
                    Codec.STRING.fieldOf("arg").forGetter(Act::getArg),
                    Codec.INT.fieldOf("delay").forGetter(Act::getDelay),
                    Codec.STRING.fieldOf("player_nick").forGetter(FollowUpAct::getPlayerName),
                    Codec.BOOL.fieldOf("follow").forGetter(FollowUpAct::isFollow)
            ).apply(instance, FollowUpAct::new)
    );
    public FollowUpAct(){
        this(null,true,"");
    }
    public FollowUpAct(UUID npcUUID, String player_name){
        this(npcUUID,true,player_name);
    }
    public FollowUpAct(UUID npcUUID, boolean follow, String player_name){
        this.entityUUID = npcUUID;
        this.playerName = player_name;
        this.follow = follow;
        this.act = "Follow up";
        this.arg = playerName;
        this.delay = 0;
    }

    public FollowUpAct(String s, String s1, String s2, String s3, Integer integer,String player_nickname,boolean isFollow) {
        this.entityUUID = UUID.fromString(s);
        this.object = s1;
        this.act = s2;
        this.arg = s3;
        this.delay = integer;
        this.playerName = player_nickname;
        this.follow = isFollow;
    }

    @Override
    protected boolean action() {
        if(entityUUID != null && playerName != null){
            SimpleNetworkWrapper.sendToServer(new FollowUpC2SPacket(this.entityUUID,this.playerName,this.follow));
            return true;
        }
        return false;
    }
    public String getPlayerName(){
        if(this.playerName != "" && this.playerName != null){
            return this.playerName;
        }
        return "";
    }
    public void setPlayerName(String player_name){
        if(player_name != "" && player_name != null){
            this.playerName = player_name;
        }
    }

    public boolean isFollow() {
        return follow;
    }

    public UUID getEntityUUID() {
        return entityUUID;
    }

    public void setEntityUUID(UUID entityUUID){
        if(entityUUID != null){
            this.entityUUID = entityUUID;
        }
    }
    public ActType type() {
        return Acts.FOLLOWUPACT;
    }
}
