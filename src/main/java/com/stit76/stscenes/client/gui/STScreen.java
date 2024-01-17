package com.stit76.stscenes.client.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class STScreen extends Screen {
    protected STScreen(Component p_96550_) {
        super(p_96550_);
    }
    public boolean isClientUpdate(){
        return true;
    }
}
