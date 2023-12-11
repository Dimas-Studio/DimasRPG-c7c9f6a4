package com.andrey66.dimasrpg.attribute;

import com.andrey66.dimasrpg.DimasRPG;
import com.andrey66.dimasrpg.attribute.custom.*;
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


    // Регистрация аттрибутов
    public static final RegistryObject<Attribute> MELEE_ARMOR = ATTRIBUTES.register("melee_armor",
            () -> new MeleeArmorAttribute("attribute.name.melee_armor", 0.0));
    public static final RegistryObject<Attribute> RANGE_ARMOR = ATTRIBUTES.register("range_armor",
            () -> new RangeArmorAttribute("attribute.name.range_armor", 0.0));
    public static final RegistryObject<Attribute> MAGIC_ARMOR = ATTRIBUTES.register("magic_armor",
            () -> new MagicArmorAttribute("attribute.name.magic_armor", 0.0));
	public static final RegistryObject<Attribute> MELEE_DAMAGE = ATTRIBUTES.register("melee_damage",
            () -> new MeleeDamageAttribute("attribute.name.melee_damage", 1.0));
    public static final RegistryObject<Attribute> RANGE_DAMAGE = ATTRIBUTES.register("range_damage",
            () -> new RangeDamageAttribute("attribute.name.range_damage", 0.0));
    public static final RegistryObject<Attribute> MAGIC_DAMAGE = ATTRIBUTES.register("magic_damage",
            () -> new MagicDamageAttribute("attribute.name.magic_damage", 0.0));
    public static final RegistryObject<Attribute> ADMIN_DAMAGE = ATTRIBUTES.register("admin_damage",
            () -> new AdminDamageAttribute("attribute.name.admin_damage", 0.0));


    // Метод-триггер для вызова из главного класса мода
    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
