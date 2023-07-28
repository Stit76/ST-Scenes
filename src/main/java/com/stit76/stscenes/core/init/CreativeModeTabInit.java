package com.stit76.stscenes.core.init;

import com.stit76.stscenes.STScenes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = STScenes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeModeTabInit {
    public static CreativeModeTab CustomDialoguesTAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event){
        CustomDialoguesTAB = event.registerCreativeModeTab(new ResourceLocation(STScenes.MODID,"stscenes_tab"),
                builder -> builder.icon(() -> new ItemStack(ItemInit.NPC_SPAWNER.get())).title(Component.translatable("creativemodetab.stscenes_tab")));
    }
}
