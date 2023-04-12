package com.andrey66.dimasrpg.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

// Класс конфига для настройки оружий
public class DimasRPGWeaponsCommonConfigs {

    // Создание установщика файла
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    // Переменная для вызова из файла мода
    public static final ForgeConfigSpec SPEC;

    // Конструкция для создания переменной в файле
    public static final ForgeConfigSpec.ConfigValue<List<List<String>>> LIST_OF_WEAPONS;


    // Вид файла
    static {

        //// Проверка на то, что вложенный лист имеет корректную структуру
        Predicate<Object> weaponListValidator = (obj) -> {
            if (obj instanceof List weaponList) {
                for (Object weapon : weaponList) {
                    if (weapon instanceof List weaponData) {
                        if (weaponData.size() == 3) {
                            Object weaponNameObj = weaponData.get(0);
                            Object weaponTypeObj = weaponData.get(1);
                            Object weaponDamageObj = weaponData.get(2);

                            if (weaponNameObj instanceof String && weaponTypeObj instanceof String weaponType) {

                                if (!weaponType.equals("melee") && !weaponType.equals("magic") && !weaponType.equals("range")) {
                                    return false;
                                }

                                if (weaponDamageObj instanceof String weaponDamageStr) {
                                    try {
                                        Float.parseFloat(weaponDamageStr);
                                    } catch (NumberFormatException ex) {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        };

        List<List<String>> defaultWeapons = new ArrayList<>();
        defaultWeapons.add(List.of("Sword", "melee", "7.0"));
        defaultWeapons.add(List.of("Bow", "range", "5.0"));

        BUILDER.push("List of weapons with attack parameter");
        LIST_OF_WEAPONS = BUILDER
                .comment("The list must match the structure:")
                .comment("[[name_1: {type_1, damage_1],")
                .comment("[name_2: {type_2, damage_2],")
                .comment("...")
                .comment("[name_n: {type_n, damage_n]]")
                .comment("Where: name - name of the item;")
                .comment("type - one of three item types: melee, magic or range")
                .comment("damage - float number of base damage")
                .define("list_of_weapons", defaultWeapons, weaponListValidator);
        BUILDER.pop();
        SPEC = BUILDER.build();

    }
}