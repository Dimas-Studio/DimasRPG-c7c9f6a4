package com.dimasrpg;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class Debug {
    public static void printToChat(String S, ChatFormatting ... p_130945_){
        if (Minecraft.getInstance().player != null)
            Minecraft.getInstance().player.sendSystemMessage(
                    Component.literal(S).withStyle(p_130945_));
    }

    public static void printToChat(String S){
        if (Minecraft.getInstance().player != null)
            Minecraft.getInstance().player.sendSystemMessage(
                    Component.literal(S));
    }
}
