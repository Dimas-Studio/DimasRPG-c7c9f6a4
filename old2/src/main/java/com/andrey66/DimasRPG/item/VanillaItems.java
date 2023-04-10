package com.andrey66.DimasRPG.item;

import com.andrey66.DimasRPG.item.custom.LevitationRoad;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VanillaItems {
    public static final DeferredRegister<Item> VANILLA_ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<Item> IRON_SWORD = VANILLA_ITEMS.register("iron_sword",
            () -> new LevitationRoad(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));


    public static void register(IEventBus eventBus) {
        VANILLA_ITEMS.register(eventBus);
    }
}
