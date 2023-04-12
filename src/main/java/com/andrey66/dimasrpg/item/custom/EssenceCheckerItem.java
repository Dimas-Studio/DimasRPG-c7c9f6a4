package com.andrey66.dimasrpg.item.custom;

import com.andrey66.dimasrpg.attribute.ModAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;

// Класс предметов, необходимых для проверки разных показателей. У данных предметов переопределён метод use с цклью вывода полезной информации
public class EssenceCheckerItem extends Item {
    public EssenceCheckerItem(Properties properties) {
        super(properties);
    }

    // Переопределение метода use для тестирования
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
