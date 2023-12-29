package com.dimasrpg.config;

import java.util.HashMap;
import java.util.Set;

public class WeaponConfigValues implements ConfigValuesInterface{
    // Хрант в себе словарь из конфиг файла
    private static final HashMap<String, HashMap<String, Float>> CONTENT = new HashMap<>();

    public static HashMap<String, HashMap<String, Float>> getDict() {
        return CONTENT;
    }
    public static void setDefaultConfigValues() {
        put("minecraft:iron_sword", "melee", 20);
        put("minecraft:diamond_sword", "melee", 40);
        put("minecraft:bow", "range", 30);
        put("minecraft:air", "melee", 0);
    }
    public static void clearDefaultConfigValues() {
        CONTENT.clear();
    }
    // метод добавления нового поля конифга
    public static void put(String name, String type, float value) {
        HashMap<String, Float> innerMap = new HashMap<>();
        innerMap.put(type, value);
        CONTENT.put(name, innerMap);
    }
    public static String[] getTypes(String name) {
        if(CONTENT.containsKey(name)){
            Set<String> keys = CONTENT.get(name).keySet();
            String types[] = new String[keys.size()];
            types = keys.toArray(types);
            return types;
        }
        return null;
    }
    public static Float getValue(String name, String type) {
        if(CONTENT.containsKey(name)){
            if(CONTENT.get(name).containsKey(type)) {
                return CONTENT.get(name).get(type);
            }
        }
        return null;
    }
    // Метод получения множества ключей (имён предметов) из конфига
    public static Set<String> getKeys() {
        return CONTENT.keySet();
    }

    // Метод проверки ключа (имени) на существование
    public static Boolean exist(String name) {
        return CONTENT.containsKey(name);
    }
}
