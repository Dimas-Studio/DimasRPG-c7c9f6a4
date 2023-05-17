package com.andrey66.dimasrpg.Math;

import net.minecraft.world.damagesource.CombatRules;

public class ModCombatRules extends CombatRules {
    public static float getDamageAfterAbsorb(float damage, float armor, float effect_defence, float attribute_defence) { // TODO: attribute_defence - отдельный атрибут (не броня), выраженный по 100 быльной шкале
        damage *= (1 - (effect_defence / 100)); // Защита от эфектов (бафов)
        damage *= (1 - (armor / 100)); // Броня
        damage *= (1 - (attribute_defence / 100)); // Родная защита существа
        return damage; //TODO: Учесть зачарования
    }
}
