package com.andrey66.dimasrpg.mixin;

import com.andrey66.dimasrpg.Debug;
import com.andrey66.dimasrpg.DimasRPG;
import com.andrey66.dimasrpg.Math.ModCombatRules;
import com.andrey66.dimasrpg.attribute.ModAttributes;
import com.andrey66.dimasrpg.config.*;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;


@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends MixinEntity{
    @Shadow
    public abstract void setHealth(float p_21154_);
    @Shadow
    public abstract float getAbsorptionAmount();
    @Shadow
    public abstract void setAbsorptionAmount(float p_21328_);
    @Shadow
    public abstract float getHealth();
    @Shadow
    public abstract CombatTracker getCombatTracker();
    @Shadow
    protected abstract void hurtArmor(DamageSource p_21122_, float p_21123_);
    @Shadow
    public abstract double getAttributeValue(Attribute p_21134_);
    @Shadow
    protected void hurtCurrentlyUsedShield(float p_21316_) {}
    @Shadow
    protected void blockUsingShield(LivingEntity p_21200_) {}
    @Shadow
    protected float lastHurt;
    @Shadow
    protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {}
    @Shadow
    protected void hurtHelmet(DamageSource p_147213_, float p_147214_) {}
    @Shadow
    protected Player lastHurtByPlayer;
    @Shadow
    protected int lastHurtByPlayerTime;
    @Shadow
    private DamageSource lastDamageSource;
    @Shadow
    private long lastDamageStamp;
    @Shadow
    private boolean checkTotemDeathProtection(DamageSource p_21263_) {
        throw new AssertionError();
    }
    @Shadow
    protected SoundEvent getDeathSound() {
        throw new AssertionError();
    }
    @Shadow
    protected float getSoundVolume() {
        throw new AssertionError();
    }
    @Shadow
    protected void playHurtSound(DamageSource p_21160_) { }

    @Shadow protected abstract void blockedByShield(LivingEntity p_21246_);

    @Shadow protected abstract void triggerItemUseEffects(ItemStack p_21138_, int p_21139_);

    public float getMeleeArmorValue() {
        return (float) this.getAttributeValue(ModAttributes.MELEE_ARMOR.get());
    }
    public float getRangeArmorValue() {
        return (float) this.getAttributeValue(ModAttributes.RANGE_ARMOR.get());
    }
    public float getMagicArmorValue() {
        return (float) this.getAttributeValue(ModAttributes.MAGIC_ARMOR.get());
    }
    private boolean blockedDamage = false;

    protected float getDamageAfterArmorAbsorb(DamageSource damageSource, float damage, String damageType, float attribute_defence) {
        if (damageSource.getEntity() instanceof LivingEntity){
            // Проверка типа урона на проникновение через броню
            if (!damageSource.is(DamageTypeTags.BYPASSES_ARMOR)) {
                switch (damageType) {
                    case ("melee") -> {
                        this.hurtArmor(damageSource, damage);
                        damage = ModCombatRules.getDamageAfterAbsorb(damage, this.getMeleeArmorValue(), 0, attribute_defence);
                    }
                    case ("range") -> {
                        this.hurtArmor(damageSource, damage);
                        damage = ModCombatRules.getDamageAfterAbsorb(damage, this.getRangeArmorValue(), 0, attribute_defence);
                    }
                    case ("magic") -> {
                        this.hurtArmor(damageSource, damage);
                        damage = ModCombatRules.getDamageAfterAbsorb(damage, this.getMagicArmorValue(), 0, attribute_defence);
                    }
                }
            }
        }
        return damage;
    }

    // Привидение объекта из класса MixinLivingEntity к классу LivingEntity
    private LivingEntity toLivingEntity() {
        return (LivingEntity) (Object) this;
    }

    // Метод для добавления своего аттрибута ко всем LivingEntity
    @Inject(
            method = "createLivingAttributes",
            require = 1, allow = 1, at = @At("RETURN"))
    private static void addAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue()
                .add(ModAttributes.MAGIC_ARMOR.get())
                .add(ModAttributes.MELEE_ARMOR.get())
                .add(ModAttributes.RANGE_ARMOR.get())
                .add(ModAttributes.MAGIC_DAMAGE.get())
                .add(ModAttributes.MELEE_DAMAGE.get())
                .add(ModAttributes.RANGE_DAMAGE.get())
                .add(ModAttributes.ADMIN_DAMAGE.get());
    }

    // Метод для изминения вычислений урона
    @Inject(method = "actuallyHurt", at = @At("HEAD"), cancellable = true)
    protected void reCalculateDamage(DamageSource damageSource, float damage, CallbackInfo ci) { // TODO: Учесть логику LivingEntity.hurt()
        if (!this.isInvulnerableTo(damageSource)) {
            int a = Debug.a();
            float vanilaDamage = 0;
            Entity entity = damageSource.getEntity();
            Entity directEntity = damageSource.getDirectEntity();
            String weaponDamageType = "";
            String projectileDamageType = "";
            String mobDamageType = "";
            float projectileDamage = 0;
            float mobDamage = 0;
            float weaponDamage = 0;
            float entityMeleeDamageBonus = 0;
            float entityRangeDamageBonus = 0;
            float entityMagicDamageBonus = 0;
            float entityMeleeArmorBonus = 0;
            float entityRangeArmorBonus = 0;
            float entityMagicArmorBonus = 0;
            float meleeDamage = 0;
            float rangeDamage = 0;
            float magicDamage = 0;
            float adminDamage = 0;
            boolean uniqueDamage = false;
            boolean haveDistance = !Objects.equals(entity, directEntity);

            if (damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
                uniqueDamage = true;
                meleeDamage = damage;
            }

            try {
                Debug.printToChat(damageSource.toString());
                if (!uniqueDamage) {
                    if (entity != null) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        LivingEntity livingEntity_this = (LivingEntity) (Object) this;

                        if (entity instanceof Mob && ConfigMobDamageValues.exist(entity.getEncodeId())) {
                            String mobName = entity.getEncodeId();
                            mobDamageType = ConfigMobDamageValues.getType(mobName);
                            mobDamage = Objects.requireNonNull(ConfigMobDamageValues.getValue(mobName));
                        }
                        if (livingEntity_this instanceof Mob) {
                            entityMeleeArmorBonus = ConfigEntityArmorValues.getValue(livingEntity_this.getEncodeId(), "melee");
                            entityRangeArmorBonus = ConfigEntityArmorValues.getValue(livingEntity_this.getEncodeId(), "range");
                            entityMagicArmorBonus = ConfigEntityArmorValues.getValue(livingEntity_this.getEncodeId(), "magic");
                        }
                        if (livingEntity_this instanceof Player) {
                            entityMeleeArmorBonus = ConfigEntityArmorValues.getValue("player", "melee");
                            entityRangeArmorBonus = ConfigEntityArmorValues.getValue("player", "range");
                            entityMagicArmorBonus = ConfigEntityArmorValues.getValue("player", "magic");
                        }
                        if (entity instanceof Mob) {
                            entityMeleeDamageBonus = ConfigEntityDamageValues.getValue(livingEntity_this.getEncodeId(), "melee");
                            entityRangeDamageBonus = ConfigEntityDamageValues.getValue(livingEntity_this.getEncodeId(), "range");
                            entityMagicDamageBonus = ConfigEntityDamageValues.getValue(livingEntity_this.getEncodeId(), "magic");
                        }
                        if (entity instanceof Player) {
                            entityMeleeDamageBonus = ConfigEntityDamageValues.getValue("player", "melee");
                            entityRangeDamageBonus = ConfigEntityDamageValues.getValue("player", "range");
                            entityMagicDamageBonus = ConfigEntityDamageValues.getValue("player", "magic");
                        }

                        Item item = livingEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem();

                        String itemString = (Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item))).toString();
                        // Проверка на существование оружия в конфиге
                        if (ConfigWeaponsValues.exist(itemString)) {
                            // Урон нанесён в ближнем бою
                            if (!haveDistance) {
                                switch (Objects.requireNonNull(ConfigWeaponsValues.getType(itemString))) {
                                    case ("melee"), ("range") -> {
                                        weaponDamage = (float) livingEntity.getAttributeValue(ModAttributes.MELEE_DAMAGE.get());
                                        weaponDamageType = "melee";
                                    }
                                    case ("magic") -> {
                                        weaponDamage = (float) livingEntity.getAttributeValue(ModAttributes.MAGIC_DAMAGE.get());
                                        weaponDamageType = "magic";
                                    }
                                    case ("admin") -> {
                                        weaponDamage = (float) livingEntity.getAttributeValue(ModAttributes.ADMIN_DAMAGE.get());
                                        weaponDamageType = "admin";
                                    }
                                }
                                // Урон нанесён в дальнем бою снарядом из конфига
                            } else if (ConfigProjectileValues.exist(
                                    String.valueOf(Objects.requireNonNull(directEntity).getEncodeId())
                            )) {
                                weaponDamage = 0;
                                weaponDamageType = ConfigWeaponsValues.getType(itemString);
                                String projectileName = Objects.requireNonNull(directEntity).getEncodeId();
                                projectileDamageType = ConfigProjectileValues.getType(projectileName);
                                projectileDamage = ConfigProjectileValues.getValue(projectileName);
                                switch (Objects.requireNonNull(weaponDamageType)) {
                                    case ("melee") -> {
                                        weaponDamage += (float) livingEntity.getAttributeValue(ModAttributes.MELEE_DAMAGE.get());
                                        weaponDamageType = "melee";
                                    }
                                    case ("range") -> {
                                        weaponDamage += (float) livingEntity.getAttributeValue(ModAttributes.RANGE_DAMAGE.get());
                                        weaponDamageType = "range";
                                    }
                                    case ("magic") -> {
                                        weaponDamage += (float) livingEntity.getAttributeValue(ModAttributes.MAGIC_DAMAGE.get());
                                        weaponDamageType = "magic";
                                    }
                                }
                            } else {
                                // Снаряда в конфиге нет
                                String projectileName = directEntity.getEncodeId();
                                Debug.printToChat(projectileName + " " + Component.translatable("key.dimasrpg.projectile_alert").getString(), ChatFormatting.YELLOW);
                                weaponDamage = (float) this.getAttributeValue(ModAttributes.RANGE_DAMAGE.get());
                                weaponDamageType = "range";
                            }
                        } else {
                            if (item instanceof TieredItem || item instanceof TridentItem) {
                                Debug.printToChat(itemString + " " + Component.translatable("key.dimasrpg.weapon_alert").getString(), ChatFormatting.YELLOW);
                            }
                            weaponDamage = (float) this.getAttributeValue(ModAttributes.MELEE_DAMAGE.get());
                            weaponDamageType = "melee";
                        }
                    } else {
                        vanilaDamage += damage;
                    }
                }
            } catch (Exception e) {
                DimasRPG.LOGGER.warn("ForgeRegistries.ITEMS.getKey(item)) is null.");
                e.printStackTrace();
            }

            meleeDamage += Objects.equals(weaponDamageType, "melee") ? weaponDamage : 0;
            meleeDamage += Objects.equals(mobDamageType, "melee") ? mobDamage : 0;
            meleeDamage += Objects.equals(projectileDamageType, "melee") ? projectileDamage : 0;
            rangeDamage += Objects.equals(weaponDamageType, "range") ? weaponDamage : 0;
            rangeDamage += Objects.equals(mobDamageType, "range") ? mobDamage : 0;
            rangeDamage += Objects.equals(projectileDamageType, "range") ? projectileDamage : 0;
            magicDamage += Objects.equals(weaponDamageType, "magic") ? weaponDamage : 0;
            magicDamage += Objects.equals(mobDamageType, "magic") ? mobDamage : 0;
            magicDamage += Objects.equals(projectileDamageType, "magic") ? projectileDamage : 0;

            adminDamage += Objects.equals(weaponDamageType, "admin") ? weaponDamage : 0;

            meleeDamage *= (1 + (entityMeleeDamageBonus/100));
            rangeDamage *= (1 + (entityRangeDamageBonus/100));
            magicDamage *= (1 + (entityMagicDamageBonus/100));

            Debug.printToChat("Оружие [" + weaponDamageType + "]: " + weaponDamage);
            Debug.printToChat("Снаряд [" + projectileDamageType + "]: " + projectileDamage);
            Debug.printToChat("Моб [" + mobDamageType + "]: " + mobDamage);

            if (this.blockedDamage){
                rangeDamage = 0;
                meleeDamage *= 0.5;
                magicDamage *= 0.9;
            }

            meleeDamage = this.getDamageAfterArmorAbsorb(damageSource, meleeDamage, "melee", entityMeleeArmorBonus);
            rangeDamage = this.getDamageAfterArmorAbsorb(damageSource, rangeDamage, "range", entityRangeArmorBonus);
            magicDamage = this.getDamageAfterArmorAbsorb(damageSource, magicDamage, "magic", entityMagicArmorBonus);

            Debug.printToChat("Melee: " + meleeDamage);
            Debug.printToChat("Range: " + rangeDamage);
            Debug.printToChat("Magic: " + magicDamage);
            Debug.printToChat("Vanila: " + vanilaDamage);
            Debug.printToChat("Admin: " + adminDamage);

            damage = meleeDamage + rangeDamage + magicDamage + vanilaDamage + adminDamage;

            this.blockedDamage = true;

            // Запуск эвента
            damage = net.minecraftforge.common.ForgeHooks.onLivingHurt(this.toLivingEntity(), damageSource, damage);

            if (damage <= 0) ci.cancel();

            // Поглощение урона дополнительным здоровьем
            float absorptionAmount = Math.max(damage - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (damage - absorptionAmount));
            float f = damage - absorptionAmount;
            if (f > 0.0F && f < 3.4028235E37F && damageSource.getEntity() instanceof ServerPlayer) {
                ((ServerPlayer)damageSource.getEntity()).awardStat(Stats.CUSTOM.get(Stats.DAMAGE_DEALT_ABSORBED), Math.round(f * 10.0F));
            }

            absorptionAmount = net.minecraftforge.common.ForgeHooks.onLivingDamage(this.toLivingEntity(), damageSource, absorptionAmount);
            if (absorptionAmount != 0.0F) {
                float f1 = this.getHealth();
                this.getCombatTracker().recordDamage(damageSource, f1, absorptionAmount);
                this.setHealth(f1 - absorptionAmount); // Forge: moved to fix MC-121048
                this.setAbsorptionAmount(this.getAbsorptionAmount() - absorptionAmount);
                this.gameEvent(GameEvent.ENTITY_DAMAGE);
            }
        }
        ci.cancel();
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void hurt(DamageSource p_21016_, float p_21017_, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (!net.minecraftforge.common.ForgeHooks.onLivingAttack(livingEntity, p_21016_, p_21017_)) cir.setReturnValue(false);
        if (this.isInvulnerableTo(p_21016_)) {
            cir.setReturnValue(false);
        } else if (livingEntity.level.isClientSide) {
            cir.setReturnValue(false);
        } else if (livingEntity.isDeadOrDying()) {
            cir.setReturnValue(false);
        } else if (p_21016_.is(DamageTypeTags.IS_FIRE) && livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE)) {
            cir.setReturnValue(false);
        } else {
            if (livingEntity.isSleeping() && !livingEntity.level.isClientSide) {
                livingEntity.stopSleeping();
            }

            livingEntity.setNoActionTime(0);
            float f = p_21017_;
            boolean flag = false;
            float f1 = 0.0F;
            if (p_21017_ > 0.0F && livingEntity.isDamageSourceBlocked(p_21016_)) {
                net.minecraftforge.event.entity.living.ShieldBlockEvent ev = net.minecraftforge.common.ForgeHooks.onShieldBlock(livingEntity, p_21016_, p_21017_);
                if(!ev.isCanceled()) {
                    if(ev.shieldTakesDamage()) this.hurtCurrentlyUsedShield(p_21017_);
                    f1 = ev.getBlockedDamage();
                    this.blockedDamage = true;
                    p_21017_ -= ev.getBlockedDamage();
                    if (!p_21016_.is(DamageTypeTags.IS_PROJECTILE)) {
                        Entity entity = p_21016_.getDirectEntity();
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingentity1 = (LivingEntity)entity;
                            this.blockUsingShield(livingentity1);
                        }
                    }

                    flag = true;
                }

                if (p_21016_.is(DamageTypeTags.IS_FREEZING) && livingEntity.getType().is(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)) {
                    p_21017_ *= 5.0F;
                }
            }

            livingEntity.walkAnimation.setSpeed(1.5F);
            boolean flag1 = true;
            if ((float)livingEntity.invulnerableTime > 10.0F && !p_21016_.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
                if (p_21017_ <= this.lastHurt) {
                    cir.setReturnValue(false);
                }

                this.actuallyHurt(p_21016_, p_21017_ - this.lastHurt);
                this.lastHurt = p_21017_;
                flag1 = false;
            } else {
                this.lastHurt = p_21017_;
                livingEntity.invulnerableTime = 20;
                this.actuallyHurt(p_21016_, p_21017_);
                livingEntity.hurtDuration = 10;
                livingEntity.hurtTime = livingEntity.hurtDuration;
            }

            if (p_21016_.is(DamageTypeTags.DAMAGES_HELMET) && !livingEntity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                this.hurtHelmet(p_21016_, p_21017_);
                p_21017_ *= 0.75F;
            }

            Entity entity1 = p_21016_.getEntity();
            if (entity1 != null) {
                if (entity1 instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity)entity1;
                    if (!p_21016_.is(DamageTypeTags.NO_ANGER)) {
                        livingEntity.setLastHurtByMob(livingentity1);
                    }
                }

                if (entity1 instanceof Player) {
                    Player player1 = (Player)entity1;
                    this.lastHurtByPlayerTime = 100;
                    this.lastHurtByPlayer = player1;
                } else if (entity1 instanceof net.minecraft.world.entity.TamableAnimal tamableEntity) {
                    if (tamableEntity.isTame()) {
                        this.lastHurtByPlayerTime = 100;
                        LivingEntity livingentity2 = tamableEntity.getOwner();
                        if (livingentity2 instanceof Player) {
                            Player player = (Player)livingentity2;
                            this.lastHurtByPlayer = player;
                        } else {
                            this.lastHurtByPlayer = null;
                        }
                    }
                }
            }

            if (flag1) {
                if (flag) {
                    livingEntity.level.broadcastEntityEvent(livingEntity, (byte)29);
                } else {
                    livingEntity.level.broadcastDamageEvent(livingEntity, p_21016_);
                }

                if (!p_21016_.is(DamageTypeTags.NO_IMPACT) && (!flag || p_21017_ > 0.0F)) {
                    this.markHurt();
                }

                if (entity1 != null && !p_21016_.is(DamageTypeTags.IS_EXPLOSION)) {
                    double d0 = entity1.getX() - livingEntity.getX();

                    double d1;
                    for(d1 = entity1.getZ() - livingEntity.getZ(); d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
                        d0 = (Math.random() - Math.random()) * 0.01D;
                    }

                    livingEntity.knockback((double)0.4F, d0, d1);
                    if (!flag) {
                        livingEntity.indicateDamage(d0, d1);
                    }
                }
            }

            if (livingEntity.isDeadOrDying()) {
                if (!this.checkTotemDeathProtection(p_21016_)) {
                    SoundEvent soundevent = this.getDeathSound();
                    if (flag1 && soundevent != null) {
                        livingEntity.playSound(soundevent, this.getSoundVolume(), livingEntity.getVoicePitch());
                    }

                    livingEntity.die(p_21016_);
                }
            } else if (flag1) {
                this.playHurtSound(p_21016_);
            }

            boolean flag2 = !flag || p_21017_ > 0.0F;
            if (flag2) {
                this.lastDamageSource = p_21016_;
                this.lastDamageStamp = livingEntity.level.getGameTime();
            }

            if (livingEntity instanceof ServerPlayer) {
                CriteriaTriggers.ENTITY_HURT_PLAYER.trigger((ServerPlayer)livingEntity, p_21016_, f, p_21017_, flag);
                if (f1 > 0.0F && f1 < 3.4028235E37F) {
                    ((ServerPlayer)livingEntity).awardStat(Stats.CUSTOM.get(Stats.DAMAGE_BLOCKED_BY_SHIELD), Math.round(f1 * 10.0F));
                }
            }

            if (entity1 instanceof ServerPlayer) {
                CriteriaTriggers.PLAYER_HURT_ENTITY.trigger((ServerPlayer)entity1, livingEntity, p_21016_, f, p_21017_, flag);
            }

            cir.setReturnValue(flag2);
        }
    }
}

