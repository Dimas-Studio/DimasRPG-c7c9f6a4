package com.andrey66.dimasrpg.mixin;

import com.andrey66.dimasrpg.attribute.ModAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends MixinEntity{
    @Shadow(remap = false)
    public abstract void setHealth(float p_21154_);
    @Shadow(remap = false)
    protected abstract float getDamageAfterArmorAbsorb(DamageSource p_21162_, float p_21163_);
    @Shadow(remap = false)
    protected abstract float getDamageAfterMagicAbsorb(DamageSource p_21162_, float p_21163_);
    @Shadow(remap = false)
    public abstract float getAbsorptionAmount();
    @Shadow(remap = false)
    public abstract void setAbsorptionAmount(float p_21328_);
    @Shadow(remap = false)
    public abstract float getHealth();
    @Shadow(remap = false)
    public abstract CombatTracker getCombatTracker();

    private LivingEntity toLivingEntity() {
        return (LivingEntity) (Object) this;
    }


    @Inject(remap = false, method = "heal", at = @At("RETURN"))
    private void init(float count, CallbackInfo ci) {
        if (count <= 0) return;
        this.setHealth(1);
    }

    @Inject(
            method = "createLivingAttributes",
            require = 1, allow = 1, at = @At("RETURN"), remap = false)
    private static void addAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue().add(ModAttributes.MAGIC_RES.get());
    }
    @Inject(method = "actuallyHurt", remap = false, at = @At("HEAD"), cancellable = true)
    private void reCalculateDamage(DamageSource p_21240_, float p_21241_, CallbackInfo ci) {
        if (!this.isInvulnerableTo(p_21240_)) {
            if (Minecraft.getInstance().player != null)
                Minecraft.getInstance().player.sendSystemMessage(
                    Component.literal("ATTACK!: " ).withStyle(ChatFormatting.YELLOW));
            p_21241_ = net.minecraftforge.common.ForgeHooks.onLivingHurt(this.toLivingEntity(), p_21240_, p_21241_);
            if (p_21241_ <= 0) ci.cancel();
            p_21241_ = this.getDamageAfterArmorAbsorb(p_21240_, p_21241_);
            p_21241_ = this.getDamageAfterMagicAbsorb(p_21240_, p_21241_);
            float f2 = Math.max(p_21241_ - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (p_21241_ - f2));
            float f = p_21241_ - f2;
            if (f > 0.0F && f < 3.4028235E37F && p_21240_.getEntity() instanceof ServerPlayer) {
                ((ServerPlayer)p_21240_.getEntity()).awardStat(Stats.CUSTOM.get(Stats.DAMAGE_DEALT_ABSORBED), Math.round(f * 10.0F));
            }

            f2 = net.minecraftforge.common.ForgeHooks.onLivingDamage(this.toLivingEntity(), p_21240_, f2);
            if (f2 != 0.0F) {
                float f1 = this.getHealth();
                this.getCombatTracker().recordDamage(p_21240_, f1, f2);
                this.setHealth(f1 - f2); // Forge: moved to fix MC-121048
                this.setAbsorptionAmount(this.getAbsorptionAmount() - f2);
                this.gameEvent(GameEvent.ENTITY_DAMAGE);
            }
        }
    }
}
