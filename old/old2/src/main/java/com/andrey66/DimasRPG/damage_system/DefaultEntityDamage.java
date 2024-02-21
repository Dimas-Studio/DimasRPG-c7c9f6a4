package com.andrey66.DimasRPG.damage_system;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.HashMap;
import java.util.Map;

public class DefaultEntityDamage {
    protected static final Map<String,Float> DICT = new HashMap<>();

    public DefaultEntityDamage(){
        DICT.put("minecraft:zombie", 2F);
    }

    public static float get(LivingEntity entity){
        if (exist(entity.getEncodeId())){
            return DICT.get(entity.getEncodeId());
        }



        //Только для админов
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendSystemMessage(
                    Component.literal("WARNING: " + entity.getEncodeId() + " don't registered!").withStyle(ChatFormatting.YELLOW));
        }
        return (float)entity.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
    }

    public static boolean exist(String item_id){
        return DICT.containsKey(item_id);
    }
}
