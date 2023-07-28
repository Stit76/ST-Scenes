package com.stit76.stscenes.common.scenes.scene.trigger;

public abstract class Trigger {
    public String name = "New trigger";
    private boolean сanUse = true;
    public abstract TriggerType<?> type();
    public boolean isTrue(){
        if(update() & сanUse){
            return true;
        }
        return false;
    }

    protected boolean update() {
        return false;
    }
    public boolean isCanUse(){
        return this.сanUse;
    }
    protected void setCanUse(boolean bool){
        this.сanUse = bool;
    }
    public void deactivate(){setCanUse(false);}
    public void activate(){setCanUse(true);}
}
