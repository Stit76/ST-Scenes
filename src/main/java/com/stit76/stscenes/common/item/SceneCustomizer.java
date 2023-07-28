package com.stit76.stscenes.common.item;

import com.stit76.stscenes.client.gui.sceneCustomizer.SceneCustomizerScreen;
import com.stit76.stscenes.client.gui.sceneCustomizer.triggerScreens.TouchTriggerScreen;
import com.stit76.stscenes.client.gui.scenesBrowser.ScenesBrowser;
import com.stit76.stscenes.common.entity.AbstractSTNPC;
import com.stit76.stscenes.common.scenes.scene.act.Act;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SceneCustomizer extends Item {
    private boolean objectMode = false;
    private Screen calledScreen;
    public Act objectAct;
    public boolean posMode = false;
    public int point = 0;
    public TouchTriggerScreen touchTrigger;

    public SceneCustomizer(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        Level level = p_41433_.getLevel();
        ItemStack itemStack = p_41433_.getItemInHand(p_41434_);
            if(!this.objectMode) {
                if (level.isClientSide) {
                    ScenesBrowser scenesBrowser = new ScenesBrowser(Component.nullToEmpty("Scenes browser"), this,p_41433_);
                    Minecraft.getInstance().setScreen(scenesBrowser);
                    return InteractionResultHolder.success(itemStack);
                }
            }

        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public InteractionResult useOn(UseOnContext p_41427_) {
        if(posMode) {
            Vec3 clickLocation = p_41427_.getClickLocation();
            touchTrigger.lastAddPos = clickLocation;
            switch (point) {
                case 1: {
                    touchTrigger.trigger.point_1 = clickLocation;
                    p_41427_.getPlayer().sendSystemMessage(Component.nullToEmpty("§3Marked 1 trigger point at coordinates [" + (int) clickLocation.x + ":" + (int) clickLocation.y + ":" + (int) clickLocation.z + "]"));
                    break;
                }
                case 2: {
                    touchTrigger.trigger.point_2 = clickLocation;
                    p_41427_.getPlayer().sendSystemMessage(Component.nullToEmpty("§3Marked 2 trigger point at coordinates [" + (int) clickLocation.x + ":" + (int) clickLocation.y + ":" + (int) clickLocation.z + "]"));
                    break;
                }
            }
            if (p_41427_.getPlayer().level.isClientSide) {
                Minecraft.getInstance().setScreen(touchTrigger);
            }
            posMode = false;
            point = 0;
            touchTrigger = null;
            return InteractionResult.SUCCESS;
        }
        return super.useOn(p_41427_);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack p_41398_, Player p_41399_, LivingEntity p_41400_, InteractionHand p_41401_) {
            if(p_41400_ instanceof AbstractSTNPC){
                if(this.objectMode && this.objectAct != null && calledScreen != null){
                    this.objectAct.entityUUID = p_41400_.getUUID();
                    if(p_41399_.level.isClientSide){
                        Minecraft.getInstance().setScreen(calledScreen);
                        this.calledScreen = null;
                        this.objectMode = false;
                        this.objectAct = null;
                        return InteractionResult.SUCCESS;
                    }
                }
            } else {
                Component component = Component.nullToEmpty("§cError! The selected mob is not an NPC");
                p_41399_.displayClientMessage(component,true);
                return InteractionResult.FAIL;
            }
        return InteractionResult.PASS;
    }

    public void setObjectMode(boolean objectMode) {this.objectMode = objectMode;}

    public boolean isObjectMode() {return objectMode;}

    public void setCalledScreen(SceneCustomizerScreen calledScreen) {this.calledScreen = calledScreen;}
}
