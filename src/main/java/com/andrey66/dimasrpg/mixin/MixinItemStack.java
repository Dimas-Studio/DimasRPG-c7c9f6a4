package com.andrey66.dimasrpg.mixin;

import com.andrey66.dimasrpg.config.ConfigWeaponsValues;
import com.andrey66.dimasrpg.item.ModItems;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Shadow(remap = false)
    public abstract Item getItem();

    private boolean CheckItemsNames(String itemString, String configString) {
        String[] strArray = itemString.split("\\.");
        List<String> itemList = Arrays.asList(strArray);
        if (itemList.size() != 3) {
            return false;
        }
        if (!itemList.get(0).equals("item")) {
            return false;
        }

        String newItemString = itemList.get(1) + ":" + itemList.get(2);
        return newItemString.equals(configString);
    }

    // Константа для порядка атрибутов оружия
    private List<Attribute> getAttributeOrder() {
        // задаем порядок атрибутов для мечей
        List<Attribute> attributeOrder = new ArrayList<>();
        attributeOrder.add(Attributes.ATTACK_DAMAGE);
        attributeOrder.add(Attributes.ATTACK_SPEED);
        return attributeOrder;
    }

    // Метод смены атрибута ATTACK_DAMAGE
    private Multimap<Attribute, AttributeModifier> changeATTACK_DAMAGE(Multimap<Attribute, AttributeModifier> original, float value) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        for (Map.Entry<Attribute, AttributeModifier> entry : original.entries()) {
            Attribute itemAttribute = entry.getKey();
            AttributeModifier modifier = entry.getValue();
            if (itemAttribute == Attributes.ATTACK_DAMAGE) {
                modifiers.put(itemAttribute, new AttributeModifier(modifier.getId(), modifier.getName(), value, modifier.getOperation()));
            } else {
                modifiers.put(itemAttribute, modifier);
            }
        }
        return modifiers;
    }

    // Изминение атрибутов для предметов из конфига
    @Inject(method = "getAttributeModifiers", at = @At("RETURN"), cancellable = true, remap = false)
    private void modifyAttributeModifiers(EquipmentSlot equipmentSlot, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> callbackInfo) {
        String item_id = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.getItem())).toString();
        if (ConfigWeaponsValues.exist(item_id)) {
            Float damage = ConfigWeaponsValues.getValue(item_id);
            if (damage == null) {
                return;
            }

            Multimap<Attribute, AttributeModifier> modifiers = callbackInfo.getReturnValue();
            Multimap<Attribute, AttributeModifier> modifiers1 = changeATTACK_DAMAGE(modifiers, damage);

            // меняем порядок атрибутов
            List<Attribute> attributeOrder = getAttributeOrder();
            ArrayListMultimap<Attribute, AttributeModifier> orderedModifiers = ArrayListMultimap.create();
            for (Attribute attribute : attributeOrder) {
                if (modifiers1.containsKey(attribute)) {
                    orderedModifiers.putAll(attribute, modifiers1.get(attribute));
                }
            }
            callbackInfo.setReturnValue(modifiers1);
        }
    }
}