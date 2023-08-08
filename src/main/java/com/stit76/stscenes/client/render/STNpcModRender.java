package com.stit76.stscenes.client.render;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.stit76.stscenes.common.entity.STNpc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.StringUtils;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;

public class STNpcModRender extends MobRenderer<STNpc, PlayerModel<STNpc>> {
    public STNpcModRender(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER),false), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(STNpc p_114482_) {
        //String texture = p_114482_.visualData.getTexture();
        //String[] parts = texture.split(":");
        //if(parts.length == 2){
        //    String id = parts[0];
        //    String filePath = parts[1];
        //    if(isValidUsAscii(id) && isValidUsAscii(filePath)){
        //        if(id == "minecraft"){return new ResourceLocation(filePath);}
        //        return new ResourceLocation(id,filePath);
        //    } else {
        //        return new ResourceLocation("");
        //    }
        //}
        //if(isValidUsAscii(parts[0])){
        //    return new ResourceLocation(parts[0]);
        //} else {
        //    return new ResourceLocation("");
        //}
        return new ResourceLocation("");
    }

    public static ResourceLocation getPlayerSkin() {
        //Получение профиля игрока "Infantt"
        //GameProfile gameProfile = new GameProfile((UUID)null,"Infantt");
        //try {
        //    if(gameProfile != null){
        //        MinecraftProfileTexture.Type textureType = MinecraftProfileTexture.Type.SKIN;
        //        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> profileTexture = Minecraft.getInstance().getSkinManager().getInsecureSkinInformation(gameProfile);
        //        if (profileTexture != null) {
        //            // Получение ResourceLocation скина
        //            ResourceLocation skinLocation = Minecraft.getInstance().getSkinManager().registerTexture(profileTexture.get(textureType),textureType);
        //            //Отправка ResourceLoaction скина
        //            return Minecraft.getInstance().getSkinManager().getInsecureSkinLocation(gameProfile);
        //        }
        //    }
        //} catch (Exception e) {
        //    throw new RuntimeException(e);
        //}
        //return DefaultPlayerSkin.getDefaultSkin(); // Выкидывание стандартного скина если не срботают варианты выше
        return null;
    }

    public static boolean isValidUsAscii (String s) {
        return Charset.forName("US-ASCII").newEncoder().canEncode(s);
    }
}
