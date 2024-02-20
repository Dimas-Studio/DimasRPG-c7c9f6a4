package com.dimasrpg;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class Debug {
    private Debug(){}
    /**
     * Выводит сообщения в чат для отладки.
     * Версия метода c форматированием.
     */
    public static void printToChat(final String s, final ChatFormatting chatFormatting) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendSystemMessage(
                    Component.literal(s).withStyle(chatFormatting));
        }
    }

    /**
     * Выводит сообщения в чат для отладки.
     * Версия метода без форматирования.
     */
    public static void printToChat(final String s) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendSystemMessage(
                    Component.literal(s));
        }
    }
}
