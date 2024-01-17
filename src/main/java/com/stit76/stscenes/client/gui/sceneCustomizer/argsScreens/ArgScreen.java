package com.stit76.stscenes.client.gui.sceneCustomizer.argsScreens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.gui.STScreen;
import com.stit76.stscenes.client.gui.components.TextEditBox;
import com.stit76.stscenes.client.gui.sceneCustomizer.SceneCustomizerScreen;
import com.stit76.stscenes.common.scenes.scene.Scenes;
import com.stit76.stscenes.common.scenes.scene.act.Act;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.synchronization.SetSceneInScenesDataC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class ArgScreen extends STScreen {
    public static ResourceLocation background = new ResourceLocation(STScenes.MODID, "textures/gui/act_customizer_gui.png");
    protected int winSizeX = (int) (119 * 1.5);
    protected int winSizeY = (int) (166 * 1.5);
    protected int leftPos = 0;
    protected int topPos = 0;
    protected SceneCustomizerScreen backScreen;
    protected Act act;
    private TextEditBox editBox_1;


    protected ArgScreen(Component p_96550_, SceneCustomizerScreen back_screen, Act act) {
        super(p_96550_);
        this.backScreen = back_screen;
        this.act = act;
    }

    @Override
    protected void init() {
        leftPos = this.width / 2 - (winSizeX / 2);
        topPos = this.height / 2 - (winSizeY / 2);
        editBox_1 = new TextEditBox(Minecraft.getInstance().font,leftPos + 15,topPos + (winSizeY - 20),60,15,Component.nullToEmpty("Delay"));
        editBox_1.setTitle(Component.translatable("scene_customizer.arg_screen.delay").getString()+":");
        editBox_1.setValue(String.valueOf(act.delay));
        addRenderableWidget(editBox_1);
        addRenderableWidget(Button.builder(Component.nullToEmpty("Ok"),(p_93751_) -> {
            OnPressOk();
        }).pos(leftPos + (winSizeX - 67),topPos + (winSizeY - 20)).size(60,15).build());
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
        poseStack.blit(background,leftPos, topPos, 0, 0, winSizeX,winSizeY, (int) (256 * 1.5), (int) (256 * 1.5));
        renderLine(poseStack,this.title.getString(),7,7,false);
    }
    protected void OnPressOk(){
        if(editBox_1.getValue() != null && !editBox_1.getValue().isEmpty()){
            boolean isOnlyDigits = true;
            for(int i = 0; i < editBox_1.getValue().length() && isOnlyDigits; i++) {
                if(!Character.isDigit(editBox_1.getValue().charAt(i))) {
                    isOnlyDigits = false;
                }
            }
            if(isOnlyDigits){
                act.delay = Integer.parseInt(editBox_1.getValue());
            }
        }
        this.minecraft.setScreen(this.backScreen);
    }

    @Override
    public void removed() {
        SimpleNetworkWrapper.sendToServer(new SetSceneInScenesDataC2SPacket(this.backScreen.num,this.backScreen.scene, Scenes.sceneList));
        super.removed();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
    @Override
    public boolean isClientUpdate() {
        return false;
    }
}
