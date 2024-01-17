package com.stit76.stscenes.client.gui.sceneCustomizer.triggerScreens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.gui.STScreen;
import com.stit76.stscenes.client.gui.sceneCustomizer.SceneCustomizerScreen;
import com.stit76.stscenes.common.item.SceneCustomizer;
import com.stit76.stscenes.common.scenes.scene.Scene;
import com.stit76.stscenes.common.scenes.scene.Scenes;
import com.stit76.stscenes.common.scenes.scene.trigger.ClickNpcTrigger;
import com.stit76.stscenes.common.scenes.scene.trigger.TouchTrigger;
import com.stit76.stscenes.common.scenes.scene.trigger.Trigger;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.synchronization.SetSceneInScenesDataC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class TriggerCustomizerScreen extends STScreen {
    public static ResourceLocation background = new ResourceLocation(STScenes.MODID, "textures/gui/act_customizer_gui.png");
    private net.minecraft.client.gui.screens.Screen lastScreen;
    private SceneCustomizerScreen screen;
    private Scene scene;
    private int trig_num;
    private SceneCustomizer sceneCustomizer;
    int winSizeX = (int) (119 * 1.5);
    int winSizeY = (int) (166 * 1.5);
    int leftPos = 0;
    int topPos = 0;
    short num;


    protected TriggerCustomizerScreen(Component p_96550_, SceneCustomizerScreen screen, Scene scene, int trig_num, SceneCustomizer sceneCustomizer, net.minecraft.client.gui.screens.Screen lastScreen) {
        super(p_96550_);
        this.screen = screen;
        this.scene = scene;
        this.trig_num = trig_num;
        this.lastScreen = lastScreen;
        this.sceneCustomizer = sceneCustomizer;
    }

    @Override
    protected void init() {
        leftPos = this.width / 2 - (winSizeX / 2);
        topPos = this.height / 2 - (winSizeY / 2);
        Trigger trigger = scene.triggers.get(trig_num);
        if(trigger instanceof  TouchTrigger){
            this.minecraft.setScreen(new TouchTriggerScreen(Component.nullToEmpty(trigger.name),screen, (TouchTrigger) trigger,this.sceneCustomizer,this.lastScreen));
        } if(trigger instanceof  ClickNpcTrigger){
            this.minecraft.setScreen(new ClickNPCTriggerScreen(Component.nullToEmpty(trigger.name),screen, (ClickNpcTrigger) trigger,this.sceneCustomizer,this.lastScreen));
        } else {
            initChangeTrigger(leftPos + 35,topPos + 65);
        }
        super.init();
    }

    @Override
    public void render(GuiGraphics poseStack, int p_96563_, int p_96564_, float p_96565_) {
        renderBackground(poseStack);
        renderBg(poseStack);
        super.render(poseStack, p_96563_, p_96564_, p_96565_);
    }
    private void renderLine(GuiGraphics poseStack, String text, int x, int y,boolean isCentred) {
        if(isCentred){
            poseStack.drawCenteredString(Minecraft.getInstance().font, text, leftPos + x, topPos + y, 16777215);
        }else {
            poseStack.drawString(Minecraft.getInstance().font, text, leftPos + x, topPos + y, 16777215);
        }
    }
    private void renderBg(GuiGraphics poseStack) {
        //RenderSystem.setShaderTexture(0, background);
        poseStack.blit(background, leftPos, topPos, 0, 0, winSizeX,winSizeY, (int) (256 * 1.5), (int) (256 * 1.5));
        renderLine(poseStack,this.title.getString(),7,7,false);
    }
    private void initChangeTrigger(int x ,int y){
        addRenderableWidget(Button.builder(Component.nullToEmpty("TouchTrigger"),(p_93751_) -> {
            this.scene.triggers.set(trig_num,new TouchTrigger());
            this.minecraft.setScreen(this.lastScreen);
        }).pos(x,y).size(100,20).build());
        addRenderableWidget(Button.builder(Component.nullToEmpty("ClickNPCTrigger"),(p_93751_) -> {
            this.scene.triggers.set(trig_num,new ClickNpcTrigger("New trigger",true,"","ac30ab68-c978-4091-845e-b0fac56f500f"));
            this.minecraft.setScreen(this.lastScreen);
        }).pos(x,y + (25 * 1)).size(100,20).build());
    }

    @Override
    public void removed() {
        SimpleNetworkWrapper.sendToServer(new SetSceneInScenesDataC2SPacket(screen.num,screen.scene, Scenes.sceneList));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
