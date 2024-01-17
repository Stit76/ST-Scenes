package com.stit76.stscenes.client.gui.npccustomizer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.gui.STScreen;
import com.stit76.stscenes.client.gui.components.AlwaysShowNameCheckBox;
import com.stit76.stscenes.client.gui.components.ShowNameCheckBox;
import com.stit76.stscenes.client.gui.modeleditor.MainME;
import com.stit76.stscenes.common.entity.AbstractSTNPC;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.server.behaviourData.ChangeImmortalityC2SPacket;
import com.stit76.stscenes.networking.packet.server.visualData.ChangeNameC2SPacket;
import com.stit76.stscenes.networking.packet.server.visualData.ChangeTextureC2SPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class NpcCustomizerScreen extends STScreen {
    public static ResourceLocation background = new ResourceLocation(STScenes.MODID, "textures/gui/npc_spawner_gui.png");
    int xMouse;
    int yMouse;
    int winSizeX = (int) (212 * 1.5);
    int winSizeY = (int) (170 * 1.5);
    int leftPos = 0;
    int topPos = 0;
    private AbstractSTNPC npc;
    private EditBox nameEditBox;
    private EditBox textureEditBox;
    private Checkbox immortality;


    public NpcCustomizerScreen(Component p_96550_, AbstractSTNPC npc) {
        super(p_96550_);
        this.npc = npc;
    }

    @Override
    protected void init() {
        leftPos = this.width / 2 - (winSizeX / 2);
        topPos = this.height / 2 - (winSizeY / 2);
        renderMenu();
        super.init();
    }

    @Override
    public void render(GuiGraphics poseStack, int p_96563_, int p_96564_, float p_96565_) {
        this.xMouse = p_96563_;
        this.yMouse = p_96564_;
        renderBackground(poseStack);
        renderBg(poseStack);
        super.render(poseStack, p_96563_, p_96564_, p_96565_);
    }

    @Override
    public void onClose() {
        saveChanges();
        super.onClose();
    }

    @Override
    public void removed() {
        saveChanges();
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

    private void renderBg(GuiGraphics poseStack) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        //RenderSystem.setShaderTexture(0, background);
        poseStack.blit(background,leftPos, topPos, 0, 0, winSizeX,winSizeY, (int) (256 * 1.5), (int) (256 * 1.5));
        renderLine(poseStack,this.title.getString(),7,7,false);
        int i = this.leftPos;
        int j = this.topPos;
        InventoryScreen.renderEntityInInventoryFollowsMouse(poseStack, leftPos + 261, (int) (topPos + (58 * 1.5)), 30, (float) (leftPos + 261) - xMouse, (float) (topPos + 100) - yMouse, this.npc);

    }
    private void renderLine(GuiGraphics poseStack, String text, int x, int y,boolean isCentred) {
        if(isCentred){
            poseStack.drawCenteredString(this.font, text, leftPos + x, topPos + y, 16777215);
        }else {
            poseStack.drawString(this.font, text, leftPos + x, topPos + y, 16777215);
        }
    }
    private void renderMenu() {
        nameEditBox = new EditBox(this.font, (int) (leftPos + (6 * 1.5)), (int) (topPos + (22 * 1.5)), (int) (127 * 1.5),16,Component.translatable("npc_spawner.npc_customizer_screen.name"));
        textureEditBox = new EditBox(this.font,(int) (leftPos + (6 * 1.5)), (int) ((int) (topPos + (22 * 1.5)) + 16 + (5 * 1.5)), (int) (87 * 1.5) , 16,Component.translatable("npc_spawner.npc_customizer_screen.texture"));
        Button texture = Button.builder(Component.translatable("npc_spawner.npc_customizer_screen.texture"),(p_93751_ -> {
            this.minecraft.setScreen(new TextureCustomizerScreen(Component.translatable("npc_spawner.npc_customizer_screen.texture"),this.npc,this));
        })).pos((int) (leftPos + (6 * 1.5)) + (int) (87 * 1.5) + 10, (int) ((int) (topPos + (22 * 1.5)) + 16 + (4 * 1.5))).size(50,18).build();
        Button model = Button.builder(Component.translatable("npc_spawner.npc_customizer_screen.model"),(p_93751_ -> {
            this.minecraft.setScreen(new MainME(Component.nullToEmpty(this.npc.visualData.getName()),this.npc,this));
        })).pos((int) (leftPos + (6 * 1.5)), (int) (topPos + 27 + ((16 + 7 * 1.5) * 2))).size((int) (127 * 1.5) , 16).build();
        AlwaysShowNameCheckBox alwaysShowTheName = new AlwaysShowNameCheckBox(this.npc,(int) (leftPos + (6 * 1.5)) + 20 + 70, (int) (topPos + 27 + ((16 + 7 * 1.5) * 3)),20,20,Component.translatable("npc_spawner.npc_customizer_screen.always"),npc.visualData.getAlwaysShowName(),true);
        ShowNameCheckBox showName = new ShowNameCheckBox(this.npc,alwaysShowTheName,(int) (leftPos + (6 * 1.5)), (int) (topPos + 27 + ((16 + 7 * 1.5) * 3)),20,20,Component.translatable("npc_spawner.npc_customizer_screen.show_name"),npc.visualData.getShowName(),true);
        nameEditBox.setValue(npc.visualData.getName());
        textureEditBox.setMaxLength(400);
        textureEditBox.setValue(this.npc.visualData.getTexture());
        immortality = new Checkbox((int) (leftPos + (6 * 1.5)), (int) (topPos + 27 + ((16 + 7 * 1.5) * 4)),20,20,Component.translatable("npc_spawner.npc_customizer_screen.immortality"),this.npc.behaviourData.isImmortality());
        addRenderableWidget(nameEditBox);
        addRenderableWidget(textureEditBox);
        addRenderableWidget(texture);
        addRenderableWidget(model);
        addRenderableWidget(showName);
        addRenderableWidget(alwaysShowTheName);
        addRenderableWidget(immortality);
    }
    public void saveChanges(){
        SimpleNetworkWrapper.sendToServer(new ChangeNameC2SPacket(npc.getUUID(),this.nameEditBox.getValue()));
        SimpleNetworkWrapper.sendToServer(new ChangeTextureC2SPacket(npc.getUUID(),this.textureEditBox.getValue()));
        SimpleNetworkWrapper.sendToServer(new ChangeImmortalityC2SPacket(npc.getUUID(),this.immortality.selected()));
    }

    public AbstractSTNPC getNpc(){return this.npc;}
    public void setNpc(AbstractSTNPC npc){this.npc = npc;}


}
