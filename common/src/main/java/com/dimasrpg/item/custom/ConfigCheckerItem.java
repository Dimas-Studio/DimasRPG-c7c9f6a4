package com.dimasrpg.item.custom;

import com.dimasrpg.Debug;
import com.dimasrpg.config.WeaponConfigValues;
import com.dimasrpg.config.ArmorConfigValues;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;



public class ConfigCheckerItem extends Item {
    /**
     * КОнструктор для предметов типа ConfigCheckerItem
     * @param properties свойства предмета
     */
    public ConfigCheckerItem(final Properties properties) {
        super(properties);
    }

    /** Переопределение метода use для тестирования. */
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
            final Level level,
            final Player player,
            final @NotNull InteractionHand hand) {
        if (!level.isClientSide()) {
            Debug.printToChat("Hello world!");
            Debug.printToChat(WeaponConfigValues.getDict().toString());
            Debug.printToChat(ArmorConfigValues.getDict().toString());
        }
        return super.use(level, player, hand);
    }
}
