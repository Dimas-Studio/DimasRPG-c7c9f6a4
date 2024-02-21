package com.andrey66.DimasRPG.events;

import com.andrey66.DimasRPG.DimasRPG;
import com.andrey66.DimasRPG.networking.ModMessages;
import com.andrey66.DimasRPG.networking.packet.ChangeClassC2S;
import com.andrey66.DimasRPG.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = DimasRPG.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent event) {
            if (KeyBinding.OPEN_CLASS_MENU.consumeClick()) {
                //Minecraft.getInstance().player.sendSystemMessage(Component.literal(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", "iron_axe")).toString()));

                ModMessages.sendToServer(new ChangeClassC2S());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = DimasRPG.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.OPEN_CLASS_MENU);
        }
    }

}
