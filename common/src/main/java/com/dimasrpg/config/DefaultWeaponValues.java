package com.dimasrpg.config;


public class DefaultWeaponValues {
    public static void init(){
        WeaponConfigValues.put("minecraft:iron_sword", "melee", 20);
        WeaponConfigValues.put("minecraft:diamond_sword", "melee", 40);
        WeaponConfigValues.put("minecraft:bow", "range", 30);
        WeaponConfigValues.put("minecraft:air", "melee", 0);
    }
}

