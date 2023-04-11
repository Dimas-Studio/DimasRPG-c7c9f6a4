package com.andrey66.dimasrpg.item;

import com.andrey66.dimasrpg.DimasRPG;
import com.andrey66.dimasrpg.item.custom.EssenceCheckerItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DimasRPG.MOD_ID);

    public static final RegistryObject<Item> ESSENCE_CHECKER = ITEMS.register("essence_checker",
            () -> new EssenceCheckerItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }



}