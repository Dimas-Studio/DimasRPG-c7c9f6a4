package com.andrey66.dimasrpg.config;

import java.util.*;

// Класс определения структуры конфига оружий
public class ConfigWeaponsValues {

    // Хрант в себе словарь из конфиг файла
    private static final HashMap<String, HashMap<String, Float>> CONFIG_SPEC = new HashMap<>();

    // Метод настройки конфига по умолчанию
    public static void setDefaultConfigValues() {
        put("minecraft:iron_sword", "melee", 20);
        put("minecraft:diamond_sword", "melee", 40);
        put("minecraft:bow", "range", 30);
        put("minecraft:air", "melee", 0);
    }

    // Метод очистки настроек конфига (используется для очистки настроек по умолчанию для замены их настройками их файла)
    public static void clearDefaultConfigValues() {
        CONFIG_SPEC.clear();
    }

    // метод добавления нового поля конифга
    public static void put(String name, String type, float value) {
        HashMap<String, Float> innerMap = new HashMap<>();
        innerMap.put(type, value);
        CONFIG_SPEC.put(name, innerMap);
    }

    // Метод получения типа урона предмета
    public static String getType(String name) {
        if(CONFIG_SPEC.containsKey(name)){
            String type;
            Set<String> keys = CONFIG_SPEC.get(name).keySet();
            type = keys.iterator().next();
            return type;
        }
        return null;
    }


    // метод пролучения количества урона предмета
    public static Float getValue(String name) {
        if(CONFIG_SPEC.containsKey(name)){
            String type;
            Set<String> keys = CONFIG_SPEC.get(name).keySet();
            type = keys.iterator().next();
            return CONFIG_SPEC.get(name).get(type);
        }
        return null;
    }

    // Метод получения множества ключей (имён предметов) из конфига
    public static Set<String> getKeys() {
        return CONFIG_SPEC.keySet();
    }

    // Метод проверки ключа (имени) на существование
    public static Boolean exist(String name) {
        return CONFIG_SPEC.containsKey(name);
    }

}
