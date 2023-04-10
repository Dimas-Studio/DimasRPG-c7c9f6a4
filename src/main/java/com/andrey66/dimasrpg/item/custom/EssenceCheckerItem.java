package com.andrey66.dimasrpg.item.custom;

import com.andrey66.dimasrpg.attribute.ModAttributes;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.intellij.lang.annotations.Identifier;

import java.util.Objects;

public class EssenceCheckerItem extends Item {

    public EssenceCheckerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND && player.isShiftKeyDown()){
            LivingEntity livingEntity = player;
            double magic_res = Objects.requireNonNull(livingEntity.getAttribute(ModAttributes.MAGIC_RES.get())).getValue();
            player.sendSystemMessage(Component.literal("Your magic resistance: " + magic_res));


        }
        return super.use(level, player, hand);
    }
}
