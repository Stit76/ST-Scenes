package com.stit76.stscenes.client.gui.sceneCustomizer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.gui.sceneCustomizer.argsScreens.ArgCustomizerScreen;
import com.stit76.stscenes.client.gui.sceneCustomizer.triggerScreens.TriggersScreen;
import com.stit76.stscenes.common.item.SceneCustomizer;
import com.stit76.stscenes.common.scenes.scene.Scene;
import com.stit76.stscenes.common.scenes.scene.act.Act;
import com.stit76.stscenes.common.scenes.scene.act.acts.TellAct;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SceneCustomizerScreen extends Screen {
    public static ResourceLocation background = new ResourceLocation(STScenes.MODID, "textures/gui/scenes_customizer_gui.png");
    private final ResourceLocation buttons = new ResourceLocation("textures/gui/resource_packs.png");
    private SceneCustomizer sceneCustomizer;
    public Scene scene;
    private EditBox name;
    int winSizeX = (int) (212 * 1.5);
    int winSizeY = (int) (166 * 1.5);
    int leftPos = 0;
    int topPos = 0;
    private int page = 1;
    private int line_on_page = 7;
    private short num;
    private int maxPage;


    public SceneCustomizerScreen(short num,Scene scene,SceneCustomizer sceneCustomizer) {
        super(Component.nullToEmpty(scene.getName()));
        this.scene = scene;
        this.num = num;
        this.sceneCustomizer = sceneCustomizer;
        this.page = (scene.getActs().size() - 1) / line_on_page + 1;
    }

    @Override
    protected void init() {
        leftPos = this.width / 2 - (winSizeX / 2);
        topPos = this.height / 2 - (winSizeY / 2);
        maxPage = (scene.getActs().size() - 1) / line_on_page + 1;
        initNameBox(this.scene);
        initToolsMenu();
        initActMenu(line_on_page);
        super.init();
    }

    @Override
    public void render(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
        renderBackground(p_96562_);
        renderBg(p_96562_);
        RenderSystem.setShaderTexture(0, background);
        int line_on_page = this.line_on_page;
        int numi = -1;
        int pn = (line_on_page * page) - line_on_page;
        for (int i = pn; i < this.scene.getActs().size() & i < pn + line_on_page; i++) {
            numi++;
                if(scene.getActiveAct() == i + 1){
                    if(!scene.isActive()){
                        blit(p_96562_,leftPos + 3,topPos + ((24 * (numi + 1)) + (3 * numi)) + 9, 223, 24, 9,11, 256, 256);
                    } else {
                        blit(p_96562_,leftPos + 3,topPos + ((24 * (numi + 1)) + (3 * numi)) + 9, 223, 12, 9,11, 256, 256);
                    }
                } else {
                    blit(p_96562_,leftPos + 3,topPos + ((24 * (numi + 1)) + (3 * numi)) + 9, 223, 0, 9,11, 256, 256);
                }
        }
        if(scene.isActive()){
            blit(p_96562_,leftPos + (winSizeX - 67) - 12,topPos + 7, 233, 11, 10,10, 256, 256);
        } else {
            blit(p_96562_,leftPos + (winSizeX - 67) - 12,topPos + 7, 233, 0, 10,10, 256, 256);
        }
        drawCenteredString(p_96562_, this.font, page + "/" + maxPage, (int)  (leftPos + (59 * 1.5)),(int) (topPos + (140 * 1.5)) + 12, 16777215);
        super.render(p_96562_, p_96563_, p_96564_, p_96565_);
    }
    private void renderBg(PoseStack poseStack) {
        RenderSystem.setShaderTexture(0, background);
        blit(poseStack, leftPos, topPos, 0, 0, winSizeX,winSizeY, (int) (256 * 1.5), (int) (256 * 1.5));
    }
    private void initNameBox(Scene scene) {
        this.name = new EditBox(this.font,leftPos + 7, topPos + 7,100,20,Component.nullToEmpty("name"));
        this.name.setBordered(false);
        this.name.setValue(scene.getName());
        addRenderableWidget(this.name);
    }
    private void initToolsMenu() {
        addRenderableWidget(Button.builder(Component.nullToEmpty("Start"),(p_93751_) -> {
            scene.start();
        }).pos(leftPos + (winSizeX - 67),topPos + 5).size(60,15).build());
        addRenderableWidget(Button.builder(Component.nullToEmpty("Add Action"),(p_93751_) -> {
            scene.getActs().add(new TellAct());
            if(scene.getActs().size() > page * line_on_page){NextPage();}
            this.minecraft.setScreen(this);
        }).pos((int) (leftPos + (126 * 1.5)), (int) (topPos + (21 * 1.5))).size((int) (79 * 1.5),20).build());
        addRenderableWidget(Button.builder(Component.nullToEmpty("Triggers"),(p_93751_) -> {
            this.getMinecraft().setScreen(new TriggersScreen(Component.nullToEmpty("Triggers"),this.scene,sceneCustomizer));
        }).pos((int) (leftPos + (126 * 1.5)), (int) (topPos + (50 * 1.5))).size((int) (79 * 1.5),20).build());
        addRenderableWidget(new ImageButton((int) (leftPos + (6 * 1.5)), (int) (topPos + (140 * 1.5)),32,32,32,0,32,buttons,256,256,(p_96713_) ->{
            PreviousPage();
            this.minecraft.setScreen(this);
        }));
        addRenderableWidget(new ImageButton((int) (leftPos + (95 * 1.5)), (int) (topPos + (140 * 1.5)),32,32,0,0,32,buttons,256,256,(p_96713_) ->{
            NextPage();
            this.minecraft.setScreen(this);
        }));
    }

    private void initActMenu(int line_on_page) {
        int numi = -1;
        int pn = (line_on_page * page) - line_on_page;
        for (int i = pn; i < this.scene.getActs().size() & i < pn + line_on_page; i++) {
            numi++;
            initAct(scene,i,leftPos + 12,topPos + ((24 * (numi + 1)) + (3 *numi)),160,20);
        }
    }
    private void initAct(Scene scene, int line, int x, int y, int sizeX, int sizeY) {
        int sizeTX = sizeX / 3;
        int sizeTY = sizeY;
        Act act = scene.getActs().get(line);
            addRenderableWidget(new ImageButton(x - 9,y,9,9,213,0,10,background,256,256,(p_93751_) -> {
                if(!scene.isActive()){
                    scene.getActs().remove(line);
                    if(scene.getActs().size() <= (page * line_on_page) - line_on_page){
                        PreviousPage();
                    }
                    this.minecraft.setScreen(this);
                }
            }));
            addRenderableWidget(Button.builder(Component.nullToEmpty(act.object),(p_93751_) -> {
                this.sceneCustomizer.setObjectMode(true);
                this.sceneCustomizer.objectAct = act;
                this.sceneCustomizer.setCalledScreen(this);
                this.minecraft.setScreen(null);
            }).pos(x,y).size(sizeTX,sizeTY).build());
            addRenderableWidget(Button.builder(Component.nullToEmpty(act.act),(p_93751_) -> {
                this.minecraft.setScreen(new ActCustomizerScreen(scene,line,Component.nullToEmpty("Choose an action"),this));
            }).pos(x + sizeTX,y).size(sizeTX,sizeTY).build());
            addRenderableWidget(Button.builder(Component.nullToEmpty(act.arg),(p_93751_) -> {
                this.minecraft.setScreen(new ArgCustomizerScreen(act,Component.nullToEmpty("Choose an arguments"),this));
            }).pos(x + sizeTX*2,y).size(sizeTX,sizeTY).build());
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
        super.removed();
    }
}
