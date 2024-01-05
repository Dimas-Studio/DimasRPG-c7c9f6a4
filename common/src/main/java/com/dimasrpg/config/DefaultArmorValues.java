package com.dimasrpg.config;


public class DefaultArmorValues {
    public static void init(){
        ArmorConfigValues.put("minecraft:chainmail_chestplate", "melee", 20.0F);
        ArmorConfigValues.put("minecraft:chainmail_leggings", "melee", 40.0F);
        ArmorConfigValues.put("minecraft:chainmail_helmet", "range", 30.0F);
        ArmorConfigValues.put("minecraft:chainmail_helmet", "admin", 30.0F);
        ArmorConfigValues.put("minecraft:chainmail_helmet", "magic", 30.0F);
        ArmorConfigValues.put("minecraft:chainmail_boots", "melee", 5.0F);
    }
}

    /*
    *   Здесь описываются стандартные значения для
    *   Если нет файла конфигурации, то будут использоваться эти значения
    *
    *
     */