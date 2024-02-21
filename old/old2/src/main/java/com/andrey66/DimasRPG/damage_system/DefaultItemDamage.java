package com.andrey66.DimasRPG.damage_system;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

import java.util.HashMap;
import java.util.Map;

public class DefaultItemDamage {
    protected static final Map<String,Float> DICT = new HashMap<>();

    public DefaultItemDamage(){
        DICT.put("item.minecraft.air", 1F);
        DICT.put("block.minecraft.air", 1F);
        DICT.put("item.minecraft.iron_axe", 50F);
    }

    public static float get(Item item){
        if (exist(item.getDescriptionId())){
            return DICT.get(item.getDescriptionId());
        }
        if (item instanceof SwordItem){
            return ((SwordItem)item).getDamage();
        }
        if (item instanceof DiggerItem) {
            return ((DiggerItem)item).getAttackDamage();
        }

        //Только для админов
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendSystemMessage(
                    Component.literal("WARNING: " + item.getDescriptionId() + " don't registered!").withStyle(ChatFormatting.YELLOW));

            }
        return 0;
    }

    public static boolean exist(String item_id){
        return DICT.containsKey(item_id);
    }
}
