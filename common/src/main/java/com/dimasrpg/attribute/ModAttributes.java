package com.dimasrpg.attribute;

import com.dimasrpg.DimasRPG;
import com.dimasrpg.attribute.custom.MeleeDamageAttribute;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(DimasRPG.MOD_ID, Registries.ATTRIBUTE);


    public static final RegistrySupplier<Attribute> MELEE_DAMAGE = ATTRIBUTES.register("config_checker", () ->
           new MeleeDamageAttribute("attribute.name.melee_damage", 1.0));
    public static void init() {
        ATTRIBUTES.register();
    }
}
