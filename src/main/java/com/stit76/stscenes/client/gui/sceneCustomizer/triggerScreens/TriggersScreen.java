package com.stit76.stscenes.client.gui.sceneCustomizer.triggerScreens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.common.item.SceneCustomizer;
import com.stit76.stscenes.common.scenes.scene.Scene;
import com.stit76.stscenes.common.scenes.scene.trigger.TouchTrigger;
import com.stit76.stscenes.common.scenes.scene.trigger.Trigger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TriggersScreen extends Screen {
    public static ResourceLocation background = new ResourceLocation(STScenes.MODID, "textures/gui/act_customizer_gui.png");
    private Scene scene;
    private SceneCustomizer sceneCustomizer;
    int winSizeX = (int) (119 * 1.5);
    int winSizeY = (int) (166 * 1.5);
    int leftPos = 0;
    int topPos = 0;
    private int page = 1;
    private int max_page;


    public TriggersScreen(Component p_96550_, Scene scene,SceneCustomizer sceneCustomizer) {
        super(p_96550_);
        this.scene = scene;
        this.sceneCustomizer = sceneCustomizer;
    }

    @Override
    protected void init() {
        leftPos = this.width / 2 - (winSizeX / 2);
        topPos = this.height / 2 - (winSizeY / 2);
        initToolsMenu();
        initTriggerMenu(5);
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
            drawCenteredString(poseStack, Minecraft.getInstance().font, text, leftPos + x, topPos + y, 16777215);
        }else {
            drawString(poseStack, Minecraft.getInstance().font, text, leftPos + x, topPos + y, 16777215);
        }
    }
    private void renderBg(PoseStack poseStack) {
        RenderSystem.setShaderTexture(0, background);
        blit(poseStack, leftPos, topPos, 0, 0, winSizeX,winSizeY, (int) (256 * 1.5), (int) (256 * 1.5));
        renderLine(poseStack,this.title.getString(),7,7,false);
    }
    private void initToolsMenu() {
        addRenderableWidget(Button.builder(Component.nullToEmpty("Add"),(p_93751_) -> {
            scene.triggers.add(new TouchTrigger());
            this.minecraft.setScreen(this);
        }).pos(leftPos + (winSizeX - 35),topPos + 5).size(30,15).build());
    }
    private void initTriggerMenu(int line_on_page) {
        int numi = -1;
        int pn = (line_on_page * page) - line_on_page;
        for (int i = pn;i < scene.triggers.size() & i < pn + line_on_page; i++) {
            numi++;
            initTrigger(i,leftPos + 35,topPos + 39 + (((26 * (numi + 1)) + (3 *numi))),100,20);
        }
    }
    private void initTrigger(int num, int x, int y, int sizeX, int sizeY){
        addRenderableWidget(Button.builder(Component.nullToEmpty(scene.triggers.get(num).name),(p_93751_) -> {
            this.minecraft.setScreen(new TriggerCustomizerScreen(Component.nullToEmpty("Change trigger type"),scene,num,sceneCustomizer,this));
        }).pos(x,y).size(sizeX,sizeY).build());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
