package com.dimasrpg.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends MixinEntity{

    @Shadow
    protected abstract float getDamageAfterArmorAbsorb(DamageSource damageSource, float f);
    @Shadow
    protected abstract float getDamageAfterMagicAbsorb(DamageSource damageSource, float f);
    @Shadow
    public abstract float getAbsorptionAmount();
    @Shadow
    public abstract void setAbsorptionAmount(float f);
    @Shadow
    public abstract CombatTracker getCombatTracker();
    @Shadow
    public abstract void setHealth(float f);
    @Shadow
    public abstract float getHealth();
    @Inject(method = "actuallyHurt", at = @At("HEAD"), cancellable = true)
    protected void setDamageTo999(DamageSource damageSource, float damage, CallbackInfo ci) {
        damage = 999;
        if (!this.isInvulnerableTo(damageSource)) {
            damage = this.getDamageAfterArmorAbsorb(damageSource, damage);
            damage = this.getDamageAfterMagicAbsorb(damageSource, damage);
            float g = damage;
            damage = Math.max(damage - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (g - damage));
            float h = g - damage;
            if (h > 0.0F && h < 3.4028235E37F) {
                Entity var6 = damageSource.getEntity();
                if (var6 instanceof ServerPlayer) {
                    ServerPlayer serverPlayer = (ServerPlayer)var6;
                    serverPlayer.awardStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(h * 10.0F));
                }
            }

            if (damage != 0.0F) {
                this.getCombatTracker().recordDamage(damageSource, damage);
                this.setHealth(this.getHealth() - damage);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - damage);
                this.gameEvent(GameEvent.ENTITY_DAMAGE);
            }
        }
        ci.cancel();
    }
}
