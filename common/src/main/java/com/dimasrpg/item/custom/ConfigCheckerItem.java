package com.dimasrpg.item.custom;

import com.dimasrpg.Debug;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;


public class ConfigCheckerItem extends Item {
    public ConfigCheckerItem(Properties properties) {
        super(properties);
    }

    /** Переопределение метода use для тестирования */
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        if(!level.isClientSide()) {
            Debug.printToChat("Hello world!");
        }
        return super.use(level, player, hand);
    }
}
