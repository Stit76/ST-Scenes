package com.stit76.stscenes.common.scenes.scene.act;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.stit76.stscenes.common.scenes.scene.act.acts.FollowUpAct;
import com.stit76.stscenes.common.scenes.scene.act.acts.TellAct;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Acts {
    public static final BiMap<ResourceLocation,ActType> TYPES = HashBiMap.create();
    public static final ActType FOLLOWUPACT = register("follow", FollowUpAct.FOLLOWUP_ACT_CODEC);
    public static final ActType TELLACT = register("tell", TellAct.TELL_ACT_CODEC);
    public static Codec<ActType> TYPE_CODEC = ResourceLocation.CODEC.flatXmap((p_274717_) -> {
        ActType actType = TYPES.get(p_274717_);
        return actType != null ? DataResult.success(actType) : DataResult.error(() -> {
            return "Unknown type " + p_274717_;
        });
    }, (p_274716_) -> {
        ResourceLocation resourcelocation = TYPES.inverse().get(p_274716_);
        return p_274716_ != null ? DataResult.success(resourcelocation) : DataResult.error(() -> {
            return "Unknown type " + resourcelocation;
        });
    });
    public static Codec<Act> CODEC = TYPE_CODEC.dispatch(Act::type,ActType::codec);
    public static Codec<List<Act>> LIST_CODEC = Codec.list(CODEC).xmap(ArrayList::new, ImmutableList::copyOf);

    private static ActType register(String p_262175_, Codec<? extends Act> p_261464_) {
        ActType actType = new ActType(p_261464_);
        ResourceLocation resourcelocation = new ResourceLocation(p_262175_);
        ActType actType1 = TYPES.putIfAbsent(resourcelocation, actType);
        if (actType1 != null) {
            throw new IllegalStateException("Duplicate registration " + resourcelocation);
        } else {
            return actType;
        }
    }

}
