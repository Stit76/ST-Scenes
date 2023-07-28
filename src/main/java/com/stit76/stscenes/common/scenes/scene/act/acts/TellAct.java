package com.stit76.stscenes.common.scenes.scene.act.acts;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stit76.stscenes.common.scenes.scene.act.Act;
import com.stit76.stscenes.common.scenes.scene.act.ActType;
import com.stit76.stscenes.common.scenes.scene.act.Acts;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.server.act.TellC2SPacket;

import java.util.UUID;

public class TellAct extends Act {
    public String player_name = "";
    public String text = "";
    public static final Codec<TellAct> TELL_ACT_CODEC = RecordCodecBuilder.create(instance -> // Given an instance
            instance.group(
                    Codec.STRING.fieldOf("uuid").forGetter(Act::getEntityUUIDfromString),
                    Codec.STRING.fieldOf("object").forGetter(Act::getObject),
                    Codec.STRING.fieldOf("act").forGetter(Act::getAct),
                    Codec.STRING.fieldOf("arg").forGetter(Act::getArg),
                    Codec.INT.fieldOf("delay").forGetter(Act::getDelay),
                    Codec.STRING.fieldOf("player_nickname").forGetter(TellAct::getPlayer_name),
                    Codec.STRING.fieldOf("text").forGetter(TellAct::getText)
            ).apply(instance, TellAct::new)
    );
    public TellAct(UUID entityUUID, String text, String player_name){
        this.entityUUID = entityUUID;
        this.text = text;
        this.player_name = player_name;
        this.act = "Tell";
        this.arg = "player \"" + player_name + "\" \"" + text + "\"";
    }
    public TellAct(String entityUUID, String object, String act,String arg,int delay,String player_nickname,String text){
        this.entityUUID = UUID.fromString(entityUUID);
        this.object = object;
        this.act = act;
        this.arg = arg;
        this.delay = delay;
        this.player_name = player_nickname;
        this.text = text;
    }
    public TellAct(){
        this.act = "Tell";
        this.delay = 60;
    }

    @Override
    protected boolean action() {
        if(entityUUID != null && text != null & player_name != null){
            SimpleNetworkWrapper.sendToServer(new TellC2SPacket(this.entityUUID,this.text,this.player_name));
            return true;
        }
        return false;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public String getText() {
        return text;
    }

    public ActType type() {
        return Acts.TELLACT;
    }
}
