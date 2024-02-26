package com.andrey66.DimasRPG.mixin;

import com.andrey66.DimasRPG.DamageContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinDamage implements DamageContainer {
    /**
     * Наше новое поле в классе EntityPlayer - эссенция

    private float essence;

    /**
     * Виртуальное поле, которое ссылается на реальное поле experienceLevel в классе EntityPlayer

    @Shadow(remap = true)
    public int experienceLevel;

    @Override
    public float getEssence() {
        return this.essence;
    }

    /**
     * Изменяет текущее количество эссенции игрока. Если оно больше, чем максимально возможное для игрока - ставится максимальное

    @Override
    public void setEssence(float value) {
        this.essence = Math.min(value, getMaxEssence());
    }

    /**
     * Максимальное количество эссенции игрока равно его уровню

    @Override
    public float getMaxEssence() {
        return this.experienceLevel;
    }

    @Override
    public void setMaxEssence(float value) {
        // NOOP
        System.out.println("Максимальное количество эссенции зависит от уровня игрока!");
    }
    */
    @Inject(method = "hurt", at = @At("HEAD"))
    private void chat(DamageSource damageSource, float damage, CallbackInfoReturnable<Boolean> cir) {
        Minecraft.getInstance().player.sendSystemMessage(Component.literal(String.valueOf(damage)));
        System.out.println("Mixin!");
    }

}

