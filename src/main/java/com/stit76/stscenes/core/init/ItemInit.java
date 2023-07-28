package com.stit76.stscenes.core.init;

import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.common.item.NPCSpawner;
import com.stit76.stscenes.common.item.SceneCustomizer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, STScenes.MODID);

    public static RegistryObject<Item> NPC_SPAWNER = ITEMS.register("npc_spawner",() -> new NPCSpawner(new Item.Properties().stacksTo(1)));
    public static RegistryObject<Item> SCENE_CUSTOMIZER = ITEMS.register("scene_customizer",() -> new SceneCustomizer(new Item.Properties().stacksTo(1)));

    public static void addItemCreativeTab(CreativeModeTabEvent.BuildContents event, Supplier<? extends ItemLike> item, CreativeModeTab tab) {
        if (event.getTab() == tab)
            event.accept(item);
    }
    public static void addItemsCreativeTab(CreativeModeTabEvent.BuildContents event) {
        addItemCreativeTab(event,NPC_SPAWNER, CreativeModeTabInit.CustomDialoguesTAB);
        addItemCreativeTab(event,SCENE_CUSTOMIZER, CreativeModeTabInit.CustomDialoguesTAB);
    }
}
