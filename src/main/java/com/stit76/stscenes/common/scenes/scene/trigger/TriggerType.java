package com.stit76.stscenes.common.scenes.scene.trigger;

import com.mojang.serialization.Codec;

public record TriggerType<T>(Codec<? extends Trigger> codec) {
}
