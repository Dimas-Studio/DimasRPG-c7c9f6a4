package com.andrey66.dimasrpg.mixin;

import com.andrey66.dimasrpg.Debug;
import com.andrey66.dimasrpg.DimasRPG;
import com.andrey66.dimasrpg.Math.ModCombatRules;
import com.andrey66.dimasrpg.attribute.ModAttributes;
import com.andrey66.dimasrpg.config.ConfigWeaponsValues;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
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
    public abstract int getArmorValue();
    @Shadow
    public abstract double getAttributeValue(Attribute p_21134_);

    @Shadow @Final public static int DEATH_DURATION;

    protected float getDamageAfterArmorAbsorb(DamageSource damageSource, float damage, String damageType) {
        if (damageSource.getEntity() instanceof LivingEntity){
            // Проверка типа урона на проникновение через броню
            if (!damageSource.is(DamageTypeTags.BYPASSES_ARMOR)) {
                switch (damageType) {
                    case ("melee") -> {
                        this.hurtArmor(damageSource, damage);
                        damage = ModCombatRules.getDamageAfterAbsorb(damage, (float) this.getAttributeValue(ModAttributes.MELEE_ARMOR.get()));
                    }
                    case ("range") -> {
                        this.hurtArmor(damageSource, damage);
                        damage = ModCombatRules.getDamageAfterAbsorb(damage, (float) this.getAttributeValue(ModAttributes.RANGE_ARMOR.get()));
                    }
                    case ("magic") -> {
                        this.hurtArmor(damageSource, damage);
                        damage = ModCombatRules.getDamageAfterAbsorb(damage, (float) this.getAttributeValue(ModAttributes.MAGIC_DAMAGE.get()));
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
                .add(ModAttributes.RANGE_DAMAGE.get());
    }

    // Метод для изминения вычислений урона
    @Inject(method = "actuallyHurt", at = @At("HEAD"), cancellable = true)
    private void reCalculateDamage(DamageSource damageSource, float damage, CallbackInfo ci) {//TODO: Учесть логику LivingEntity.hurt()
        if (!this.isInvulnerableTo(damageSource)) {
            String damageType = "";
            float bonusDamage = 0;
            boolean haveDistance = !Objects.equals(damageSource.getEntity(), damageSource.getDirectEntity());
            try {
                if (damageSource.getEntity() != null){
                    LivingEntity livingEntity = (LivingEntity) damageSource.getEntity();
                    Item item = livingEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem(); // TODO: Проверить на существах без рук
                    String itemString = (Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item))).toString();


                    // Проверка на существование оружия в конфиге
                    if (ConfigWeaponsValues.exist(itemString)) {
                        // Урон нанесён в ближнем бою
                        if (!haveDistance) {
                            switch (Objects.requireNonNull(ConfigWeaponsValues.getType(itemString))) {
                                case ("melee") -> {
                                    damage = (float) livingEntity.getAttributeValue(ModAttributes.MELEE_DAMAGE.get());
                                    damageType = "melee";
                                }
                                case ("range") -> {
                                    damage = (float) livingEntity.getAttributeValue(ModAttributes.MELEE_DAMAGE.get());
                                    damageType = "melee";
                                }
                                case ("magic") -> {
                                    damage = (float) livingEntity.getAttributeValue(ModAttributes.MAGIC_DAMAGE.get());
                                    damageType = "magic";
                                }
                            }
                        // Урон нанесён в дальнем бою
                        } else {
                            damage = 0;
                            String itemType = ConfigWeaponsValues.getType(itemString);
                            String projectileName = String.valueOf(damageSource.getDirectEntity());
                            String projectileType = "magic"; //TODO: Получать из конфига
                            float projectileDamage = 2; //TODO: Получать из конфига
                            if (Objects.equals(itemType, projectileType)) {
                                damage += projectileDamage;
                                switch (Objects.requireNonNull(itemType)) {
                                    case ("melee") -> {
                                        damage += (float) livingEntity.getAttributeValue(ModAttributes.MELEE_DAMAGE.get());
                                        damageType = "melee";
                                    }
                                    case ("range") -> {
                                        damage += (float) livingEntity.getAttributeValue(ModAttributes.RANGE_DAMAGE.get());
                                        damageType = "range";
                                    }
                                    case ("magic") -> {
                                        damage += (float) livingEntity.getAttributeValue(ModAttributes.MAGIC_DAMAGE.get());
                                        damageType = "magic";
                                    }
                                }
                            } else {
                                // Расчёт основного урона
                                switch (Objects.requireNonNull(itemType)) {
                                    case ("melee") -> {
                                        damage = (float) livingEntity.getAttributeValue(ModAttributes.MELEE_DAMAGE.get());
                                        damageType = "melee";
                                    }
                                    case ("range") -> {
                                        damage = (float) livingEntity.getAttributeValue(ModAttributes.RANGE_DAMAGE.get());
                                        damageType = "range";
                                    }
                                    case ("magic") -> {
                                        damage = (float) livingEntity.getAttributeValue(ModAttributes.MAGIC_DAMAGE.get());
                                        damageType = "magic";
                                    }
                                }
                                // Расчёт урона от снаряда
                                bonusDamage = this.getDamageAfterArmorAbsorb(damageSource, projectileDamage, projectileType);
                            }
                        }
                    } else {
                        if (item instanceof TieredItem || item instanceof TridentItem) {
                            Debug.printToChat(itemString + " " + Component.translatable("key.dimasrpg.weapon_alert").getString(), ChatFormatting.YELLOW);
                        }
                        damage = (float) this.getAttributeValue(ModAttributes.MELEE_DAMAGE.get());
                        damageType = "melee";
                    }
                }
            } catch (Exception e) {
                DimasRPG.LOGGER.warn("ForgeRegistries.ITEMS.getKey(item)) is null.");
                e.printStackTrace();
            }

            // Запуск эвента
            damage = net.minecraftforge.common.ForgeHooks.onLivingHurt(this.toLivingEntity(), damageSource, damage);

            if (damage <= 0) ci.cancel();

            // Поглощение урона бронёй
            damage = this.getDamageAfterArmorAbsorb(damageSource, damage, damageType);

            // Добавление урона снаряда, если его тип отличается от предмета
            Debug.printToChat(damage + "+" + bonusDamage);
            damage += bonusDamage;

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
}
