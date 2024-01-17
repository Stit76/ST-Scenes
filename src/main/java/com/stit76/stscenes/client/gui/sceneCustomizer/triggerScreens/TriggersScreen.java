package com.stit76.stscenes.client.gui.sceneCustomizer.triggerScreens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.gui.STScreen;
import com.stit76.stscenes.client.gui.components.HintButton;
import com.stit76.stscenes.client.gui.sceneCustomizer.SceneCustomizerScreen;
import com.stit76.stscenes.common.item.SceneCustomizer;
import com.stit76.stscenes.common.scenes.scene.Scene;
import com.stit76.stscenes.common.scenes.scene.Scenes;
import com.stit76.stscenes.common.scenes.scene.trigger.StockTrigger;
import com.stit76.stscenes.common.scenes.scene.trigger.TouchTrigger;
import com.stit76.stscenes.common.scenes.scene.trigger.Trigger;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.synchronization.SetSceneInScenesDataC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class TriggersScreen extends STScreen {
    public static ResourceLocation background = new ResourceLocation(STScenes.MODID, "textures/gui/act_customizer_gui.png");
    private final ResourceLocation buttons = new ResourceLocation("textures/gui/resource_packs.png");
    private net.minecraft.client.gui.screens.Screen backScreen;
    private Scene scene;
    private SceneCustomizerScreen screen;
    private SceneCustomizer sceneCustomizer;
    int winSizeX = (int) (119 * 1.5);
    int winSizeY = (int) (166 * 1.5);
    int leftPos = 0;
    int topPos = 0;
    private int page = 1;
    private int maxPage;
    private int line_on_page = 5;


    public TriggersScreen(Component p_96550_, SceneCustomizerScreen screen, Scene scene, SceneCustomizer sceneCustomizer, net.minecraft.client.gui.screens.Screen backScreen) {
        super(p_96550_);
        this.screen = screen;
        this.scene = scene;
        this.sceneCustomizer = sceneCustomizer;
        this.backScreen = backScreen;
    }

    @Override
    protected void init() {
        leftPos = this.width / 2 - (winSizeX / 2);
        topPos = this.height / 2 - (winSizeY / 2);
        maxPage = (scene.triggers.size() - 1) / line_on_page + 1;
        initToolsMenu();
        initTriggerMenu(this.line_on_page);
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
        //RenderSystem.setShaderTexture(0, background);
        poseStack.blit(background, leftPos, topPos, 0, 0, winSizeX,winSizeY, (int) (256 * 1.5), (int) (256 * 1.5));
        renderLine(poseStack,this.title.getString(),7,7,false);
        poseStack.drawCenteredString(Minecraft.getInstance().font, this.page + "/" + this.maxPage,leftPos + (winSizeX / 2),(int) (topPos + (148 * 1.5)),16777215);
    }
    private void initToolsMenu() {
        addRenderableWidget(Button.builder(Component.translatable("buttons.back"),(p_93751_) -> {
            this.minecraft.setScreen(this.backScreen);
        }).pos(leftPos + (winSizeX - 35),topPos + 5).size(30,15).build());
        addRenderableWidget(Button.builder(Component.translatable("buttons.add"),(p_93751_) -> {
            scene.triggers.add(new StockTrigger(Component.translatable("scene_customizer.triggers_screen.stock_trigger.name").getString(),false));
            SimpleNetworkWrapper.sendToServer(new SetSceneInScenesDataC2SPacket(screen.num,screen.scene, Scenes.sceneList));
            this.minecraft.setScreen(this);
        }).pos(leftPos + (winSizeX - 65),topPos + 5).size(30,15).build());
        addRenderableWidget(new ImageButton((int) (leftPos + (6 * 1.5)), (int) (topPos + (140 * 1.5)),32,32,32,0,32,buttons,256,256,(p_96713_) ->{
            PreviousPage();
            this.minecraft.setScreen(this);
        }));
        addRenderableWidget(new ImageButton((int) (leftPos + winSizeX - (26 * 1.5)), (int) (topPos + (140 * 1.5)),32,32,0,0,32,buttons,256,256,(p_96713_) ->{
            NextPage();
            this.minecraft.setScreen(this);
        }));
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
        Trigger trigger = scene.triggers.get(num);
        HintButton trButton = new HintButton(x,y,sizeX,sizeY,Component.nullToEmpty(trigger.name),(p_93751_) -> {
            this.minecraft.setScreen(new TriggerCustomizerScreen(Component.translatable("scene_customizer.triggers_screen.change_trigger_type_title"),screen,scene,num,sceneCustomizer,this));
        });
        trButton.hintText = new String[]{
                Component.translatable("scene_customizer.triggers_screen.hint.name").getString() + trigger.name,
                Component.translatable("scene_customizer.triggers_screen.hint.type").getString() + trigger.type(),
                Component.translatable("scene_customizer.triggers_screen.hint.status").getString() + (trigger.isCanUse() ? Component.translatable("status.on").getString() : Component.translatable("status.off").getString())
        };
        addRenderableWidget(trButton);
    }
    public void NextPage(){
        if(line_on_page * page < scene.triggers.size()){
            page++;
        }
    }
    public void PreviousPage(){
        if(page != 1){
            page--;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
