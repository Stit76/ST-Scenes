package com.stit76.stscenes.common.entity;

import com.stit76.stscenes.common.ai.goal.AlwaysLookAtPlayerGoal;
import com.stit76.stscenes.common.ai.goal.GoToPointGoal;
import com.stit76.stscenes.common.entity.data.STNPCBehaviourData;
import com.stit76.stscenes.common.ai.goal.FollowGoal;
import com.stit76.stscenes.common.entity.data.STNPCVisualData;
import com.stit76.stscenes.core.init.EntityInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.UUID;

public abstract class AbstractSTNPC extends Animal {
    public STNPCVisualData visualData;
    public STNPCBehaviourData behaviourData;
    private UUID lastInteractPlayer;
    private boolean interact;
    protected AbstractSTNPC(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        setStockValues();
    }
    protected void setStockValues(){
        visualData = new STNPCVisualData(this);
        behaviourData = new STNPCBehaviourData(this);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(this.visualData.TEXTURE,"stscenes:textures/entity/player/wide/steve.png");
        this.getEntityData().define(this.visualData.URL,"");
        this.getEntityData().define(this.visualData.NAME,"Steve");
        this.getEntityData().define(this.visualData.SHOW_NAME,true);
        this.getEntityData().define(this.visualData.ALWAYES_SHOW_NAME,true);
        this.getEntityData().define(this.visualData.HEAD_MODEL,"none");
        this.getEntityData().define(this.visualData.BODY_MODEL,"none");
        this.getEntityData().define(this.visualData.ARMS_MODEL,"none");
        this.getEntityData().define(this.visualData.LEGS_MODEL,"none");
        //
        this.getEntityData().define(this.behaviourData.FOLLOW,false);
        this.getEntityData().define(this.behaviourData.FOLLOW_PLAYER,"");
        this.getEntityData().define(this.behaviourData.GO_TO_POINT,false);
        this.getEntityData().define(this.behaviourData.POINT_TO_GO,new Vector3f());
        this.getEntityData().define(this.behaviourData.GO_TO_POINT_SPEED,1.25f);

        this.getEntityData().define(this.behaviourData.LOOK_AT_POINT,false);
        this.getEntityData().define(this.behaviourData.POINT_LOOK,new Vector3f());
        this.getEntityData().define(this.behaviourData.IMMORTALITY,false);

        super.defineSynchedData();
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH,20.00D).add(Attributes.MOVEMENT_SPEED,0.25D);
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new FollowGoal(this,1.25f,false));
        this.goalSelector.addGoal(1,new GoToPointGoal(this));
        this.goalSelector.addGoal(2,new AlwaysLookAtPlayerGoal(this,Player.class,15,false));

        super.registerGoals();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return EntityInit.ST_NPC.get().create(level);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_27587_) {
        this.visualData.addAdditionalSaveData(p_27587_);
        this.behaviourData.addAdditionalSaveData(p_27587_);
        super.addAdditionalSaveData(p_27587_);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_27576_) {
        this.visualData.readAdditionalSaveData(p_27576_);
        this.behaviourData.readAdditionalSaveData(p_27576_);
        super.readAdditionalSaveData(p_27576_);
    }

    @Override
    public InteractionResult mobInteract(Player p_27584_, InteractionHand p_27585_) {
        this.interact = true;
        this.lastInteractPlayer = p_27584_.getUUID();
        return super.mobInteract(p_27584_, p_27585_);
    }

    @Override
    public void tick() {
        if(this.visualData.getShowName() && this.visualData.getName() != ""){
            this.setCustomNameVisible(this.visualData.getAlwaysShowName());
            setCustomName(Component.nullToEmpty(visualData.getName()));
        } else {
            setCustomName(null);
            this.setCustomNameVisible(false);
        }
        if(this.behaviourData.isImmortality()){
            this.setHealth(this.getMaxHealth());
        }
        super.tick();
    }
    public boolean isInteractPlayer(UUID player) {
        boolean interactFlag = this.interact;
        this.interact = false;
        if(player.equals(this.lastInteractPlayer)){
            return interactFlag;
        }
        return false;
    }
    public boolean isInteract() {
        boolean interactFlag = this.interact;
        this.interact = false;
        return interactFlag;
    }

    public UUID getLastInteractPlayer() {
        return lastInteractPlayer;
    }
}
