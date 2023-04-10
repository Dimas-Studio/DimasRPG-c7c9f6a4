package com.andrey66.DimasRPG.events;

import com.andrey66.DimasRPG.DimasRPG;
import com.andrey66.DimasRPG.damage_system.CalculateDamage;
import com.andrey66.DimasRPG.player_class.PlayerClass;
import com.andrey66.DimasRPG.player_class.PlayerClassProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class MobEvents {
    @Mod.EventBusSubscriber(modid = DimasRPG.MOD_ID)
    public static class ForgeEvents{

        @SubscribeEvent
        public static void onLivingDamage(LivingDamageEvent event){
            if(!event.getSource().equals(DamageSource.OUT_OF_WORLD)) {
                if (event.getSource().getEntity() != null){
                    CalculateDamage damage = new CalculateDamage(event.getEntity(), event.getSource(), event.getAmount());
                    event.setAmount(damage.getFinalDamage());
                }
            }

        }

        @SubscribeEvent
        public static void onAttachCapabilityPlayer(AttachCapabilitiesEvent<Entity> event){
            if (event.getObject() instanceof Player){
                if(!event.getObject().getCapability(PlayerClassProvider.PLAYER_CLASS).isPresent()){
                    event.addCapability(new ResourceLocation(DimasRPG.MOD_ID, "properties"), new PlayerClassProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event){
            if(event.isWasDeath()){
                event.getOriginal().getCapability(PlayerClassProvider.PLAYER_CLASS).ifPresent(oldStore ->
                        event.getOriginal().getCapability(PlayerClassProvider.PLAYER_CLASS).ifPresent(newStore ->
                                newStore.copyFrom(oldStore)));
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
            event.register(PlayerClass.class);
        }

        // @SubscribeEvent
        // public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        //     if(event.side == LogicalSide.SERVER){
        //         event.player.getCapability(PlayerClassProvider.PLAYER_CLASS).ifPresent(player_class -> {
        //                 if (player_class.getPlayer_class() > 0 && event.player.getRandom().nextFloat() < 0.005f){
        //                     player_class.subPlayerClass(1);
        //                     event.player.sendSystemMessage(Component.literal("-"));
        //                 }
        //         });
        //     }
        // }
    }



}

