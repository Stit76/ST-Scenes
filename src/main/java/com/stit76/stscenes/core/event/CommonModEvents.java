package com.stit76.stscenes.core.event;

import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.common.entity.STNpc64x64;
import com.stit76.stscenes.core.init.EntityInit;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = STScenes.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(EntityInit.ST_NPC.get(), STNpc64x64.createAttributes().build());
    }
}
