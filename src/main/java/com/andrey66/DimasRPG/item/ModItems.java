package com.andrey66.DimasRPG.item;

import com.andrey66.DimasRPG.DimasRPG;
//import net.minecraft.world.item.CreativeModeTab;  Для стандартный вкладок майнкрафта
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DimasRPG.MOD_ID);

    public static final RegistryObject<Item> ZIRCON = ITEMS.register("zircon",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.DIMAS_RPG)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}