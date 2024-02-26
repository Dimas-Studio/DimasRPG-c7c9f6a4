package com.dimasrpg.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class MixinEntity {

    /**
     * Проверка на невосприимчивость урона.
     * @param damageSource источник урона
     * @return true, если у существа есть иммунитет
     */
    @Shadow
    public abstract boolean isInvulnerableTo(DamageSource damageSource);

    /**
     * Что-то связанное с эвентами из стандартного кода.
     * @param gameEvent что-то связанное с эвентами
     */
    @Shadow
    public abstract void gameEvent(GameEvent gameEvent);
    @Shadow
    public abstract Level level();
    @Shadow
    public abstract EntityType<?> getType();
    @Shadow
    public int invulnerableTime;
    @Shadow
    protected void markHurt() {
        throw new AssertionError();
    }
    @Shadow
    @Final
    public abstract double getX();
    @Final
    @Shadow
    public abstract double getZ();
    @Shadow
    public abstract void playSound(SoundEvent arg, float f, float g);



}
