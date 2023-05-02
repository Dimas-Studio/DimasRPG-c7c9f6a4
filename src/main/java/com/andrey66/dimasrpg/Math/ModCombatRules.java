package com.andrey66.dimasrpg.Math;

import com.andrey66.dimasrpg.Debug;
import net.minecraft.ChatFormatting;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.CombatRules;

public class ModCombatRules extends CombatRules {
    public static float getDamageAfterAbsorb(float damage, float defense) {
        if (defense * 2 > damage) {
            damage = (float) (Math.round(Math.max(damage * (1 - 0.02 * defense), damage * 0.5) * 100.0) / 100.0);
        }

        return damage;
    }
}
