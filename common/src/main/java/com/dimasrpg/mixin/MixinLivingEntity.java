package com.dimasrpg.mixin;


import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends MixinEntity{
    @Shadow
    public abstract boolean isDeadOrDying();
    @Shadow
    public abstract boolean hasEffect(MobEffect arg);
    @Shadow
    public abstract boolean isSleeping();
    @Shadow
    public abstract void stopSleeping();
    @Shadow
    protected int noActionTime;
    @Shadow
    public abstract boolean isDamageSourceBlocked(DamageSource arg);
    @Shadow
    protected abstract void hurtCurrentlyUsedShield(float f);
    @Shadow
    protected abstract void blockUsingShield(LivingEntity arg);
    @Shadow
    @Final  // Мы это не запомним, но это должно быть над Shadow
    public WalkAnimationState walkAnimation;
    @Shadow
    protected float lastHurt;
    @Shadow
    protected abstract void actuallyHurt(DamageSource arg, float g);
    @Shadow
    public int hurtDuration;
    @Shadow
    public int hurtTime;
    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot arg);
    @Shadow
    protected abstract void hurtHelmet(DamageSource arg, float f);
    @Shadow
    public abstract void setLastHurtByMob(@Nullable LivingEntity arg);
    @Shadow
    protected int lastHurtByPlayerTime;
    @Shadow
    protected Player lastHurtByPlayer;
    @Shadow
    public abstract void knockback(double d, double e, double f);
    @Shadow
    public abstract void indicateDamage(double d, double e);
    @Shadow
    private boolean checkTotemDeathProtection(DamageSource arg){
        throw new AssertionError();
    }
    @Shadow
    protected SoundEvent getDeathSound(){
        throw new AssertionError();
    }
    @Shadow
    protected float getSoundVolume(){
        throw new AssertionError();
    }
    @Shadow
    public abstract float getVoicePitch();
    @Shadow
    protected void playHurtSound(DamageSource arg){
        throw new AssertionError();
    }
    @Shadow
    private DamageSource lastDamageSource;
    @Shadow
    private long lastDamageStamp;
    @Shadow
    public abstract void die(DamageSource arg);
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    protected void hurt(DamageSource damageSource , float f, CallbackInfoReturnable <Boolean> cir) {
        LivingEntity livingentity = (LivingEntity) (Object) this;
        if (this.isInvulnerableTo(damageSource)) {
            cir.setReturnValue(false);
        } else if (this.level().isClientSide) {
            cir.setReturnValue(false);
        } else if (this.isDeadOrDying()) {
            cir.setReturnValue(false);
        } else if (damageSource.is(DamageTypeTags.IS_FIRE) && this.hasEffect(MobEffects.FIRE_RESISTANCE)) {
            cir.setReturnValue(false);
        } else {
            if (this.isSleeping() && !this.level().isClientSide) {
                this.stopSleeping();
            }

            this.noActionTime = 0;
            float g = f;
            boolean bl = false;
            float h = 0.0F;
            if (f > 0.0F && this.isDamageSourceBlocked(damageSource)) {
                this.hurtCurrentlyUsedShield(f);
                h = f;
                f = 0.0F;
                if (!damageSource.is(DamageTypeTags.IS_PROJECTILE)) {
                    Entity entity = damageSource.getDirectEntity();
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity)entity;
                        this.blockUsingShield(livingEntity);
                    }
                }

                bl = true;
            }

            if (damageSource.is(DamageTypeTags.IS_FREEZING) && this.getType().is(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)) { // getType в entity.class
                f *= 5.0F;
            }

            this.walkAnimation.setSpeed(1.5F);
            boolean bl2 = true;
            if ((float)this.invulnerableTime > 10.0F && !damageSource.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
                if (f <= this.lastHurt) {
                    cir.setReturnValue(false);
                }

                this.actuallyHurt(damageSource, f - this.lastHurt);
                this.lastHurt = f;
                bl2 = false;
            } else {
                this.lastHurt = f;
                this.invulnerableTime = 20;
                this.actuallyHurt(damageSource, f);
                this.hurtDuration = 10;
                this.hurtTime = this.hurtDuration;
            }

            if (damageSource.is(DamageTypeTags.DAMAGES_HELMET) && !this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                this.hurtHelmet(damageSource, f);
                f *= 0.75F;
            }

            Entity entity2 = damageSource.getEntity();
            if (entity2 != null) {
                if (entity2 instanceof LivingEntity) {
                    LivingEntity livingEntity2 = (LivingEntity)entity2;
                    if (!damageSource.is(DamageTypeTags.NO_ANGER)) {
                        this.setLastHurtByMob(livingEntity2);
                    }
                }

                if (entity2 instanceof Player) {
                    Player player = (Player)entity2;
                    this.lastHurtByPlayerTime = 100;
                    this.lastHurtByPlayer = player;
                } else if (entity2 instanceof Wolf) {
                    Wolf wolf = (Wolf)entity2;
                    if (wolf.isTame()) {
                        this.lastHurtByPlayerTime = 100;
                        LivingEntity var11 = wolf.getOwner();
                        if (var11 instanceof Player) {
                            Player player2 = (Player)var11;
                            this.lastHurtByPlayer = player2;
                        } else {
                            this.lastHurtByPlayer = null;
                        }
                    }
                }
            }

            if (bl2) {
                if (bl) {
                    this.level().broadcastEntityEvent(livingentity, (byte)29);
                } else {
                    this.level().broadcastDamageEvent(livingentity, damageSource);
                }

                if (!damageSource.is(DamageTypeTags.NO_IMPACT) && (!bl || f > 0.0F)) {
                    this.markHurt();
                }

                if (entity2 != null && !damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
                    double d = entity2.getX() - this.getX();

                    double e;
                    for(e = entity2.getZ() - this.getZ(); d * d + e * e < 1.0E-4; e = (Math.random() - Math.random()) * 0.01) {
                        d = (Math.random() - Math.random()) * 0.01;
                    }

                    this.knockback(0.4000000059604645, d, e);
                    if (!bl) {
                        this.indicateDamage(d, e);
                    }
                }
            }

            if (this.isDeadOrDying()) {
                if (!this.checkTotemDeathProtection(damageSource)) {
                    SoundEvent soundEvent = this.getDeathSound();
                    if (bl2 && soundEvent != null) {
                        this.playSound(soundEvent, this.getSoundVolume(), this.getVoicePitch());
                    }

                    this.die(damageSource);
                }
            } else if (bl2) {
                this.playHurtSound(damageSource);
            }

            boolean bl3 = !bl || f > 0.0F;
            if (bl3) {
                this.lastDamageSource = damageSource;
                this.lastDamageStamp = this.level().getGameTime();
            }

            if (livingentity instanceof ServerPlayer) {
                CriteriaTriggers.ENTITY_HURT_PLAYER.trigger((ServerPlayer)livingentity, damageSource, g, f, bl);
                if (h > 0.0F && h < 3.4028235E37F) {
                    ((ServerPlayer)livingentity).awardStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(h * 10.0F));
                }
            }

            if (entity2 instanceof ServerPlayer) {
                CriteriaTriggers.PLAYER_HURT_ENTITY.trigger((ServerPlayer)entity2, livingentity, damageSource, g, f, bl);
            }

            cir.setReturnValue(bl3);

        }


    }



}
