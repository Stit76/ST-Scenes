package com.stit76.stscenes.client.gui.sceneCustomizer.argsScreens;

import com.stit76.stscenes.client.gui.components.TextEditBox;
import com.stit76.stscenes.client.gui.sceneCustomizer.SceneCustomizerScreen;
import com.stit76.stscenes.common.scenes.scene.act.Act;
import com.stit76.stscenes.common.scenes.scene.act.acts.FollowUpAct;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class FollowUpArgScreen extends ArgScreen{
    private TextEditBox editBox_1;

    protected FollowUpArgScreen(Component p_96550_, SceneCustomizerScreen back_screen, Act act) {
        super(p_96550_, back_screen, act);
    }

    @Override
    protected void init() {
        super.init();
        editBox_1 = new TextEditBox(Minecraft.getInstance().font, this.leftPos + 35,this.topPos + 60,100,20,Component.nullToEmpty("Text"));
        editBox_1.setTitle("Who to follow (Player's nickname)");
        editBox_1.setValue(((FollowUpAct)act).getPlayerName());
        addRenderableWidget(editBox_1);
    }

    @Override
    protected void OnPressOk() {
        act.arg = this.editBox_1.getValue();
        ((FollowUpAct) act).setPlayerName(this.editBox_1.getValue());
        super.OnPressOk();
    }
}
