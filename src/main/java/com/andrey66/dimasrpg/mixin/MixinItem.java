package com.andrey66.dimasrpg.mixin;

import com.andrey66.dimasrpg.Debug;
import com.andrey66.dimasrpg.attribute.ModAttributes;
import com.andrey66.dimasrpg.config.ConfigArmorValues;
import com.andrey66.dimasrpg.config.ConfigWeaponsValues;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(method = "inventoryTick", at = @At(value = "HEAD"))
    public void addCustomNbt(ItemStack itemStack, Level level, Entity p_41406_, int p_41407_, boolean p_41408_, CallbackInfo ci) {
        if (!level.isClientSide()) {
            CompoundTag root = itemStack.getOrCreateTag();
            CompoundTag attributes = new CompoundTag();
            ListTag attributeList = new ListTag();
            String item_id = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(itemStack.getItem())).toString();
            if (!root.contains("AttributeModifiers") && ConfigArmorValues.exist(item_id)) {
                String attributeName = "";
                String name = "";
                String type;
                boolean changeAttribute = false;
                double amount;
                String slot;
                Debug.printToChat(ConfigArmorValues.getSlots(item_id).toString());
                for (Map.Entry<String, HashMap<String, Float>> slots : Objects.requireNonNull(ConfigArmorValues.getSlots(item_id)).entrySet()) {
                    slot = slots.getKey();
                    for (Map.Entry<String, Float> values : Objects.requireNonNull(ConfigArmorValues.getTypes(item_id, slot)).entrySet()){
                        CompoundTag attribute = new CompoundTag();
                        type = values.getKey();
                        amount = values.getValue();
                        switch (type) {
                            case ("melee") -> {
                                attributeName = ModAttributes.MELEE_ARMOR.getId().toString();
                                changeAttribute = true;
                            }
                            case ("range") -> {
                                attributeName = ModAttributes.RANGE_ARMOR.getId().toString();
                                changeAttribute = true;
                            }
                            case ("magic") -> {
                                attributeName = ModAttributes.MAGIC_ARMOR.getId().toString();
                                changeAttribute = true;
                            }
                        }
                        if(changeAttribute){
                            attribute.putString("AttributeName", attributeName);
                            attribute.putString("Name", attributeName);
                            attribute.putDouble("Amount", amount);
                            UUID uuid = UUID.randomUUID();
                            long mostSignificantBits = uuid.getMostSignificantBits();
                            long leastSignificantBits = uuid.getLeastSignificantBits();

                            int[] uuidArray = new int[]{
                                    (int) (mostSignificantBits >> 32),
                                    (int) mostSignificantBits,
                                    (int) (leastSignificantBits >> 32),
                                    (int) leastSignificantBits
                            };
                            attribute.putIntArray("UUID", uuidArray);
                            attribute.putString("Slot", slot);

                            attributeList.add(attribute);

                        }
                    }

                }
                attributes.put("AttributeModifiers", attributeList);
                itemStack.setTag(attributes);
            }
        }
    }
}
