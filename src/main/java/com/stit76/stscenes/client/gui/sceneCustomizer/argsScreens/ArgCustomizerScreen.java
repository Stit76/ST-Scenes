package com.stit76.stscenes.client.gui.sceneCustomizer.argsScreens;

import com.stit76.stscenes.client.gui.STScreen;
import com.stit76.stscenes.client.gui.sceneCustomizer.SceneCustomizerScreen;
import com.stit76.stscenes.common.scenes.scene.act.Act;
import com.stit76.stscenes.common.scenes.scene.act.acts.FollowUpAct;
import com.stit76.stscenes.common.scenes.scene.act.acts.TellAct;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ArgCustomizerScreen extends STScreen {
    private Component name;
    private SceneCustomizerScreen back_screen;
    private Act act;

    public ArgCustomizerScreen(Act act, Component name,SceneCustomizerScreen back_screen) {
        super(name);
        this.act = act;
        this.back_screen = back_screen;
        this.name = name;
    }

    protected void init() {
        if(act instanceof TellAct){
            this.minecraft.setScreen(new TellArgScreen(this.name,this.back_screen,this.act));
        } else if(act instanceof FollowUpAct){
            this.minecraft.setScreen(new FollowUpArgScreen(this.name,this.back_screen,this.act));
        } else {
            this.minecraft.setScreen(this.back_screen);
        }
    }
}
