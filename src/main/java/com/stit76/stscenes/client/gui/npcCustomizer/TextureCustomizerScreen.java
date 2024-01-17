package com.stit76.stscenes.client.gui.npccustomizer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.gui.STScreen;
import com.stit76.stscenes.client.gui.components.StringButton;
import com.stit76.stscenes.common.entity.AbstractSTNPC;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import com.stit76.stscenes.networking.packet.server.visualData.ChangeTextureC2SPacket;
import com.stit76.stscenes.utils.FileUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;

public class TextureCustomizerScreen extends STScreen {
    public static ResourceLocation background = new ResourceLocation(STScenes.MODID, "textures/gui/textures_customizer_gui.png");
    private final ResourceLocation buttons = new ResourceLocation("textures/gui/resource_packs.png");

    private String fullPath = FileUtils.getSkinDirectory().toString();
    private String jarPath = "assets/stscenes/textures/entity/player/";
    private AbstractSTNPC npc;
    private Screen backScreen;
    private int page = 1;
    private int pages = 1;
    private int lines_on_page = 4;
    short winSizeX = (int) (212 * 1.5);
    short winSizeY = (int) (166 * 1.5);
    short leftPos = 0;
    short topPos = 0;
    public TextureCustomizerScreen(Component title, AbstractSTNPC npc, Screen backScreen){
        super(title);
        this.npc = npc;
        this.backScreen = backScreen;
    }
    @Override
    protected void init() {
        leftPos = (short) (this.width / 2 - (winSizeX / 2));
        topPos = (short) (this.height / 2 - (winSizeY / 2));
        initWidgets();
        addRenderableWidget(new ImageButton((int) (leftPos + (6 * 1.5)), (int) (topPos + (140 * 1.5)),32,32,32,0,32,buttons,256,256,(p_96713_) ->{
            PreviousPage();
            this.minecraft.setScreen(this);
        }));
        addRenderableWidget(new ImageButton((int) (leftPos + (110 * 1.5)), (int) (topPos + (140 * 1.5)),32,32,0,0,32,buttons,256,256,(p_96713_) ->{
            NextPage();
            this.minecraft.setScreen(this);
        }));
        super.init();
    }

