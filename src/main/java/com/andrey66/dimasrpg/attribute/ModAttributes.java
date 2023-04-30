package com.andrey66.dimasrpg.attribute;

import com.andrey66.dimasrpg.DimasRPG;
import com.andrey66.dimasrpg.attribute.custom.DamageTypeAttribute;
import com.andrey66.dimasrpg.attribute.custom.MagicArmorAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// Класс для регистрации новых аттрибутов с особыми свойствами
public class ModAttributes {

    // Созаём регистратор, который определяет новые аттрибуты как чать мода
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, DimasRPG.MOD_ID);


    // Регистрация аттрибута magic_res
    public static final RegistryObject<Attribute> MELEE_ARMOR = ATTRIBUTES.register("melee_armor",
            () -> new MagicArmorAttribute("attribute.name.melee_armor", 0.0));
    public static final RegistryObject<Attribute> RANGE_ARMOR = ATTRIBUTES.register("range_armor",
            () -> new MagicArmorAttribute("attribute.name.range_armor", 0.0));
    public static final RegistryObject<Attribute> MAGIC_ARMOR = ATTRIBUTES.register("magic_armor",
            () -> new MagicArmorAttribute("attribute.name.magic_armor", 0.0));
    public static final RegistryObject<Attribute> DAMAGE_TYPE = ATTRIBUTES.register("damage_type",
            () -> new DamageTypeAttribute("damage_type", DamageTypeAttribute.getTypeFromString("melee")));

    // Метод-триггер для вызова из главного класса мода
    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
