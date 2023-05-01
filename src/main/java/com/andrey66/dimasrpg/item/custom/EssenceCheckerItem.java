package com.andrey66.dimasrpg.item.custom;

import com.andrey66.dimasrpg.Debug;
import com.andrey66.dimasrpg.attribute.ModAttributes;
import com.andrey66.dimasrpg.config.ConfigArmorValues;
import com.andrey66.dimasrpg.config.ConfigWeaponsValues;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
        Item item = Items.IRON_HOE;

        Debug.printToChat(item.getDescriptionId());

        return super.use(level, player, hand);
    }
}
