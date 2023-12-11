package com.dimasrpg.fabric;

import com.dimasrpg.fabriclike.DimasRPGFabricLike;
import net.fabricmc.api.ModInitializer;

public class DimasRPGFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DimasRPGFabricLike.init();
    }
}
