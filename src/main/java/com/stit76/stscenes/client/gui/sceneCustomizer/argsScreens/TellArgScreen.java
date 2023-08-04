package com.stit76.stscenes.client.gui.sceneCustomizer.argsScreens;

import com.stit76.stscenes.client.gui.components.TextEditBox;
import com.stit76.stscenes.client.gui.sceneCustomizer.SceneCustomizerScreen;
import com.stit76.stscenes.common.scenes.scene.act.Act;
import com.stit76.stscenes.common.scenes.scene.act.acts.TellAct;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TellArgScreen extends ArgScreen{
    private TextEditBox editBox_1;
    private TextEditBox editBox_2;

    protected TellArgScreen(Component p_96550_, SceneCustomizerScreen back_screen, Act act) {
        super(p_96550_, back_screen, act);
    }

    @Override
    protected void init() {
        super.init();
        editBox_1 = new TextEditBox(Minecraft.getInstance().font,leftPos + 35,this.topPos + 60,100,20,Component.nullToEmpty("Text"));
        editBox_2 = new TextEditBox(Minecraft.getInstance().font,leftPos + 35,this.topPos + 60 + 20 + 25,100,20,Component.nullToEmpty("Who"));
        editBox_1.setTitle("What to say:");
        editBox_1.setValue(((TellAct)act).text);
        editBox_2.setTitle("To whom to say:");
        editBox_2.setValue(((TellAct)act).player_name);
        addRenderableWidget(editBox_1);
        addRenderableWidget(editBox_2);
    }

    @Override
    protected void OnPressOk() {
        if(this.act instanceof TellAct){
            act.arg = this.editBox_1.getValue();
            ((TellAct) this.act).text = this.editBox_1.getValue();
            ((TellAct) this.act).player_name = this.editBox_2.getValue();
        }
        super.OnPressOk();
    }
}
