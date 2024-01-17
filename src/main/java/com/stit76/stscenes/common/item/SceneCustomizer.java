package com.stit76.stscenes.common.item;

import com.stit76.stscenes.client.gui.sceneCustomizer.SceneCustomizerScreen;
import com.stit76.stscenes.client.gui.sceneCustomizer.triggerScreens.ClickNPCTriggerScreen;
import com.stit76.stscenes.client.gui.sceneCustomizer.triggerScreens.TouchTriggerScreen;
import com.stit76.stscenes.client.gui.scenesBrowser.ScenesBrowser;
import com.stit76.stscenes.common.entity.AbstractSTNPC;
import com.stit76.stscenes.common.scenes.scene.Scenes;
import com.stit76.stscenes.common.scenes.scene.act.compoundact.CAct;
import com.stit76.stscenes.common.scenes.scene.trigger.ClickNpcTrigger;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.synchronization.SetSceneInScenesDataC2SPacket;
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
    public CAct objectAct;
    public boolean posMode = false;
    public int point = 0;
    public TouchTriggerScreen touchTrigger;

    public SceneCustomizer(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        Level level = p_41433_.level();
        ItemStack itemStack = p_41433_.getItemInHand(p_41434_);
            if(!this.objectMode) {
                if (level.isClientSide) {
                    ScenesBrowser scenesBrowser = new ScenesBrowser(Component.translatable("scene_customizer.scenes_browser.title"), this,p_41433_);
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
                    p_41427_.getPlayer().sendSystemMessage(Component.nullToEmpty(Component.translatable("scene_customizer.marked1").getString() + (int) clickLocation.x + ":" + (int) clickLocation.y + ":" + (int) clickLocation.z + "]"));
                    break;
                }
                case 2: {
                    touchTrigger.trigger.point_2 = clickLocation;
                    p_41427_.getPlayer().sendSystemMessage(Component.nullToEmpty(Component.translatable("scene_customizer.marked2").getString() + (int) clickLocation.x + ":" + (int) clickLocation.y + ":" + (int) clickLocation.z + "]"));
                    break;
                }
            }
            if (p_41427_.getPlayer().level().isClientSide) {
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
            if(this.objectMode && calledScreen != null){
                if(p_41400_ instanceof AbstractSTNPC){
                    if(p_41399_.level().isClientSide){
                        if(calledScreen instanceof SceneCustomizerScreen && this.objectAct != null){
                            this.objectAct.object = ((AbstractSTNPC) p_41400_).visualData.getName();
                            this.objectAct.entityUUID = p_41400_.getUUID();
                            SimpleNetworkWrapper.sendToServer(new SetSceneInScenesDataC2SPacket(((SceneCustomizerScreen) calledScreen).num, ((SceneCustomizerScreen) calledScreen).scene, Scenes.sceneList));
                        }
                        if(calledScreen instanceof ClickNPCTriggerScreen){
                            ((ClickNPCTriggerScreen) calledScreen).trigger.entityUUID = p_41400_.getUUID();
                        }
                        Minecraft.getInstance().setScreen(calledScreen);
                        this.calledScreen = null;
                        this.objectMode = false;
                        this.objectAct = null;
                        return InteractionResult.SUCCESS;
                    }

                } else {
                    Component component = Component.translatable("scene_customizer.mob_error");
                    p_41399_.displayClientMessage(component,true);
                    return InteractionResult.FAIL;
                }
            }
        return InteractionResult.PASS;
    }

    public void setObjectMode(boolean objectMode) {this.objectMode = objectMode;}

    public boolean isObjectMode() {return objectMode;}

    public void setCalledScreen(Screen calledScreen) {this.calledScreen = calledScreen;}
}
