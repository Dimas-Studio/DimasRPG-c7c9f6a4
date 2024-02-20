package com.dimasrpg;

import com.dimasrpg.config.ConfigProvider;
import com.dimasrpg.item.ModItems;
import com.google.common.base.Suppliers;
import com.mojang.logging.LogUtils;
import dev.architectury.registry.registries.RegistrarManager;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.function.Supplier;

public class DimasRPG {
    private DimasRPG() {}

    /**
     * ID мода
     */
    public static final String MOD_ID = "dimasrpg";

    /**
     * Инструмент для логирования событий в консоль
     */
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * We can use this if we don't want to use DeferredRegister.
     */
    public static final Supplier<RegistrarManager> REGISTRIES =
            Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));
    /**
     * Точка входа в мод.
     */
    public static void init() {
        LOGGER.makeLoggingEventBuilder(Level.DEBUG);
        ConfigProvider.createReadAllConfigs();
        ModItems.init(); // Регистрация предметов
        System.out.println(
                ExampleExpectPlatform
                        .getConfigDirectory()
                        .toAbsolutePath()
                        .normalize()
                        .toString()
        );
    }
}
