package com.stit76.stscenes.client.event;

import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.render.STNpc64x64ModRender;
import com.stit76.stscenes.client.render.models.STNpc64x64Model;
import com.stit76.stscenes.core.init.EntityInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = STScenes.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public final class ClientModEvents {

    public static void ClientModEvents(){

    }

    @SubscribeEvent
    public static void registerRenders(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityInit.ST_NPC.get(), STNpc64x64ModRender::new);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions layerDefinitions){
        layerDefinitions.registerLayerDefinition(STNpc64x64Model.LAYER_LOCATION, STNpc64x64Model::createBodyLayer);
    }
}
