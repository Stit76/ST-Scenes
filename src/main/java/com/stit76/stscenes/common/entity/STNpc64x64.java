package com.stit76.stscenes.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class STNpc64x64 extends AbstractSTNPC {
    public STNpc64x64(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }
}
