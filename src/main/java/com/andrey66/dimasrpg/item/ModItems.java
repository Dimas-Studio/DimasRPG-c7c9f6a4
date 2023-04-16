package com.andrey66.dimasrpg.item;

import com.andrey66.dimasrpg.DimasRPG;
import com.andrey66.dimasrpg.item.custom.EssenceCheckerItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// Класс для регистрации новых предметов с особыми свойствами
public class ModItems {

    // Созаём регистратор, который определяет новые предметы как чать мода
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DimasRPG.MOD_ID);

    // Регистрация предмета essence_checker
    public static final RegistryObject<Item> ESSENCE_CHECKER = ITEMS.register("essence_checker",
            () -> new EssenceCheckerItem(new Item.Properties()));

    // Метод-триггер для вызова из главного класса мода
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }



}
