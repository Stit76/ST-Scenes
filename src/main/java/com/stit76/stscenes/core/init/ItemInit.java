package com.stit76.stscenes.core.init;

import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.common.item.NPCSpawner;
import com.stit76.stscenes.common.item.SceneCustomizer;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = STScenes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, STScenes.MODID);

    public static RegistryObject<Item> NPC_SPAWNER = ITEMS.register("npc_spawner",() -> new NPCSpawner(new Item.Properties().stacksTo(1)));
    public static RegistryObject<Item> SCENE_CUSTOMIZER = ITEMS.register("scene_customizer",() -> new SceneCustomizer(new Item.Properties().stacksTo(1)));

    @SubscribeEvent
    public static void registerCreativeModeTabs(BuildCreativeModeTabContentsEvent event){

    }
}
