package com.stit76.stscenes.client.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;

public class CustomButton extends ImageButton {
    private String name;

    public CustomButton(String name,int p_94230_, int p_94231_, int p_94232_, int p_94233_, int p_94234_, int p_94235_, int p_94236_, ResourceLocation p_94237_, int p_94238_, int p_94239_, OnPress p_94240_) {
        super(p_94230_, p_94231_, p_94232_, p_94233_, p_94234_, p_94235_, p_94236_, p_94237_, p_94238_, p_94239_, p_94240_);
        this.name = name;
    }

    @Override
    public void renderWidget(PoseStack p_268099_, int p_267992_, int p_267950_, float p_268076_) {
        this.renderTexture(p_268099_, this.resourceLocation, this.getX(), this.getY(), this.xTexStart, this.yTexStart, this.yDiffTex, this.width, this.height, this.textureWidth, this.textureHeight);
        drawCenteredString(p_268099_, Minecraft.getInstance().font,name,this.getX() + this.width / 2,this.getY() + this.height / 3,14737632);
        super.renderWidget(p_268099_, p_267992_, p_267950_, p_268076_);
    }
}
