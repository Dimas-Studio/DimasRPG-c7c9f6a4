package com.andrey66.dimasrpg.mixin;

import com.andrey66.dimasrpg.attribute.ModAttributes;
import com.andrey66.dimasrpg.config.ConfigWeaponsValues;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

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


    private final HashMap<String, ImmutableMultimap<Attribute, AttributeModifier>> defaultModifiers = new HashMap<>();
    protected void addNewAttribute(String itemString) {
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

    public boolean defaultModifiersContains(String name){
        return defaultModifiers.containsKey(name);
    }

    @Inject(method = "getDefaultAttributeModifiers", at = @At("RETURN"), cancellable = true)
    public void setAttributesToProjectileWeapon(EquipmentSlot p_40390_, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> cir) {
        Item item = (Item) (Object) this;
        if (!(item instanceof ProjectileWeaponItem rangedItem)) {
            return;
        }
        String item_string = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(rangedItem)).toString();
        if (p_40390_ == EquipmentSlot.MAINHAND) {
            if (!defaultModifiersContains(item_string)) {
                addNewAttribute(item_string);
            } else {
                cir.setReturnValue(this.defaultModifiers.get(item_string));
            }
        }
    }
}
