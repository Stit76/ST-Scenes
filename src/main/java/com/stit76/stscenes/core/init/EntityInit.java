package com.stit76.stscenes.core.init;

import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.common.entity.STNpc64x64;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, STScenes.MODID);

    public static final RegistryObject<EntityType<STNpc64x64>> ST_NPC = ENTITES.register("st_npc",
            () -> EntityType.Builder.of(STNpc64x64::new, MobCategory.CREATURE).sized(0.6f,1.8f)
            .build(new ResourceLocation(STScenes.MODID,"").toString()));
}
