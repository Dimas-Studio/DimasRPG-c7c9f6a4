package com.dimasrpg;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public final class Debug {
    private Debug() { }
    /**
     * Выводит сообщения в чат для отладки.
     * Версия метода с форматированием.
     *
     * @param s Строка для печати.
     * @param chatFormatting Правила форматирования.
     */
    public static void printToChat(
            final String s, final ChatFormatting chatFormatting) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendSystemMessage(
                    Component.literal(s).withStyle(chatFormatting));
        }
    }

    /**
     * Выводит сообщения в чат для отладки.
     * Версия метода без форматирования.
     *
     * @param s Строка для печати.
     */
    public static void printToChat(final String s) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendSystemMessage(
                    Component.literal(s));
        }
    }
}
