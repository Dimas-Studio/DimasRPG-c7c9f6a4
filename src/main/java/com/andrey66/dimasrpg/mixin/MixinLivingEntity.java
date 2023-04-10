package com.andrey66.dimasrpg.mixin;

import com.andrey66.dimasrpg.attribute.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @Shadow(remap = false)
    public abstract void setHealth(float p_21154_);

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
}
