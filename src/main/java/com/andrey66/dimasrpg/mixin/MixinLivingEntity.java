package com.andrey66.dimasrpg.mixin;

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
    public abstract int getArmorValue();
    @Shadow
    public abstract double getAttributeValue(Attribute p_21134_);

    protected float getDamageAfterArmorAbsorb(DamageSource damageSource, float damage) {
        if (damageSource.getEntity() instanceof LivingEntity){
            Item item = ((LivingEntity) damageSource.getEntity()).getItemInHand(InteractionHand.MAIN_HAND).getItem();
            String itemString = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).toString();
            if (!damageSource.is(DamageTypeTags.BYPASSES_ARMOR)) { //TODO: позможно стоит посмотреть на этот метод
                if (ConfigWeaponsValues.exist(itemString)) {
                    if (Objects.equals(ConfigWeaponsValues.getType(itemString), "melee")) {
                        this.hurtArmor(damageSource, damage);
                        damage = CombatRules.getDamageAfterAbsorb(damage, (float)this.getArmorValue(), (float)this.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
                    } else {
                        return 0;
                    }
                } else {
                    this.hurtArmor(damageSource, damage);
                    damage = CombatRules.getDamageAfterAbsorb(damage, (float)this.getArmorValue(), (float)this.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
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
                .add(ModAttributes.DAMAGE_TYPE.get())
                .add(ModAttributes.MELEE_ARMOR.get())
                .add(ModAttributes.RANGE_ARMOR.get());
    }

    // Метод для изминения вычислений урона
    @Inject(method = "actuallyHurt", at = @At("HEAD"), cancellable = true)
    private void reCalculateDamage(DamageSource damageSource, float damage, CallbackInfo ci) {
        if (!this.isInvulnerableTo(damageSource)) {
            damage = net.minecraftforge.common.ForgeHooks.onLivingHurt(this.toLivingEntity(), damageSource, damage);
            if (damage <= 0) ci.cancel();
            damage = this.getDamageAfterArmorAbsorb(damageSource, damage);
            //damage = this.getDamageAfterMagicAbsorb(damageSource, damage);
            float f2 = Math.max(damage - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (damage - f2));
            float f = damage - f2;
            if (f > 0.0F && f < 3.4028235E37F && damageSource.getEntity() instanceof ServerPlayer) {
                ((ServerPlayer)damageSource.getEntity()).awardStat(Stats.CUSTOM.get(Stats.DAMAGE_DEALT_ABSORBED), Math.round(f * 10.0F));
            }

            f2 = net.minecraftforge.common.ForgeHooks.onLivingDamage(this.toLivingEntity(), damageSource, f2);
            if (f2 != 0.0F) {
                float f1 = this.getHealth();
                this.getCombatTracker().recordDamage(damageSource, f1, f2);
                this.setHealth(f1 - f2); // Forge: moved to fix MC-121048
                this.setAbsorptionAmount(this.getAbsorptionAmount() - f2);
                this.gameEvent(GameEvent.ENTITY_DAMAGE);

                if (Minecraft.getInstance().player != null)
                    Minecraft.getInstance().player.sendSystemMessage(
                            Component.literal(String.valueOf((f1 - f2))).withStyle(ChatFormatting.YELLOW));
            }
        }
        ci.cancel();
    }
}
