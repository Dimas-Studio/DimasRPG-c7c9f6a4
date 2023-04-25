package com.andrey66.dimasrpg.mixin;

import com.andrey66.dimasrpg.config.ConfigWeaponsValues;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
    @Shadow
    public abstract Item getItem();

    /*
    // Константа для порядка атрибутов оружия
    private List<Attribute> getAttributeOrder() {
        // задаем порядок атрибутов для мечей
        List<Attribute> attributeOrder = new ArrayList<>();
        attributeOrder.add(Attributes.ATTACK_DAMAGE);
        attributeOrder.add(Attributes.ATTACK_SPEED);
        return attributeOrder;
    }
    */

    // Метод смены атрибута ATTACK_DAMAGE
    private Multimap<Attribute, AttributeModifier> changeAttribute(Multimap<Attribute, AttributeModifier> original, float value) { // TODO: добавить параметр аттрибута
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        for (Map.Entry<Attribute, AttributeModifier> entry : original.entries()) {
            Attribute itemAttribute = entry.getKey();
            AttributeModifier modifier = entry.getValue();
            if (itemAttribute == Attributes.ATTACK_DAMAGE) { // TODO: Заменить константу на параметр аттрибута
                modifiers.put(itemAttribute, new AttributeModifier(modifier.getId(), modifier.getName(), value, modifier.getOperation()));
            } else {
                modifiers.put(itemAttribute, modifier);
            }
        }

        return modifiers;
    }


    /*
    private Multimap<Attribute, AttributeModifier> addAttribute(Multimap<Attribute, AttributeModifier> original, float value, Attribute attribute) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        //for (Map.Entry<Attribute, AttributeModifier> entry : original.entries()) {
        //    Attribute itemAttribute = entry.getKey();
        //    AttributeModifier modifier = entry.getValue();
        //    modifiers.put(itemAttribute, modifier);
        //    modifiers.put(attribute, new AttributeModifier(UUID.randomUUID(), attribute.getDescriptionId(), value, AttributeModifier.Operation.ADDITION));
        //}
        //return modifiers;
        //Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create(original);
        //modifiers.put(attribute, new AttributeModifier(UUID.randomUUID(), attribute.getDescriptionId(), value, AttributeModifier.Operation.ADDITION));
        return modifiers;
    }
    */
    // Изминение атрибутов для предметов из конфига
    @Inject(method = "getAttributeModifiers", at = @At("RETURN"), cancellable = true)
    private void modifyAttributeModifiers(EquipmentSlot equipmentSlot, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> callbackInfo) {
        String item_id = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.getItem())).toString();

        // Оружие
        if (ConfigWeaponsValues.exist(item_id)) {
            Float damage = ConfigWeaponsValues.getValue(item_id);
            if (damage == null) {
                return;
            }

            Multimap<Attribute, AttributeModifier> modifiers = callbackInfo.getReturnValue();
            modifiers = changeAttribute(modifiers, damage);

            // меняем порядок атрибутов
            /*
            List<Attribute> attributeOrder = getAttributeOrder();
            ArrayListMultimap<Attribute, AttributeModifier> orderedModifiers = ArrayListMultimap.create();
            for (Attribute attribute : attributeOrder) {
                if (modifiers.containsKey(attribute)) {
                    orderedModifiers.putAll(attribute, modifiers.get(attribute));
                }
            }*/
            callbackInfo.setReturnValue(modifiers);

        }
    }
}