package com.stit76.stscenes.client.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.utils.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StringButton extends AbstractButton {
    protected final StringButton.OnPress onPress;
    protected String hint = "";
    public StringButton(int p_93365_, int p_93366_, int p_93367_, int p_93368_, Component p_93369_, OnPress onPress) {
        super(p_93365_, p_93366_, p_93367_, p_93368_, p_93369_);
        this.onPress = onPress;
    }
    public StringButton(int p_93365_, int p_93366_, int p_93367_, int p_93368_, Component p_93369_, OnPress onPress,String hint) {
        super(p_93365_, p_93366_, p_93367_, p_93368_, p_93369_);
        this.onPress = onPress;
        this.hint = hint;
    }
    public static void drawShorterString(GuiGraphics poseStack, String string, int x, int y, int length){
        poseStack.drawString(Minecraft.getInstance().font, StringUtils.getShorterStringBy(string,length),x,y,16777215);
    }

    @Override
    public void renderWidget(GuiGraphics p_275468_, int p_275505_, int p_275674_, float p_275696_) {
        if(this.isHovered){
            if(!this.hint.isEmpty()){
                drawShorterString(p_275468_,this.hint,this.getX() +(width / 40),this.getY() + (height / 2),this.width);
            } else {
                drawShorterString(p_275468_,this.getMessage().getString(),this.getX() +(width / 40),this.getY() + (height / 2),this.width);
            }
        } else {
            drawShorterString(p_275468_,this.getMessage().getString(),this.getX() +(width / 40),this.getY() + (height / 2),this.width);
        }
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }

    @OnlyIn(Dist.CLIENT)
    public interface OnPress {
        void onPress(StringButton p_93751_);
    }
}
