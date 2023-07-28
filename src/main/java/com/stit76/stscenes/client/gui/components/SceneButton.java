package com.stit76.stscenes.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.client.gui.scenesBrowser.ScenesBrowser;
import com.stit76.stscenes.common.scenes.scene.Scene;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class SceneButton extends Button {
    private Scene scene;
    public SceneButton(int p_259075_, int p_259271_, int p_260232_, int p_260028_, Component p_259351_, OnPress p_260152_,Scene scene) {
        super(p_259075_, p_259271_, p_260232_, p_260028_, p_259351_, p_260152_, DEFAULT_NARRATION);
        this.scene = scene;
    }

    @Override
    public void renderWidget(PoseStack poseStack, int p_275505_, int p_275674_, float p_275696_) {
        poseStack.pushPose();
        RenderSystem.enableDepthTest();
        poseStack.translate(0,0,-100D);
        RenderSystem.setShaderTexture(0, ScenesBrowser.background);
        if(scene.isActive()){
            blit(poseStack, this.getX() - 8, this.getY(), 213, 22, 9,20, 256,256);
        } else {
            blit(poseStack, this.getX() - 8, this.getY(), 213, 0, 9,20, 256, 256);
        }
        super.renderWidget(poseStack, p_275505_, p_275674_, p_275696_);
        poseStack.popPose();
        poseStack.pushPose();
        if(this.isHovered){
            RenderSystem.enableDepthTest();
            poseStack.translate(0,0,200D);
            RenderSystem.setShaderTexture(0, ScenesBrowser.background);
            blit(poseStack, p_275505_, p_275674_, 0, (int) (166 * 1.5), (int) (102 * 1.5), (int) (33 * 1.5), (int) (256 * 1.5), (int) (256 * 1.5));
            drawString(poseStack, Minecraft.getInstance().font,"Name: " + scene.getName(),p_275505_ + 10,p_275674_ + 10,16777215);
            drawString(poseStack, Minecraft.getInstance().font,"Date: " + scene.getDate(),p_275505_ + 10,p_275674_ + 30,16777215);
        }
        poseStack.popPose();
    }

}
