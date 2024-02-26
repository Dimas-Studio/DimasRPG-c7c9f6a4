package com.dimasrpg.config;

import java.util.HashMap;
import java.util.Set;

public final class ArmorConfigValues {
    private ArmorConfigValues() { }


    /**
     * Хранит в себе словарь из конфиг файла.
     */
    private static final HashMap<String, HashMap<String, Float>> CONTENT
            = new HashMap<>();


    /**
     * Получает словарь CONTENT.
     * @return словарь CONTENT
     */
    public static HashMap<String, HashMap<String, Float>> getDict() {
        return CONTENT;
    }


    /**
     * Устанавливает значения по умолчанию.
     */
    public static void setDefaultConfigValues() {
        DefaultArmorValues.init();
    }


    /**
     * Очищает значения в словаре.
     */
    public static void clearDefaultConfigValues() {
        CONTENT.clear();
    }


    /**
     * Добавляет новый предмет в конфиг-словарь.
     * @param name название предмета
     * @param type тип защиты
     * @param value количество защиты
     */
    public static void put(
            final String name,
            final String type,
            final float value) {
        HashMap<String, Float> innerMap = new HashMap<>();
        if (CONTENT.containsKey(name)) {
            innerMap = CONTENT.get(name);
        }
        innerMap.put(type, value);
        CONTENT.put(name, innerMap);
    }


    /**
     * Получает массив типов защиты предмета.
     * @param name название предмета
     * @return массив типов защиты
     */

    public static String[] getTypes(final String name) {
        if (CONTENT.containsKey(name)) {
            Set<String> keys = CONTENT.get(name).keySet();
            String[] types = new String[keys.size()];
            types = keys.toArray(types);
            return types;
        }
        return null;
    }


    /**
     * Получает количество защиты определённого типа указанного предмета.
     * @param name название предмета
     * @param type тип защиты
     * @return количество защиты
     */
    public static Float getValue(final String name, final String type) {
        if (CONTENT.containsKey(name)) {
            if (CONTENT.get(name).containsKey(type)) {
                return CONTENT.get(name).get(type);
            }
        }
        return null;
    }


    /**
     * Получает множество всех предметов в конфиг-словаре.
     * @return множество предметов
     */
    public static Set<String> getKeys() {
        return CONTENT.keySet();
    }


    /**
     * Проверяет предмет на существование в конфиг-словаре.
     * @param name название предмета
     * @return true, если предмет имеется в конфиге
     */
    public static Boolean exist(final String name) {
        return CONTENT.containsKey(name);
    }
}
