package com.dimasrpg.item;

import com.dimasrpg.DimasRPG;
import com.dimasrpg.ExampleExpectPlatform;
import com.dimasrpg.item.custom.ConfigCheckerItem;
import com.google.common.base.Suppliers;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;


public class ModItems {

    /** Новая вкладка креатива */
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(DimasRPG.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> MOD_TAB = TABS.register("tab", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + DimasRPG.MOD_ID + ".tab"),
                    () -> new ItemStack(ModItems.CONFIG_CHECKER.get())));


    /** Созаём регистратор, который определяет новые предметы как чать мода */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(DimasRPG.MOD_ID, Registries.ITEM);
    /** Новый предмет */
    public static final RegistrySupplier<Item> CONFIG_CHECKER = ITEMS.register("config_checker", () ->
            new ConfigCheckerItem(new Item.Properties().arch$tab(ModItems.MOD_TAB)));

    public static void init() {
        TABS.register();
        ITEMS.register();
    }
}
