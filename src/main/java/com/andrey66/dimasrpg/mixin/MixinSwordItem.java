package com.andrey66.dimasrpg.mixin;

import com.andrey66.dimasrpg.attribute.ModAttributes;
import com.andrey66.dimasrpg.config.ConfigWeaponsValues;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(SwordItem.class)
public abstract class MixinSwordItem {

    protected MixinSwordItem() {
    }

    private final HashMap<String, ImmutableMultimap<Attribute, AttributeModifier>> defaultModifiers = new HashMap<>();


    private void addNewAttribute(String itemString) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (ConfigWeaponsValues.exist(itemString)) {
            HashMap<String, Attribute> STRING_ARMOR_ATTRIBUTES = new HashMap<>() {{
                put("melee_armor", ModAttributes.MELEE_ARMOR.get());
                put("range_armor", ModAttributes.RANGE_ARMOR.get());
                put("magic_armor", ModAttributes.MAGIC_ARMOR.get());
                put("health", Attributes.MAX_HEALTH);
                put("knockback_resistance", Attributes.KNOCKBACK_RESISTANCE);
                put("speed", Attributes.MOVEMENT_SPEED);
                put("flying_speed", Attributes.FLYING_SPEED);
                put("knockback", Attributes.ATTACK_KNOCKBACK);
                put("attack_speed", Attributes.ATTACK_SPEED);
                put("luck", Attributes.LUCK);
                put("jump_strange", Attributes.JUMP_STRENGTH);
                put("melee", ModAttributes.MELEE_DAMAGE.get());
                put("range", ModAttributes.RANGE_DAMAGE.get());
                put("magic", ModAttributes.MAGIC_DAMAGE.get());
            }};
            if(ConfigWeaponsValues.getType(itemString) != null){
                String armor_type = ConfigWeaponsValues.getType(itemString);
                Float value = ConfigWeaponsValues.getValue(itemString);
                if (STRING_ARMOR_ATTRIBUTES.containsKey(armor_type) && armor_type != null && value != null){
                    UUID randomUUID = UUID.randomUUID();
                    builder.put(STRING_ARMOR_ATTRIBUTES.get(armor_type), new AttributeModifier(randomUUID, armor_type, value, AttributeModifier.Operation.ADDITION));
                }
            }

        }
        this.defaultModifiers.put(itemString, builder.build());
    }

    private boolean defaultModifiersContains(String name){
        return defaultModifiers.containsKey(name);
    }

    @Inject(method = "getDefaultAttributeModifiers", at = @At("RETURN"), cancellable = true)
    public void getDefaultAttributeModifiers(EquipmentSlot p_40390_, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> cir) {
        SwordItem swordItem = (SwordItem) (Object) this;
        String item_string = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(swordItem)).toString();
        if (p_40390_ == EquipmentSlot.MAINHAND) {
            if (!defaultModifiersContains(item_string)) {
                addNewAttribute(item_string);
            } else {
                cir.setReturnValue(this.defaultModifiers.get(item_string));
            }

        }
    }

    @Inject(method = "getDamage", at = @At("RETURN"), cancellable = true)
    public void getDamage(CallbackInfoReturnable<Float> cir){
        SwordItem swordItem = (SwordItem) (Object) this;
        String item_string = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(swordItem)).toString();
        if (ConfigWeaponsValues.exist(item_string)){
            cir.setReturnValue(ConfigWeaponsValues.getValue(item_string));
        }
    }
}
