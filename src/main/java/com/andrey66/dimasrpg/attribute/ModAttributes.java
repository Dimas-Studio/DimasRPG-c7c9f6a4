package com.andrey66.dimasrpg.attribute;

import com.andrey66.dimasrpg.DimasRPG;
import com.andrey66.dimasrpg.attribute.custom.MagicResAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, DimasRPG.MOD_ID);

    public static final RegistryObject<Attribute> MAGIC_RES = ATTRIBUTES.register("magic_res",
            () -> new MagicResAttribute("magic_res", 0.0));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
