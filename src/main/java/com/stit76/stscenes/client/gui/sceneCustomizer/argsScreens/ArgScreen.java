package com.stit76.stscenes.client.gui.sceneCustomizer.argsScreens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.gui.components.TextEditBox;
import com.stit76.stscenes.common.scenes.scene.act.Act;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class ArgScreen extends Screen {
    public static ResourceLocation background = new ResourceLocation(STScenes.MODID, "textures/gui/act_customizer_gui.png");
    int winSizeX = (int) (119 * 1.5);
    int winSizeY = (int) (166 * 1.5);
    int leftPos = 0;
    int topPos = 0;
    protected Screen back_screen;
    protected Act act;
    private TextEditBox editBox_1;


    protected ArgScreen(Component p_96550_,Screen back_screen,Act act) {
        super(p_96550_);
        this.back_screen = back_screen;
        this.act = act;
    }

    @Override
    protected void init() {
        leftPos = this.width / 2 - (winSizeX / 2);
        topPos = this.height / 2 - (winSizeY / 2);
        editBox_1 = new TextEditBox(Minecraft.getInstance().font,leftPos + 15,topPos + (winSizeY - 20),60,15,Component.nullToEmpty("Delay"));
        editBox_1.setTitle("Delay:");
        editBox_1.setValue(String.valueOf(act.delay));
        addRenderableWidget(editBox_1);
        addRenderableWidget(Button.builder(Component.nullToEmpty("Ok"),(p_93751_) -> {
            OnPressOk();
        }).pos(leftPos + (winSizeX - 67),topPos + (winSizeY - 20)).size(60,15).build());
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
    protected void OnPressOk(){
        act.delay = Integer.parseInt(editBox_1.getValue());
        this.minecraft.setScreen(this.back_screen);
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
