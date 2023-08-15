package com.stit76.stscenes.client.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StringButton extends AbstractButton {
    protected final StringButton.OnPress onPress;
    public StringButton(int p_93365_, int p_93366_, int p_93367_, int p_93368_, Component p_93369_, OnPress onPress) {
        super(p_93365_, p_93366_, p_93367_, p_93368_, p_93369_);
        this.onPress = onPress;
    }
    public static void drawEndingLine(PoseStack poseStack,String string,int x,int y,int length){
        String s = string;
        int l = s.length() - (17 * length / 100);
        if(l < 0) l = 0;
        String s1 = l <= 0 ? s.substring(l) : "..." + s.substring(l);
        drawString(poseStack,Minecraft.getInstance().font, s1,x,y,16777215);
    }

    @Override
    public void renderWidget(PoseStack p_275468_, int p_275505_, int p_275674_, float p_275696_) {
        drawEndingLine(p_275468_,this.getMessage().getString(),this.getX() + 5,this.getY() + 5,this.width);
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
