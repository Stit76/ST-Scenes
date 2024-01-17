package com.stit76.stscenes.common.ai.goal;

import com.stit76.stscenes.common.entity.AbstractSTNPC;
import com.stit76.stscenes.common.entity.data.STNPCBehaviourData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class AlwaysLookAtPlayerGoal extends Goal {
    protected final Mob mob;
    protected final Class<? extends LivingEntity> lookAtType;
    protected final TargetingConditions lookAtContext;

    public AlwaysLookAtPlayerGoal(Mob p_25520_, Class<? extends LivingEntity> p_25521_, float p_25522_) {
        this(p_25520_, p_25521_, p_25522_, 0.02F);
    }

    public AlwaysLookAtPlayerGoal(Mob p_25524_, Class<? extends LivingEntity> p_25525_, float p_25526_, float p_25527_) {
        this(p_25524_, p_25525_, p_25526_, false);
    }

    public AlwaysLookAtPlayerGoal(Mob p_148118_, Class<? extends LivingEntity> p_148119_, float p_148120_,boolean p_148122_) {
        this.mob = p_148118_;
        this.lookAtType = p_148119_;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        if (p_148119_ == Player.class) {
            this.lookAtContext = TargetingConditions.forNonCombat().range((double)p_148120_).selector((p_25531_) -> {
                return EntitySelector.notRiding(p_148118_).test(p_25531_);
            });
        } else {
            this.lookAtContext = TargetingConditions.forNonCombat().range((double)p_148120_);
        }

    }

    public boolean canUse() {
        return ((AbstractSTNPC) mob).behaviourData.getLookAtPoint();
    }

    public boolean canContinueToUse() {
        return ((AbstractSTNPC) mob).behaviourData.getLookAtPoint();
    }

    public void start() {

    }

    public void stop() {

    }

    public void tick() {
        STNPCBehaviourData bd = ((AbstractSTNPC) mob).behaviourData;
        this.mob.getLookControl().setLookAt(bd.getPointLook().x, bd.getPointLook().y, bd.getPointLook().z);
    }
}
