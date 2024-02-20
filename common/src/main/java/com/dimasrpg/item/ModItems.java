package com.dimasrpg.item;

import com.dimasrpg.DimasRPG;
import com.dimasrpg.item.custom.ConfigCheckerItem;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;


public class ModItems {
    private ModItems() { }

    /** Регистратор для новых креативных вкладок */
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(
                    DimasRPG.MOD_ID,
                    Registries.CREATIVE_MODE_TAB
            );
    /**
     * Вкладка мода
     */
    public static final RegistrySupplier<CreativeModeTab> MOD_TAB =
            TABS.register("tab", () ->
                    CreativeTabRegistry.create(
                            Component.translatable(
                                    "itemGroup." + DimasRPG.MOD_ID + ".tab"
                            ),
                    () -> new ItemStack(ModItems.CONFIG_CHECKER.get()))
            );


    /** Созаём регистратор, который определяет новые предметы как чать мода. */
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(DimasRPG.MOD_ID, Registries.ITEM);
    /** Новый предмет. */
    public static final RegistrySupplier<Item> CONFIG_CHECKER =
            ITEMS.register("config_checker", () ->
                    new ConfigCheckerItem(
                            new Item.Properties().arch$tab(ModItems.MOD_TAB)
                    )
            );

    /**
     * Регистрация предметов и креативных вкладок.
     */
    public static void init() {
        TABS.register();
        ITEMS.register();
    }
}
