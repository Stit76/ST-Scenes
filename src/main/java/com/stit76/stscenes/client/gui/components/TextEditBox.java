package com.stit76.stscenes.client.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class TextEditBox extends EditBox {
    private String title;
    private boolean centred = true;
    public TextEditBox(Font p_94114_, int p_94115_, int p_94116_, int p_94117_, int p_94118_, Component p_94119_,boolean centred) {
        this(p_94114_,p_94115_,p_94116_,p_94117_,p_94118_,p_94119_);
        this.centred = centred;
    }
    public TextEditBox(Font p_94114_, int p_94115_, int p_94116_, int p_94117_, int p_94118_, Component p_94119_) {
        super(p_94114_, p_94115_, p_94116_, p_94117_, p_94118_, p_94119_);
        this.title = p_94119_.getString();
    }

    @Override
    public void renderWidget(PoseStack p_94160_, int p_94161_, int p_94162_, float p_94163_) {
        if(centred){
            drawCenteredString(p_94160_, Minecraft.getInstance().font,this.title,this.getX()+(this.width / 2),this.getY() - 10,14737632);
        } else {
            drawString(p_94160_, Minecraft.getInstance().font,this.title,this.getX()-title.length() - 2,this.getY() + (this.height/4),14737632);
        }
        super.renderWidget(p_94160_, p_94161_, p_94162_, p_94163_);
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public void setCentred(boolean centred) {
        this.centred = centred;
    }

    public boolean isCentred() {
        return centred;
    }
}
