package com.stit76.stscenes.common.scenes.scene.act;

import com.mojang.serialization.Codec;

public record ActType<T>(Codec<? extends Act> codec) {
}
