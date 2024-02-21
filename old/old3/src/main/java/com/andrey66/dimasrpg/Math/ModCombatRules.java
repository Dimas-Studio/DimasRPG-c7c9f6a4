package com.andrey66.dimasrpg.Math;

import net.minecraft.world.damagesource.CombatRules;

import static java.lang.Math.min;

public class ModCombatRules extends CombatRules {
    public static float getDamageAfterAbsorb(float damage, float armor, float effect_defence, float attribute_defence) { // attribute_defence - отдельный атрибут (не броня), выраженный по 100 быльной шкале
        damage *= (1 - (min(effect_defence, 99) / 100)); // Защита от эфектов (бафов)
        damage *= (1 - (min(armor, 99) / 100)); // Броня
        damage *= (1 - (min(attribute_defence, 99) / 100)); // Родная защита существа
        return damage; //Учесть зачарования
    }
}
