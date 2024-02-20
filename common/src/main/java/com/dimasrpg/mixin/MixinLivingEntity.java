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

    /**
     * Количество урона после поглощения брони.
     * @param damageSource Источник урона
     * @param f Количество урона
     * @return Количество урона после поглощения брони
     */
    @Shadow
    protected abstract float getDamageAfterArmorAbsorb(DamageSource damageSource, float f);

    /**
     * Количество урона после поглощения магией.
     * @param damageSource Источник урона
     * @param f Количество урона
     * @return Количество урона после поглощения магией
     */
    @Shadow
    protected abstract float getDamageAfterMagicAbsorb(DamageSource damageSource, float f);

    /**
     * Получает количество жёлтых сердец (поглощение).
     * @return количество доп XP
     */
    @Shadow
    public abstract float getAbsorptionAmount();

    /**
     * Устанавливает количество жёлтых сердец (поглощение).
     * @param f количество доп XP
     */
    @Shadow
    public abstract void setAbsorptionAmount(float f);

    /**
     * Что-то пока непонятное.
     * @return Что-то пока непонятное
     */
    @Shadow
    public abstract CombatTracker getCombatTracker();

    /**
     * Устанавливает количество здоровья существа.
     * @param f количество здоровья
     */
    @Shadow
    public abstract void setHealth(float f);

    /**
     * Получает количество здоровья существа.
     * @return количество здоровья
     */
    @Shadow
    public abstract float getHealth();

    /**
     * Константа для каких-то вычислений
     * (взято из стандартного кода)
     */
    private final float constToActuallyHurt1 = 10.0F;

    /**
     * Константа для каких-то вычислений
     * (взято из стандартного кода)
     */
    private final float constToActuallyHurt2 = 3.4028235E37F;

    /**
     * Метод нанесения урона жертве
     * @param damageSource источник урона
     * @param damage количество урона
     * @param ci CallbackInfo
     */
    @Inject(method = "actuallyHurt", at = @At("HEAD"), cancellable = true)
    protected void setDamageTo999(
            final DamageSource damageSource,
            float damage,
            final CallbackInfo ci) {
        if (!this.isInvulnerableTo(damageSource)) {
            damage = this.getDamageAfterArmorAbsorb(damageSource, damage);
            damage = this.getDamageAfterMagicAbsorb(damageSource, damage);
            float g = damage;
            damage = Math.max(damage - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (g - damage));
            float h = g - damage;
            if (h > 0.0F && h < constToActuallyHurt2) {
                Entity var6 = damageSource.getEntity();
                if (var6 instanceof ServerPlayer) {
                    ServerPlayer serverPlayer = (ServerPlayer) var6;
                    serverPlayer.awardStat(
                            Stats.DAMAGE_DEALT_ABSORBED,
                            Math.round(h * constToActuallyHurt1)
                    );
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
