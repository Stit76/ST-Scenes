package com.stit76.stscenes.common.scenes.scene.trigger;

import com.mojang.serialization.Codec;
import com.stit76.stscenes.common.scenes.scene.act.Act;

public record TriggerType<T>(Codec<? extends Trigger> codec) {
}
