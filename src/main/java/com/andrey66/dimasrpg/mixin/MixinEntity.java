package com.andrey66.dimasrpg.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow(remap = false)
    public abstract boolean isInvulnerableTo(DamageSource p_20122_);
    @Shadow(remap = false)
    public abstract void gameEvent(GameEvent p_146851_);

}