    @Override
    public void render(GuiGraphics poseStack, int p_96563_, int p_96564_, float p_96565_) {
        renderBackground(poseStack);
        renderBg(poseStack,p_96563_,p_96564_);
        poseStack.drawCenteredString(this.font,this.page + "/" + this.pages,(int) (leftPos + (65 * 1.5)), (int) (topPos + (148 * 1.5)),16777215);
        super.render(poseStack, p_96563_, p_96564_, p_96565_);
    }
    private void renderBg(GuiGraphics poseStack, int xMouse, int yMouse) {
        RenderSystem.setShaderTexture(0, background);
        poseStack.blit(background, leftPos, topPos, 0, 0, winSizeX,winSizeY, (int) (256 * 1.5), (int) (256 * 1.5));
        renderLine(poseStack,this.title.getString(),7,7,false);
        int i = this.leftPos;
        int j = this.topPos;
        InventoryScreen.renderEntityInInventoryFollowsMouse(poseStack, leftPos + 255, (int) (topPos + (100 * 1.5)), 50, (float) (leftPos + 261) - xMouse, (float) (topPos + 100) - yMouse, this.npc);
    }
    private void renderLine(GuiGraphics poseStack, String text, int x, int y,boolean isCentred) {
        if(isCentred){
            poseStack.drawCenteredString(this.font, text, leftPos + x, topPos + y, 16777215);
        }else {
            poseStack.drawString(this.font, text, leftPos + x, topPos + y, 16777215);
        }
    }
    private void initWidgets(){
        addRenderableWidget(Button.builder(Component.translatable("buttons.ok"),(p_93751_) -> {
            this.minecraft.setScreen(backScreen);
        }).pos((int) (leftPos + (142 * 1.5)), (int) (topPos + (136 * 1.5))).size((int) (60 * 1.5),20).build());
        initDirectory(this.fullPath);
        addRenderableWidget(Button.builder(Component.translatable("buttons.back"),(p_93751_) -> {
            this.minecraft.setScreen(this.backScreen);
        }).pos(leftPos + (winSizeX - 37),topPos + 5).size(30,15).build());
    }
    public void initDirectory(String rootPath){
        if(!FileUtils.isJarPath(rootPath)) {
            List<File> rss = FileUtils.getDirectoryFiles(rootPath);
            this.pages = (rss.size() - 1) / lines_on_page + 1;
            StringButton backDirectButton = new StringButton(leftPos + 3, topPos + (30 * (0 + 1)) + (5 * 0), 200, 20, Component.nullToEmpty("..."), (p_93751_ -> {
                String str = rootPath;
                int countCh = 0;
                for (int i = 0; i < str.length(); i++) {
                    char ch = str.charAt(i);
                    if (ch == '\\') {
                        countCh = i;
                    }
                }
                str = str.substring(0, countCh);
                int index = str.indexOf("entity");
                if(index >= 0){
                    this.fullPath = str;
                }
                this.minecraft.setScreen(this);
            }));
            addRenderableWidget(backDirectButton);
            int numi = 0;
            int pn = (lines_on_page * page) - lines_on_page;
            for (int i = pn; i < rss.size() & i < pn + lines_on_page; i++) {
                File file = rss.get(i);
                if (file.isFile()) {
                    int index = file.getPath().indexOf("textures");
                    if (index >= 0) {
                        String shortPath = file.getPath().substring(index);
                        String toSendShortPath = STScenes.MODID + ":" + shortPath.replace('\\', '/');
                        StringButton pathButton = new StringButton(leftPos + 3, topPos + (30 * (numi + 2)) + (5 * numi + 1), 200, 20, Component.nullToEmpty(shortPath), (p_93751_ -> {
                            SimpleNetworkWrapper.sendToServer(new ChangeTextureC2SPacket(npc.getUUID(),toSendShortPath));
                        }));
                        addRenderableWidget(pathButton);
                    }
                } else {
                    String path = file.getPath();
                    StringButton pathButton = new StringButton(leftPos + 3, topPos + (30 * (numi + 2)) + (5 * numi + 1), 200, 20, Component.nullToEmpty(path), (p_93751_ -> {
                        this.fullPath = path;
                        this.minecraft.setScreen(this);
                    }));
                    addRenderableWidget(pathButton);
                }
                numi++;
            }
        } else {
            initDirectoryInJar(this.fullPath);
        }
    }
    public void initDirectoryInJar(String rootPath){
        List<JarEntry> rss = FileUtils.getDirectoryFilesInJar(rootPath,this.jarPath);
        this.pages = (rss.size() - 1) / lines_on_page + 1;
        StringButton backDirectButton = new StringButton( leftPos + 3,topPos + (30 * (0 + 1)) + (5 * 0),200,20,Component.nullToEmpty("..."),(p_93751_ -> {
            String str = jarPath;
            HashMap<Integer,Integer> sNum = new HashMap<>();
            int charNum = 0;
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if(ch == '/') {
                    charNum++;
                    sNum.put(charNum,i);
                }
            }
            str = str.substring(0,sNum.get(charNum - 1)) + "/";
            int index = str.indexOf("entity");
            if(index >= 0){
                this.jarPath = str;
            }
            this.minecraft.setScreen(this);
        }));
        addRenderableWidget(backDirectButton);
        int numi = 0;
        int pn = (lines_on_page * page) - lines_on_page;
        for (int i = pn; i < rss.size() & i < pn + lines_on_page; i++) {
            JarEntry file = rss.get(i);
            if(file.isDirectory()){
                String path = file.getName();
                StringButton pathButton = new StringButton( leftPos + 3,topPos + (30 * (numi + 2)) + (5 * numi + 1),200,20,Component.nullToEmpty(path),(p_93751_ -> {
                    this.jarPath = path;
                    this.minecraft.setScreen(this);
                }));
                addRenderableWidget(pathButton);
            } else {
                int index = file.getName().indexOf("textures");
                if(index >= 0){
                    String shortPath = file.getName().substring(index);
                    String toSendShortPath = STScenes.MODID + ":" + shortPath.replace('\\','/');
                    StringButton pathButton = new StringButton( leftPos + 3,topPos + (30 * (numi + 2)) + (5 * numi + 1),200,20,Component.nullToEmpty(shortPath),(p_93751_ -> {
                        SimpleNetworkWrapper.sendToServer(new ChangeTextureC2SPacket(npc.getUUID(),toSendShortPath));
                    }));
                    addRenderableWidget(pathButton);
                }
            }
            numi++;
        }
    }
    public void NextPage(){
        if(page < pages){
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
    public boolean isClientUpdate() {
        return false;
    }
}
