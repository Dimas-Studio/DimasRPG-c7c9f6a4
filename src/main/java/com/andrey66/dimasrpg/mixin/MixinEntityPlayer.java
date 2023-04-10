package com.andrey66.dimasrpg.mixin;

import com.andrey66.dimasrpg.EssenceContainer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Player.class)
public abstract class MixinEntityPlayer implements EssenceContainer {

    /**
     * Наше новое поле в классе EntityPlayer - эссенция
     */
    private float essence;

    /**
     * Виртуальное поле, которое ссылается на реальное поле experienceLevel в классе EntityPlayer
     */
    @Shadow(remap = false)
    public int experienceLevel;

    @Override
    public float getEssence() {
        return this.essence;
    }

    /**
     * Изменяет текущее количество эссенции игрока. Если оно больше, чем максимально возможное для игрока - ставится максимальное
     */
    @Override
    public void setEssence(float value) {
        this.essence = Math.min(value, getMaxEssence());
    }

    /**
     * Максимальное количество эссенции игрока равно его уровню
     */
    @Override
    public float getMaxEssence() {
        return this.experienceLevel;
    }

    @Override
    public void setMaxEssence(float value) {
        // NOOP
        System.out.println("Максимальное количество эссенции зависит от уровня игрока!");
    }


}