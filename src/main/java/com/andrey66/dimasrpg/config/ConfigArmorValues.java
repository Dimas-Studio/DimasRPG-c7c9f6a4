package com.andrey66.dimasrpg.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// Класс определения структуры конфига брони
public class ConfigArmorValues {

    // Хрант в себе словарь из конфиг файла
    private static final HashMap<String, HashMap<String, Float>> CONFIG_SPEC = new HashMap<>();

    // Метод настройки конфига по умолчанию
    public static void setDefaultConfigValues() {
        put("minecraft:diamond_chestplate", new HashMap<String, Float>(){{
            put("melee", 10.0f);
            put("range", 11.0f);
        }});
    }

    // Метод очистки настроек конфига (используется для очистки настроек по умолчанию для замены их настройками их файла)
    public static void clearDefaultConfigValues() {
        CONFIG_SPEC.clear();
    }

    // метод добавления нового поля конифга
    public static void put(String name, HashMap<String, Float> values) {
        HashMap<String, Float> innerMap = new HashMap<>();
        for (Map.Entry<String, Float> entry : values.entrySet()) {
            String type = entry.getKey();
            Float value = entry.getValue();
            innerMap.put(type, value);
        }
        CONFIG_SPEC.put(name, innerMap);
    }

    // Метод получения типов блокировки предмета
    //public static String getTypes(String name) {
    //    if(CONFIG_SPEC.containsKey(name)){
    //        String type;
    //        Set<String> keys = CONFIG_SPEC.get(name).keySet();
    //        type = keys.iterator().next();
    //        return type;
    //    }
    //    return null;
    //}


    // метод пролучения количества защиты предмета
    public static Float getValue(String name, String type) {
        if(CONFIG_SPEC.containsKey(name)){
            if (CONFIG_SPEC.get(name).containsKey(type)) {
                return CONFIG_SPEC.get(name).get(type);
            }
        }
        return null;
    }

    public static HashMap<String, Float> getTypes(String name) {
        if(CONFIG_SPEC.containsKey(name)){
            return CONFIG_SPEC.get(name);
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
