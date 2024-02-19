package com.dimasrpg.mixin;

import com.dimasrpg.attribute.ModAttributes;
import com.dimasrpg.config.WeaponConfigValues;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mixin(Item.class)
public class MixinItem {
    @Shadow @Final private Holder.Reference<Item> builtInRegistryHolder;
    private final HashMap<String, ImmutableMultimap<Attribute, AttributeModifier>> defaultModifiers = new HashMap<>();
    protected void addNewAttributes(String sn_item){

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        if (WeaponConfigValues.exist(sn_item)){
            HashMap<String, Attribute> STRING_ITEM_ATTRIBUTES = new HashMap<>(){{
                put("melle", ModAttributes.MELEE_DAMAGE.get());
            }};

            if (WeaponConfigValues.getTypes(sn_item)!= null){
                for (String itemType: Objects.requireNonNull(WeaponConfigValues.getTypes(sn_item))){
                    Float value = WeaponConfigValues.getValue(sn_item, itemType);
                    if (STRING_ITEM_ATTRIBUTES.containsKey(itemType)&&itemType!=null&&value!=null) {
                        UUID randomUUID = UUID.randomUUID();
                        builder.put(STRING_ITEM_ATTRIBUTES.get(itemType), new AttributeModifier(randomUUID, itemType, value, AttributeModifier.Operation.ADDITION));
                    }
                }
            }
        }
        this.defaultModifiers.put(sn_item, builder.build());
    }
    private boolean defaultModifiriesConstains(String name){
        return defaultModifiers.containsKey(name);
    }
    @Inject(method = "getDefaultAttributeModifiers", at = @At("RETURN"), cancellable = true)
    public void getDefaultAttributeModifiers(EquipmentSlot equipmentSlot, CallbackInfoReturnable <Multimap<Attribute, AttributeModifier>> cir) {
        Item item = (Item) (Object) this;
       // if (item instanceof ArmorItem)
        String sn_item = BuiltInRegistries.ITEM.getKey(item).toString();
        if (equipmentSlot==EquipmentSlot.MAINHAND){
            if (!defaultModifiriesConstains(sn_item)){
                addNewAttributes(sn_item);
            } else {
              cir.setReturnValue(this.defaultModifiers.get(sn_item));
            }
        }
        cir.setReturnValue(ImmutableMultimap.of());
    }
    @Inject(method = "appendHoverText", at = @At("HEAD"))
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci){
        Item item = (Item) (Object) this;
        String sn_item = BuiltInRegistries.ITEM.getKey(item).toString();
        String text = "";
        if (!WeaponConfigValues.exist(sn_item)){
            return;
        }
        String[] types = WeaponConfigValues.getTypes(sn_item);
        for (String ttype: Objects.requireNonNull(types)){
            switch (ttype){
                case("melee") -> {
                    text=Component.translatable("key.dimasrpg.melee_type_description").getString();
                }
                case ("range") -> {
                    text=Component.translatable("key.dimasrpg.range_type_description").getString();
                }
                case ("magic") -> {
                    text=Component.translatable("key.dimasrpg.magic_type_description").getString();
                }
                case ("admin") -> {
                    text=Component.translatable("key.dimasrpg.admin_type_description").getString();
                }
                default -> {}
            }
            if (text!=""){
                list.add(Component.literal(text));
            }
            text="";
        }
    }


}
