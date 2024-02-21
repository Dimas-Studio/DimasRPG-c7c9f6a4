package com.dimasrpg.forge;
//@generated
import dev.architectury.platform.forge.EventBuses;
import com.dimasrpg.DimasRPG;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DimasRPG.MOD_ID)
public class DimasRPGForge {
    public DimasRPGForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(DimasRPG.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        DimasRPG.init();
    }
}
