package com.andrey66.dimasrpg.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class MixinPlayer extends MixinLivingEntity{
    @Inject(method = "actuallyHurt", at = @At("HEAD"), cancellable = true)
    protected void raeCalculateDamage(DamageSource damageSource, float damage, CallbackInfo ci) {
        super.reCalculateDamage(damageSource, damage, ci);
    }
}
