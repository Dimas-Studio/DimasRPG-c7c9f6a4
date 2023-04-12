package com.andrey66.dimasrpg;

import com.andrey66.dimasrpg.attribute.ModAttributes;
import com.andrey66.dimasrpg.config.DimasRPGWeaponsCommonConfig;
import com.andrey66.dimasrpg.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(DimasRPG.MOD_ID)
public class DimasRPG
{
    // регистрация mod id для ссылок из конфиг файлов
    public static final String MOD_ID = "dimasrpg";
    public static final Logger LOGGER = LogUtils.getLogger(); // Создание инструмента для логгинга
    public DimasRPG()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus); // регистрация предметов
        ModAttributes.register(modEventBus); // регистрация аттрибутов
        modEventBus.addListener(this::commonSetup);
        DimasRPGWeaponsCommonConfig.initConfig(MOD_ID); // регистрация конфигоа оружия
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {

        DimasRPGWeaponsCommonConfig.initConfig(MOD_ID);

    }
    private void addCreative(CreativeModeTabEvent.BuildContents event) { // Добавление предмета в меню креатива
        if(event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) { // Инструменты
            event.accept(ModItems.ESSENCE_CHECKER);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }


    }
}
