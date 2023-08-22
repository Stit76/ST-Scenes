package com.stit76.stscenes.client.render;


import com.mojang.authlib.GameProfile;
import com.stit76.stscenes.client.render.models.STNpcModel;
import com.stit76.stscenes.common.entity.STNpc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.nio.charset.Charset;
import java.util.UUID;

public class STNpcModRender extends MobRenderer<STNpc, STNpcModel<STNpc>> {
    public STNpcModRender(EntityRendererProvider.Context context) {
        super(context, new STNpcModel<>(context.bakeLayer(STNpcModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(STNpc p_114482_) {
        String texture = p_114482_.visualData.getTexture();
        String[] parts = texture.split(":");
        if(parts.length == 2){
            String id = parts[0];
            String filePath = parts[1];
            if(isValidUsAscii(id) && isValidUsAscii(filePath)){
                if(id == "minecraft"){return new ResourceLocation(filePath);}
                return new ResourceLocation(id,filePath);
            } else {
                return new ResourceLocation("");
            }
        }
        if(isValidUsAscii(parts[0])){
            return new ResourceLocation(parts[0]);
        } else {
            return new ResourceLocation("");
        }
    }
    public ResourceLocation getPlayerSkinByName(String name){
        GameProfile gameprofile = new GameProfile((UUID)null, name);
        return Minecraft.getInstance().getSkinManager().getInsecureSkinLocation(gameprofile);
    }

    public static boolean isValidUsAscii (String s) {
        return Charset.forName("US-ASCII").newEncoder().canEncode(s);
    }
}
