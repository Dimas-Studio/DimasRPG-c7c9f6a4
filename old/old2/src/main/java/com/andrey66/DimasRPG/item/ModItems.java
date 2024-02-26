package com.andrey66.DimasRPG.item;

import com.andrey66.DimasRPG.DimasRPG;
//import net.minecraft.world.item.CreativeModeTab;  Для стандартный вкладок майнкрафта
import com.andrey66.DimasRPG.item.custom.LevitationRoad;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DimasRPG.MOD_ID);

    public static final RegistryObject<Item> STEEL = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.DIMAS_RPG)));

    public static final RegistryObject<Item> LEVITATION_ROAD = ITEMS.register("levitation_road",
            () -> new LevitationRoad(new Item.Properties().tab(ModCreativeModeTab.DIMAS_RPG)));

    // Create check entity type wand to creative
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}