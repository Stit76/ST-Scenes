package com.stit76.stscenes.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.client.gui.scenesBrowser.ScenesBrowser;
import com.stit76.stscenes.common.scenes.scene.Scene;
import com.stit76.stscenes.common.scenes.scene.Scenes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class SceneButton extends HintButton {
    private Scene scene;
    public SceneButton(int p_259075_, int p_259271_, int p_260232_, int p_260028_, Component p_259351_, OnPress p_260152_,Scene scene) {
        super(p_259075_, p_259271_, p_260232_, p_260028_, p_259351_, p_260152_);
        this.scene = scene;
    }

    @Override
    public void renderWidget(GuiGraphics poseStack, int p_275505_, int p_275674_, float p_275696_) {
        //RenderSystem.enableDepthTest();
        //poseStack.pose().translate(0,0,-100D);
        //RenderSystem.setShaderTexture(0, ScenesBrowser.background);
        if(scene.isActive()){
            poseStack.blit(ScenesBrowser.background, this.getX() - 8, this.getY(), 213, 22, 9,20, 256,256);
        } else {
            poseStack.blit(ScenesBrowser.background, this.getX() - 8, this.getY(), 213, 0, 9,20, 256, 256);
        }
        int index = -1;
        for (int i = 0; i < Scenes.sceneList.size(); i++) {
            if(scene.getUUID() == Scenes.sceneList.get(i).getUUID()){
                index = i;
            }
        }
        this.hintText = new String[] {
                Component.translatable("scene_customizer.hint.name").getString() + this.scene.getName(),
                Component.translatable("scene_customizer.hint.index").getString() + String.valueOf(index),
                Component.translatable("scene_customizer.hint.date").getString() + this.scene.getDate()
        };
        super.renderWidget(poseStack, p_275505_, p_275674_, p_275696_);
    }

}
