package com.stit76.stscenes.core.init;

import com.stit76.stscenes.STScenes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = STScenes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeModeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB,STScenes.MODID);
    public static final RegistryObject<CreativeModeTab> STSCENESTAB = CREATIVE_MODE_TABS.register("st_scenes_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.NPC_SPAWNER.get()))
                    .title(Component.nullToEmpty("ST-Scenes"))
                    .displayItems(((p_270258_, p_259752_) -> {
                        p_259752_.accept(ItemInit.NPC_SPAWNER.get());
                        p_259752_.accept(ItemInit.SCENE_CUSTOMIZER.get());
                    }))
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }


}
