package com.dimasrpg;

import com.dimasrpg.item.ModItems;
import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class DimasRPG {
    public static final String MOD_ID = "dimasrpg";
    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));
    public static void init() {
        //Точка входа в мод
        ModItems.init(); // Регистрация предметов
        System.out.println(ExampleExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
