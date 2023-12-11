package com.dimasrpg.quilt;

import com.dimasrpg.fabriclike.DimasRPGFabricLike;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class DimasRPGQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        DimasRPGFabricLike.init();
    }
}
