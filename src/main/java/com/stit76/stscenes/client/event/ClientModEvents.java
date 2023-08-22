package com.stit76.stscenes.client.event;

import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.render.STNpcModRender;
import com.stit76.stscenes.client.render.models.STNpcModel;
import com.stit76.stscenes.core.init.EntityInit;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = STScenes.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public final class ClientModEvents {

    public static void ClientModEvents(){

    }

    @SubscribeEvent
    public static void registerRenders(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityInit.ST_NPC.get(), STNpcModRender::new);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions layerDefinitions){
        layerDefinitions.registerLayerDefinition(STNpcModel.LAYER_LOCATION,STNpcModel::createBodyLayer);
    }
}
