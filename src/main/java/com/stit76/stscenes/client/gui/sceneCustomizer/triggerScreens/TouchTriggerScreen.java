package com.stit76.stscenes.client.gui.sceneCustomizer.triggerScreens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.gui.STScreen;
import com.stit76.stscenes.client.gui.sceneCustomizer.SceneCustomizerScreen;
import com.stit76.stscenes.common.item.SceneCustomizer;
import com.stit76.stscenes.common.scenes.scene.Scenes;
import com.stit76.stscenes.common.scenes.scene.trigger.TouchTrigger;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.synchronization.SetSceneInScenesDataC2SPacket;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class TouchTriggerScreen extends STScreen {
    public static ResourceLocation background = new ResourceLocation(STScenes.MODID, "textures/gui/act_customizer_gui.png");
    private Screen lastScreen;
    private SceneCustomizerScreen screen;
    public TouchTrigger trigger;
    private SceneCustomizer sceneCustomizer;
    public Vec3 lastAddPos;
    int winSizeX = (int) (119 * 1.5);
    int winSizeY = (int) (166 * 1.5);
    int leftPos = 0;
    int topPos = 0;
    private EditBox nameEditBox;
    private EditBox playerEditBox;


    protected TouchTriggerScreen(Component p_96550_,SceneCustomizerScreen screen, TouchTrigger trigger, SceneCustomizer sceneCustomizer, Screen lastScreen) {
        super(p_96550_);
        this.screen = screen;
        this.trigger = trigger;
        this.lastScreen = lastScreen;
        this.sceneCustomizer =sceneCustomizer;
    }

    @Override
    protected void init() {
        leftPos = this.width / 2 - (winSizeX / 2);
        topPos = this.height / 2 - (winSizeY / 2);
        addRenderableWidget(Button.builder(Component.nullToEmpty("Back"),(p_93751_) -> {
            this.minecraft.setScreen(this.lastScreen);
        }).pos(leftPos + (winSizeX - 35),topPos + 5).size(30,15).build());
        addRenderableWidget(Button.builder(Component.nullToEmpty("Delete"),(p_93751_) -> {

        }).pos(leftPos + (winSizeX - 75),topPos + 5).size(40,15).build());
        this.nameEditBox = new EditBox(this.font,leftPos + 7,topPos + 7, 60,20,Component.nullToEmpty("name"));
        this.nameEditBox.setBordered(false);
        this.nameEditBox.setValue(this.trigger.name);
        addRenderableWidget(this.nameEditBox);
        playerEditBox = new EditBox(this.font,leftPos + 35,topPos + 65, 100,20,Component.nullToEmpty("player"));
        this.playerEditBox.setValue(trigger.getPlayer_nick());
        addRenderableWidget(playerEditBox);
        addRenderableWidget(Button.builder(Component.nullToEmpty(trigger.point_1 != null ? (int) trigger.point_1.x + " " + (int) trigger.point_1.y + " " + (int) trigger.point_1.z : "Point 1"),(p_93751_) -> {
            this.sceneCustomizer.posMode = true;
            this.sceneCustomizer.point = 1;
            this.sceneCustomizer.touchTrigger = this;
            this.minecraft.setScreen(null);
        }).pos(leftPos + 35,topPos + 90).size(50,20).build());
        addRenderableWidget(Button.builder(Component.nullToEmpty(trigger.point_2 != null ? (int) trigger.point_2.x + " " + (int) trigger.point_2.y + " " + (int) trigger.point_2.z : "Point 2"),(p_93751_) -> {
            this.sceneCustomizer.posMode = true;
            this.sceneCustomizer.point = 2;
            this.sceneCustomizer.touchTrigger = this;
            this.minecraft.setScreen(null);
        }).pos(leftPos + 85,topPos + 90).size(50,20).build());
        super.init();
    }

    @Override
    public void render(PoseStack poseStack, int p_96563_, int p_96564_, float p_96565_) {
        renderBackground(poseStack);
        renderBg(poseStack);
        super.render(poseStack, p_96563_, p_96564_, p_96565_);
    }
    private void renderBg(PoseStack poseStack) {
        RenderSystem.setShaderTexture(0, background);
        blit(poseStack, leftPos, topPos, 0, 0, winSizeX,winSizeY, (int) (256 * 1.5), (int) (256 * 1.5));
    }

    @Override
    public void removed() {
        this.trigger.name = nameEditBox.getValue();
        this.trigger.player_nick = this.playerEditBox.getValue();
        SimpleNetworkWrapper.sendToServer(new SetSceneInScenesDataC2SPacket(screen.num,screen.scene, Scenes.sceneList));
        super.removed();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
