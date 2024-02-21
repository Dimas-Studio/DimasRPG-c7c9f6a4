package com.andrey66.DimasRPG.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LevitationRoad extends Item {
    public LevitationRoad(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(hand == InteractionHand.MAIN_HAND) {
            // print_to_player(player ,Integer.toString(player.totalExperience));
            if (player.totalExperience >= 20 || player.experienceLevel > 0){
                player.giveExperiencePoints(-10);
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60));
            }
        }
        player.getCooldowns().addCooldown(this,20);
        return super.use(level, player, hand);
    }

    private void print_to_player(Player player, String str){
        player.sendSystemMessage(Component.literal(str));
    }
}
