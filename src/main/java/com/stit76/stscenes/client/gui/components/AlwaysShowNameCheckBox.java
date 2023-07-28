package com.stit76.stscenes.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.common.entity.AbstractSTNPC;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.server.visualData.ChangeNameAlwaysVisibleC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AlwaysShowNameCheckBox extends AbstractButton {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
    private static final int TEXT_COLOR = 14737632;
    private boolean selected;
    private final boolean showLabel;
    private AbstractSTNPC npc;

    public AlwaysShowNameCheckBox(AbstractSTNPC npc, int p_93833_, int p_93834_, int p_93835_, int p_93836_, Component p_93837_, boolean p_93838_, boolean p_93839_) {
        super(p_93833_, p_93834_, p_93835_, p_93836_, p_93837_);
        this.npc = npc;
        this.selected = p_93838_;
        this.showLabel = p_93839_;
    }


    public void onPress() {
        this.selected = !this.selected;
        if(npc != null){
            if(npc instanceof AbstractSTNPC){
                if(this.npc.visualData.getShowName()){
                    SimpleNetworkWrapper.sendToServer(new ChangeNameAlwaysVisibleC2SPacket(npc.getUUID(),this.getSelected()));
                }
            }
        }
    }

    public boolean getSelected() {
        return this.selected;
    }
    public void setSelected(boolean bool) {
        this.selected = bool;
    }

    public void updateWidgetNarration(NarrationElementOutput p_260253_) {
        p_260253_.add(NarratedElementType.TITLE, this.createNarrationMessage());
        if (this.active) {
            if (this.isFocused()) {
                p_260253_.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.focused"));
            } else {
                p_260253_.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.hovered"));
            }
        }

    }

    public void renderWidget(PoseStack p_93843_, int p_93844_, int p_93845_, float p_93846_) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.enableDepthTest();
        Font font = minecraft.font;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        blit(p_93843_, this.getX(), this.getY(), this.isFocused() ? 20.0F : 0.0F, this.selected ? 20.0F : 0.0F, 20, this.height, 64, 64);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.showLabel) {
            drawString(p_93843_, font, this.getMessage(), this.getX() + 24, this.getY() + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
        }
    }
}
