package com.stit76.stscenes.client.gui.scenesBrowser;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.gui.STScreen;
import com.stit76.stscenes.client.gui.sceneCustomizer.SceneCustomizerScreen;
import com.stit76.stscenes.client.gui.components.SceneButton;
import com.stit76.stscenes.common.item.SceneCustomizer;
import com.stit76.stscenes.common.scenes.scene.Scene;
import com.stit76.stscenes.common.scenes.scene.Scenes;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.synchronization.AddSceneToScenesDataC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class ScenesBrowser extends STScreen {
    public static final ResourceLocation background = new ResourceLocation(STScenes.MODID, "textures/gui/scenes_browser_gui.png");
    private static final ResourceLocation buttons = new ResourceLocation("textures/gui/resource_packs.png");

    public SceneCustomizer sceneCustomizer;
    public Player player;
    private List<Scene> sceneList;
    short winSizeX = (int) (212 * 1.5);
    short winSizeY = (int) (166 * 1.5);
    short leftPos = 0;
    short topPos = 0;
    byte line_on_page = 14;
    short page = 1;
    private int maxPage;
    public ScenesBrowser(Component p_96550_, SceneCustomizer sceneCustomizer,Player player) {
        super(p_96550_);
        this.player = player;
        this.sceneCustomizer = sceneCustomizer;
    }

    @Override
    protected void init() {
        sceneList = Scenes.sceneList;
        leftPos = (short) (this.width / 2 - (winSizeX / 2));
        topPos = (short) (this.height / 2 - (winSizeY / 2));
        maxPage = (sceneList.size() - 1) / line_on_page + 1;
        initTools();
        initScenes();
        super.init();
    }

    @Override
    public void render(GuiGraphics poseStack, int p_96563_, int p_96564_, float p_96565_) {
        renderBackground(poseStack);
        renderBg(poseStack);
        poseStack.drawCenteredString(this.font, page + "/" + maxPage, leftPos + (winSizeX - 20),(int) (topPos + (140 * 1.5)) + 12, 16777215);
        super.render(poseStack, p_96563_, p_96564_, p_96565_);
    }
    private void renderBg(GuiGraphics poseStack) {
        //RenderSystem.setShaderTexture(0, background);
        poseStack.blit(background, leftPos, topPos, 0, 0, winSizeX,winSizeY, (int) (256 * 1.5), (int) (256 * 1.5));
        poseStack.drawString(Minecraft.getInstance().font,this.title.getString(),leftPos + 7,topPos + 7,16777215);
    }
    private void initTools(){
        //198 : 4
        addRenderableWidget(new ImageButton((int) (leftPos + (198 * 1.5)), (int) (topPos + (4 * 1.5)), (int) (9 * 1.5),
                (int) (9 * 1.5), (int) (223 * 1.5),0, (int) (9 * 1.5),background, (int) (256 * 1.5), (int) (256 * 1.5),
                (p_93751_) -> {
                    Scene scene = new Scene("Test");
                    scene.setName("New scene");
                    scene.updateDate();
                    //if(sceneList.size() > page * line_on_page){NextPage();}
                    SimpleNetworkWrapper.sendToServer(new AddSceneToScenesDataC2SPacket(scene,Scenes.sceneList));
                }));
        addRenderableWidget(new ImageButton((int) (leftPos + (8 * 1.5)), (int) (topPos + (140 * 1.5)),32,32,32,0,32,buttons,256,256,(p_96713_) ->{
            PreviousPage();
            this.minecraft.setScreen(this);
        }));
        addRenderableWidget(new ImageButton((int) (leftPos + (8 * 1.5)) + 32, (int) (topPos + (140 * 1.5)),32,32,0,0,32,buttons,256,256,(p_96713_) ->{
            NextPage();
            this.minecraft.setScreen(this);
        }));
    }
    private void initScenes(){
        int numY = 0;
        int numX = 0;
        int pn = (line_on_page * page) - line_on_page;
        for (int i = pn; i < sceneList.size() & i < pn + line_on_page; i++) {
            if(numY >= line_on_page / 2){
                numX++;
                numY = 1;
            } else {
                numY++;
            }
            initScene((short) i, sceneList.get(i),leftPos + ((24 * (numX + 1)) + (150 *numX)),topPos + ((20 * (numY + 1)) + (3 *numY)),100,21);
        }

    }
    private void initScene(short num,Scene scene, int x, int y, int sizeX, int sizeY){
        addRenderableWidget(new SceneButton(x,y,sizeX,sizeY,Component.nullToEmpty(scene.getName()),(p_93751_) -> {
            this.minecraft.setScreen(new SceneCustomizerScreen(num,scene,sceneCustomizer,this));
        },scene));
    }
    public void NextPage(){
        if(page < maxPage){
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
