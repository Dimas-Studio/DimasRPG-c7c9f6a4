package com.andrey66.dimasrpg.item.custom;

import com.andrey66.dimasrpg.attribute.ModAttributes;
import com.andrey66.dimasrpg.config.ConfigArmorValues;
import com.andrey66.dimasrpg.config.ConfigWeaponsValues;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

// Класс предметов, необходимых для проверки разных показателей. У данных предметов переопределён метод use с цклью вывода полезной информации
public class EssenceCheckerItem extends Item {
    public EssenceCheckerItem(Properties properties) {
        super(properties);
    }

    // Переопределение метода use для тестирования
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        player.sendSystemMessage(Component.literal("Your magic resistance: " + !level.isClientSide()));
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND && player.isShiftKeyDown()){
            double magic_res = Objects.requireNonNull(player.getAttribute(ModAttributes.MAGIC_RES.get())).getValue();
            player.sendSystemMessage(Component.literal("Your magic resistance: " + magic_res));

            if (ConfigArmorValues.exist("minecraft:diamond_chestplate")) {
                String value = Objects.requireNonNull(ConfigWeaponsValues.getValue("minecraft:iron_sword")).toString();
                player.sendSystemMessage(Component.literal("Iron_sword have " + value + " " + ConfigWeaponsValues.getType("minecraft:iron_sword") + " damage"));
            } else {
                player.sendSystemMessage(Component.literal("Iron_sword have default damage"));
            }
            player.sendSystemMessage(Component.literal(Double.toString(Objects.requireNonNull(player.getAttribute(ModAttributes.MAGIC_RES.get())).getValue())));


        } else if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            double value =  Objects.requireNonNull(player.getAttribute(ModAttributes.MAGIC_RES.get())).getValue();
            Objects.requireNonNull(player.getAttribute(ModAttributes.MAGIC_RES.get())).setBaseValue(value+1);
            player.sendSystemMessage(Component.literal("Done"));
        }
        return super.use(level, player, hand);
    }
}
