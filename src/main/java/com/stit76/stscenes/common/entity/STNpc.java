package com.stit76.stscenes.common.entity;

import com.stit76.stscenes.STScenes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class STNpc extends AbstractSTNPC {
    public STNpc(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }
}
