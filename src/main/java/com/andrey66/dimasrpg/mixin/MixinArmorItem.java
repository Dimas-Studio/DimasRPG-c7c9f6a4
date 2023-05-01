package com.andrey66.dimasrpg.mixin;

import com.andrey66.dimasrpg.attribute.ModAttributes;
import com.andrey66.dimasrpg.config.ConfigArmorValues;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.Util;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(ArmorItem.class)
public abstract class MixinArmorItem {

    private static final EnumMap<ArmorItem.Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266744_) -> {
        p_266744_.put(ArmorItem.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        p_266744_.put(ArmorItem.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        p_266744_.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        p_266744_.put(ArmorItem.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });

    protected MixinArmorItem() {
    }

    @Shadow @Final protected ArmorItem.Type type;
    private final HashMap<String, ImmutableMultimap<Attribute, AttributeModifier>> defaultModifiers = new HashMap<>();

    protected ArmorMaterial material1;
    protected ArmorItem.Type type1;
    @Inject(method = "<init>", at = @At("RETURN"))
    private void saveMaterial(ArmorMaterial material, ArmorItem.Type type, Item.Properties p_40388_, CallbackInfo ci){
        this.material1 = material;
        this.type1 = type;
    }

    private void addNewAttribute(ArmorMaterial material, ArmorItem.Type type, String itemString) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(type);
        if (material.getKnockbackResistance() > 0) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", (double)material.getKnockbackResistance(), AttributeModifier.Operation.ADDITION));
        }
        if (ConfigArmorValues.exist(itemString)) {
            HashMap<String, Attribute> STRING_ARMOR_ATTRIBUTES = new HashMap<String, Attribute>() {{
                put("melee", ModAttributes.MELEE_ARMOR.get());
                put("range", ModAttributes.RANGE_ARMOR.get());
                put("magic", ModAttributes.MAGIC_ARMOR.get());
                put("health", Attributes.MAX_HEALTH);
                put("knockback_resistance", Attributes.KNOCKBACK_RESISTANCE);
                put("speed", Attributes.MOVEMENT_SPEED);
                put("flying_speed", Attributes.FLYING_SPEED);
                put("knockback", Attributes.ATTACK_KNOCKBACK);
                put("attack_speed", Attributes.ATTACK_SPEED);
                put("luck", Attributes.LUCK);
                put("jump_strange", Attributes.JUMP_STRENGTH);
                put("melee_damage", ModAttributes.MELEE_DAMAGE.get());
                put("range_damage", ModAttributes.RANGE_DAMAGE.get());
                put("magic_damage", ModAttributes.MAGIC_DAMAGE.get());
            }};
            if(ConfigArmorValues.getTypes(itemString) != null){
                for(Map.Entry<String, Float> values: Objects.requireNonNull(ConfigArmorValues.getTypes(itemString)).entrySet()){
                    String armor_type = values.getKey();
                    Float value = values.getValue();
                    if (STRING_ARMOR_ATTRIBUTES.containsKey(armor_type)){
                        builder.put(STRING_ARMOR_ATTRIBUTES.get(armor_type), new AttributeModifier(uuid, armor_type, value, AttributeModifier.Operation.ADDITION));
                    }
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
        ArmorItem armorItem = (ArmorItem) (Object) this;
        String item_string = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(armorItem)).toString();
        if (p_40390_ == armorItem.getEquipmentSlot()) {
            if (!defaultModifiersContains(item_string)) {
                addNewAttribute(this.material1, this.type1, item_string);
            } else {
                cir.setReturnValue(this.defaultModifiers.get(item_string));
            }

        }
    }
}
