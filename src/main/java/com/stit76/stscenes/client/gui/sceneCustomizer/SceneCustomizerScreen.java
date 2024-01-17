package com.stit76.stscenes.client.gui.sceneCustomizer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.gui.STScreen;
import com.stit76.stscenes.client.gui.sceneCustomizer.argsScreens.cacts.CArgCustomizerScreen;
import com.stit76.stscenes.client.gui.sceneCustomizer.argsScreens.sacts.SArgCustomizerScreen;
import com.stit76.stscenes.client.gui.sceneCustomizer.triggerScreens.TriggersScreen;
import com.stit76.stscenes.common.item.SceneCustomizer;
import com.stit76.stscenes.common.scenes.scene.Scene;
import com.stit76.stscenes.common.scenes.scene.Scenes;
import com.stit76.stscenes.common.scenes.scene.act.compoundact.CAct;
import com.stit76.stscenes.common.scenes.scene.act.compoundact.cacts.TellAct;
import com.stit76.stscenes.common.scenes.scene.act.simpleact.SAct;
import com.stit76.stscenes.common.scenes.scene.act.simpleact.sacts.GoToActionSAct;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.synchronization.SetSceneActiveC2SPacket;
import com.stit76.stscenes.networking.packet.synchronization.SetSceneInScenesDataC2SPacket;
import com.stit76.stscenes.networking.packet.synchronization.SetSceneLastPlayerC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SceneCustomizerScreen extends STScreen {
    public static ResourceLocation background = new ResourceLocation(STScenes.MODID, "textures/gui/scenes_customizer_gui.png");
    private final ResourceLocation buttons = new ResourceLocation("textures/gui/resource_packs.png");
    public net.minecraft.client.gui.screens.Screen backScreen;
    public SceneCustomizer sceneCustomizer;
    public Scene scene;
    private EditBox name;
    int winSizeX = (int) (212 * 1.5);
    int winSizeY = (int) (166 * 1.5);
    int leftPos = 0;
    int topPos = 0;
    private int page = 1;
    private int line_on_page = 7;
    public short num;
    private int maxPage;


    public SceneCustomizerScreen(short num, Scene scene, SceneCustomizer sceneCustomizer, net.minecraft.client.gui.screens.Screen backScreen) {
        super(Component.nullToEmpty(scene.getName()));
        this.scene = scene;
        this.num = num;
        this.sceneCustomizer = sceneCustomizer;
        this.page = (scene.getActs().size() - 1) / line_on_page + 1;
        this.backScreen = backScreen;
    }

    @Override
    protected void init() {
        this.scene = Scenes.sceneList.get(num);
        leftPos = this.width / 2 - (winSizeX / 2);
        topPos = this.height / 2 - (winSizeY / 2);
        maxPage = (scene.getActs().size() - 1) / line_on_page + 1;
        initNameBox(this.scene);
        initToolsMenu();
        initActMenu(line_on_page);
        super.init();
    }

    @Override
    public void render(GuiGraphics p_96562_, int p_96563_, int p_96564_, float p_96565_) {
        renderBackground(p_96562_);
        renderBg(p_96562_);
        //RenderSystem.setShaderTexture(0, background);
        int line_on_page = this.line_on_page;
        int numi = -1;
        int pn = (line_on_page * page) - line_on_page;
        for (int i = pn; i < this.scene.getActs().size() & i < pn + line_on_page; i++) {
            numi++;
                if(scene.getActiveAct() == i + 1){
                    if(!scene.isActive()){
                        p_96562_.blit(background,leftPos + 3,topPos + ((24 * (numi + 1)) + (3 * numi)) + 9, 223, 24, 9,11, 256, 256);
                    } else {
                        p_96562_.blit(background,leftPos + 3,topPos + ((24 * (numi + 1)) + (3 * numi)) + 9, 223, 12, 9,11, 256, 256);
                    }
                } else {
                    p_96562_.blit(background,leftPos + 3,topPos + ((24 * (numi + 1)) + (3 * numi)) + 9, 223, 0, 9,11, 256, 256);
                }
        }
        if(scene.isActive()){
            p_96562_.blit(background,leftPos + (winSizeX - 37 - 60) - 12,topPos + 7, 233, 11, 10,10, 256, 256);
        } else {
            p_96562_.blit(background,leftPos + (winSizeX - 37 - 60) - 12,topPos + 7, 233, 0, 10,10, 256, 256);
        }
        p_96562_.drawCenteredString(this.font, page + "/" + maxPage, (int)  (leftPos + (59 * 1.5)),(int) (topPos + (140 * 1.5)) + 12, 16777215);
        p_96562_.drawCenteredString(this.font, "Add:", leftPos + 189 + 59,(int) (topPos + (21 * 1.5)), 16777215);
        super.render(p_96562_, p_96563_, p_96564_, p_96565_);
    }
    private void renderBg(GuiGraphics poseStack) {
        //RenderSystem.setShaderTexture(0, background);
        poseStack.blit(background,leftPos, topPos, 0, 0, winSizeX,winSizeY, (int) (256 * 1.5), (int) (256 * 1.5));
    }
    private void initNameBox(Scene scene) {
        this.name = new EditBox(this.font,leftPos + 7, topPos + 7,100,20,Component.nullToEmpty("name"));
        this.name.setBordered(false);
        this.name.setValue(scene.getName());
        addRenderableWidget(this.name);
    }
    private void initToolsMenu() {
        addRenderableWidget(Button.builder(Component.translatable("buttons.back"),(p_93751_) -> {
            this.minecraft.setScreen(this.backScreen);
        }).pos(leftPos + (winSizeX - 37),topPos + 5).size(30,15).build());
        addRenderableWidget(Button.builder(Component.nullToEmpty(scene.isActive()
                ? Component.translatable("scene_customizer.scene_customizer_screen.stop").getString()
                : Component.translatable("scene_customizer.scene_customizer_screen.start").getString()),
                (p_93751_) -> {
            if(name != null){this.scene.setName(this.name.getValue());}
            SimpleNetworkWrapper.sendToServer(new SetSceneLastPlayerC2SPacket(this.num));
            SimpleNetworkWrapper.sendToServer(new SetSceneActiveC2SPacket(!scene.isActive(),this.num,Scenes.sceneList));
        }).pos(leftPos + (winSizeX - 37 - 60),topPos + 5).size(60,15).build());
        addRenderableWidget(Button.builder(Component.translatable("scene_customizer.scene_customizer_screen.c_act"),(p_93751_) -> {
            if(name != null){this.scene.setName(this.name.getValue());}
            scene.getActs().add(new TellAct());
            //if(scene.getActs().size() > page * line_on_page){NextPage();}
            SimpleNetworkWrapper.sendToServer(new SetSceneInScenesDataC2SPacket(this.num,this.scene, Scenes.sceneList));
        }).pos((int) (leftPos + (126 * 1.5)), (int) (topPos + (31 * 1.5))).size(118,20).build());
        addRenderableWidget(Button.builder(Component.translatable("scene_customizer.scene_customizer_screen.s_act"),(p_93751_) -> {
            if(name != null){this.scene.setName(this.name.getValue());}
            scene.getActs().add(new GoToActionSAct(0));
            SimpleNetworkWrapper.sendToServer(new SetSceneInScenesDataC2SPacket(this.num,this.scene, Scenes.sceneList));
        }).pos((int) (leftPos + (126 * 1.5)), (int) (topPos + (56 * 1.5))).size(118,20).build());
        addRenderableWidget(Button.builder(Component.translatable("scene_customizer.scene_customizer_screen.settings_button"),(p_93751_) -> {
            this.minecraft.setScreen(new SceneSettingsScreen(Component.translatable("scene_customizer.scene_settings.title"),this.scene,this.num,this));
        }).pos(leftPos + winSizeY - (118 / 2),topPos + winSizeY - 25).size(118,20).build());
        addRenderableWidget(Button.builder(Component.translatable("scene_customizer.scene_customizer_screen.trigger_button"),(p_93751_) -> {
            this.getMinecraft().setScreen(new TriggersScreen(Component.translatable("scene_customizer.trigger_screen.title"),this,this.scene,sceneCustomizer,this));
        }).pos((int) (leftPos + (126 * 1.5)), topPos + winSizeY - 50).size((int) (79 * 1.5),20).build());
        addRenderableWidget(new ImageButton((int) (leftPos + (6 * 1.5)), (int) (topPos + (140 * 1.5)),32,32,32,0,32,buttons,256,256,(p_96713_) ->{
            if(name != null){this.scene.setName(this.name.getValue());}
            PreviousPage();
            this.minecraft.setScreen(this);
        }));
        addRenderableWidget(new ImageButton((int) (leftPos + (95 * 1.5)), (int) (topPos + (140 * 1.5)),32,32,0,0,32,buttons,256,256,(p_96713_) ->{
            if(name != null){this.scene.setName(this.name.getValue());}
            NextPage();
            this.minecraft.setScreen(this);
        }));
    }

    private void initActMenu(int line_on_page) {
        int numi = -1;
        int pn = (line_on_page * page) - line_on_page;
        for (int i = pn; i < this.scene.getActs().size() & i < pn + line_on_page; i++) {
            numi++;
            if(scene.getActs().get(i) instanceof CAct){
                initCompoundAct(scene,i,leftPos + 12,topPos + ((24 * (numi + 1)) + (3 *numi)),160,20);
            } else if(scene.getActs().get(i) instanceof SAct){
                initSimpleAct(scene,i,leftPos + 12,topPos + ((24 * (numi + 1)) + (3 *numi)),160,20);
            }
        }
    }
    private void initCompoundAct(Scene scene, int line, int x, int y, int sizeX, int sizeY) {
        int sizeTX = sizeX / 3;
        int sizeTY = sizeY;
        CAct act = ((CAct)scene.getActs().get(line));
        addRenderableWidget(new ImageButton(x - 9,y,9,9,213,0,10,background,256,256,(p_93751_) -> {
            if(!scene.isActive()){
                if(name != null){this.scene.setName(this.name.getValue());}
                scene.getActs().remove(line);
                //if(scene.getActs().size() <= (page * line_on_page) - line_on_page){
                //    PreviousPage();
                //}
                SimpleNetworkWrapper.sendToServer(new SetSceneInScenesDataC2SPacket(this.num,this.scene, Scenes.sceneList));
            }
        }));
            addRenderableWidget(Button.builder(Component.nullToEmpty(act.object),(p_93751_) -> {
                this.sceneCustomizer.setObjectMode(true);
                this.sceneCustomizer.objectAct = act;
                this.sceneCustomizer.setCalledScreen(this);
                this.minecraft.setScreen(null);
            }).pos(x,y).size(sizeTX,sizeTY).build());
            addRenderableWidget(Button.builder(Component.nullToEmpty(act.act),(p_93751_) -> {
                this.minecraft.setScreen(new CActCustomizerScreen(num,scene,line,Component.translatable("scene_customizer.c_act_customizer_screen.title"),this));
            }).pos(x + sizeTX,y).size(sizeTX,sizeTY).build());
            addRenderableWidget(Button.builder(Component.nullToEmpty(act.arg),(p_93751_) -> {
                this.minecraft.setScreen(new CArgCustomizerScreen(act,Component.translatable("scene_customizer.c_arg_customizer_screen.title"),this));
            }).pos(x + sizeTX*2,y).size(sizeTX,sizeTY).build());
    }
    private void initSimpleAct(Scene scene, int line, int x, int y, int sizeX, int sizeY) {
        int sizeTX = sizeX / 2;
        int sizeTY = sizeY;
        SAct act = ((SAct)scene.getActs().get(line));
        addRenderableWidget(new ImageButton(x - 9,y,9,9,213,0,10,background,256,256,(p_93751_) -> {
            if(!scene.isActive()){
                if(name != null){this.scene.setName(this.name.getValue());}
                scene.getActs().remove(line);
                //if(scene.getActs().size() <= (page * line_on_page) - line_on_page){
                //    PreviousPage();
                //}
                SimpleNetworkWrapper.sendToServer(new SetSceneInScenesDataC2SPacket(this.num,this.scene, Scenes.sceneList));
            }
        }));
        addRenderableWidget(Button.builder(Component.nullToEmpty(act.actName()),(p_93751_) -> {
            this.minecraft.setScreen(new SActCustomizerScreen(num,scene,line,Component.translatable("scene_customizer.s_act_customizer_screen.title"),this));
        }).pos(x,y).size(sizeTX,sizeTY).build());
        addRenderableWidget(Button.builder(Component.nullToEmpty(act.argName()),(p_93751_) -> {
            this.minecraft.setScreen(new SArgCustomizerScreen(act,Component.translatable("scene_customizer.s_arg_customizer_screen.title"),this));
        }).pos(x + sizeTX,y).size(sizeTX,sizeTY).build());
    }

    public void NextPage(){
        if(line_on_page * page < scene.getActs().size()){
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

    @Override
    public void removed() {
        if(name != null){this.scene.setName(this.name.getValue());}
        SimpleNetworkWrapper.sendToServer(new SetSceneInScenesDataC2SPacket(this.num,this.scene, Scenes.sceneList));
        super.removed();
    }
}
