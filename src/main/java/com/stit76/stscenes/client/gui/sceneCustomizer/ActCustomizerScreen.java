package com.stit76.stscenes.client.gui.sceneCustomizer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.gui.STScreen;
import com.stit76.stscenes.common.scenes.scene.Scene;
import com.stit76.stscenes.common.scenes.scene.Scenes;
import com.stit76.stscenes.common.scenes.scene.act.Act;
import com.stit76.stscenes.common.scenes.scene.act.acts.FollowUpAct;
import com.stit76.stscenes.common.scenes.scene.act.acts.TellAct;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.synchronization.SetSceneInScenesDataC2SPacket;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class ActCustomizerScreen extends STScreen {
    public static ResourceLocation background = new ResourceLocation(STScenes.MODID, "textures/gui/act_customizer_gui.png");
    Screen back_screen;
    Scene scene;
    private int num;
    int type_p = 0;
    int line;
    int winSizeX = (int) (119 * 1.5);
    int winSizeY = (int) (166 * 1.5);
    int leftPos = 0;
    int topPos = 0;

    protected ActCustomizerScreen(int num,Scene scene,int line, Component name,Screen screen) {
        super(name);
        this.num = num;
        this.scene = scene;
        this.line = line;
        this.back_screen = screen;
    }

    @Override
    protected void init() {
        leftPos = this.width / 2 - (winSizeX / 2);
        topPos = this.height / 2 - (winSizeY / 2);
        int sizeX = 120;
        int sizeY = 20;
        int x = leftPos + ((winSizeX/2) - (sizeX/2));
        int y = topPos + ((winSizeY/4));
        addRenderableWidget(Button.builder(Component.nullToEmpty("Tell"),(p_93751_) -> {
            this.changeAct(new TellAct());
        }).pos(x,y).size(sizeX,sizeY).build());
        addRenderableWidget(Button.builder(Component.nullToEmpty("Follow up"),(p_93751_) -> {
            this.changeAct(new FollowUpAct());
        }).pos(x,y + ((20) + 5)).size(sizeX,sizeY).build());
        super.init();
    }

    @Override
    public void render(PoseStack poseStack, int p_96563_, int p_96564_, float p_96565_) {
        renderBackground(poseStack);
        renderBg(poseStack);
        super.render(poseStack, p_96563_, p_96564_, p_96565_);
    }
    private void renderLine(PoseStack poseStack, String text, int x, int y,boolean isCentred) {
        if(isCentred){
            drawCenteredString(poseStack, this.font, text, leftPos + x, topPos + y, 16777215);
        }else {
            drawString(poseStack, this.font, text, leftPos + x, topPos + y, 16777215);
        }
    }
    private void renderBg(PoseStack poseStack) {
        RenderSystem.setShaderTexture(0, background);
        blit(poseStack, leftPos, topPos, 0, 0, winSizeX,winSizeY, (int) (256 * 1.5), (int) (256 * 1.5));
        renderLine(poseStack,this.title.getString(),7,7,false);
    }
    private void changeAct(Act act){
        UUID entityUUID = this.scene.getActs().get(line).entityUUID;
        this.scene.getActs().set(this.line,act);
        this.scene.getActs().get(this.line).entityUUID = entityUUID;
        this.minecraft.setScreen(back_screen);
    }

    @Override
    public void removed() {
        SimpleNetworkWrapper.sendToServer(new SetSceneInScenesDataC2SPacket(this.num,this.scene, Scenes.sceneList));
        super.removed();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
