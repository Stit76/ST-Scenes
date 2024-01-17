package com.stit76.stscenes.common.scenes.scene.trigger;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Triggers {
    public static final BiMap<ResourceLocation, TriggerType> TYPES = HashBiMap.create();
    public static final TriggerType STOCK_TRIGGER = register("stocktrigger", StockTrigger.STOCK_TRIGGER_CODEC);
    public static final TriggerType TOUCH_TRIGGER = register("touchtrigger", TouchTrigger.TOUCH_TRIGGER_CODEC);
    public static final TriggerType CLICK_NPC_TRIGGER = register("clicknpctrigger", ClickNpcTrigger.CLICK_NPC_TRIGGER_CODEC);
    public static Codec<TriggerType> TYPE_CODEC = ResourceLocation.CODEC.flatXmap((p_274717_) -> {
        TriggerType triggerType = TYPES.get(p_274717_);
        return triggerType != null ? DataResult.success(triggerType) : DataResult.error(() -> {
            return "Unknown type " + p_274717_;
        });
    }, (p_274716_) -> {
        ResourceLocation resourcelocation = TYPES.inverse().get(p_274716_);
        return p_274716_ != null ? DataResult.success(resourcelocation) : DataResult.error(() -> {
            return "Unknown type " + resourcelocation;
        });
    });
    public static Codec<Trigger> CODEC = TYPE_CODEC.dispatch(Trigger::iType,TriggerType::codec);
    public static Codec<List<Trigger>> LIST_CODEC = Codec.list(CODEC).xmap(ArrayList::new, ImmutableList::copyOf);
    private static TriggerType register(String p_262175_, Codec<? extends Trigger> p_261464_) {
        TriggerType triggerType = new TriggerType(p_261464_);
        ResourceLocation resourcelocation = new ResourceLocation(p_262175_);
        TriggerType triggerType1 = TYPES.putIfAbsent(resourcelocation, triggerType);
        if (triggerType1 != null) {
            throw new IllegalStateException("Duplicate registration " + resourcelocation);
        } else {
            return triggerType;
        }
    }
}
