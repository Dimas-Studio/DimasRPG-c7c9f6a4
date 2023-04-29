package com.andrey66.dimasrpg.mixin;

import com.andrey66.dimasrpg.config.ConfigWeaponsValues;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(Item.class)
public class MixinItem {
    @Inject(method = "appendHoverText", at = @At("HEAD"))
    public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_, CallbackInfo ci) {
        Item item = (Item)(Object)this;
        String itemString = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).toString();
        String text = "";
        String type = ConfigWeaponsValues.getType(itemString);
        if (type == null) {
            return;
        }
        switch (type) {
            case ("melee") -> text = Component.translatable("key.dimasrpg.melee_type_description").getString();
            case ("range") -> text = Component.translatable("key.dimasrpg.range_type_description").getString();
            case ("magic") -> text = Component.translatable("key.dimasrpg.magic_type_description").getString();
            default -> {
            }
        }
        if (!text.equals("")){
            p_41423_.add(Component.literal(text));
        }
    }
}
