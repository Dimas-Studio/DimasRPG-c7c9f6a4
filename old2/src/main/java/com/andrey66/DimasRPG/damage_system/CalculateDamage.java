package com.andrey66.DimasRPG.damage_system;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class CalculateDamage {
    private final EntityType attacking_type;
    private final Entity attacking;
    private final EntityType true_attacking_type;
    private EntityType victim_type;
    private final LivingEntity victim;
    private DamageSource source;
    private final float amount;

    public CalculateDamage(LivingEntity victim, DamageSource damageSource, float amount) {
        this.attacking_type = Objects.requireNonNull(damageSource.getEntity()).getType();
        this.true_attacking_type = Objects.requireNonNull(damageSource.getDirectEntity()).getType();
        this.victim_type = victim.getType();
        this.victim = victim;
        this.source = damageSource;
        if(damageSource.getEntity() != null) {
            this.attacking = damageSource.getEntity();
        } else {
            this.attacking = null;
        }



        this.amount = amount;
    }

    protected float CalculatePhysicalDamage(){
        float damage = CalculatePhysicalDamageAttack();
        float armor = CalculatePhysicalDamageArmor();
        return getDamage(damage, armor);
    }

    protected float CalculateMagicDamage(){
        return 0;
    }

    protected float CalculateRangerDamage(){
        return 0;
    }
    protected float CalculatePhysicalDamageAttack(){
        float damage = amount;
        if (attacking instanceof Player){
            damage = 1;
            damage += new DefaultItemDamage().get(attacking.getHandSlots().iterator().next().getItem());
            damage *= ((Player) attacking).getAttackStrengthScale(0);
            boolean critical_flag = attacking.fallDistance > 0.0f
                    && !attacking.isOnGround()
                    && !((Player) attacking).onClimbable()
                    && !attacking.isInWater()
                    && !((Player) attacking).hasEffect(MobEffects.BLINDNESS)
                    && !attacking.isPassenger();
            if(critical_flag){
                damage = (float)Math.ceil(damage * 1.5);
            }
            return damage;
        }
        if (attacking instanceof LivingEntity) {
            //TODO Else: у враждебных существ урон зависит от сложности
            damage = 0;
            damage += new DefaultEntityDamage().get((LivingEntity) attacking);
            damage += new DefaultItemDamage().get(attacking.getHandSlots().iterator().next().getItem());

        }

        return damage;
    }

    protected float CalculatePhysicalDamageArmor() {
        float armor;
        armor = victim.getArmorValue();
        return armor;
    }

    protected float getDamage(float damage, float armor){
        float percent = ((float)Math.ceil((100 * armor * 0.06)/(1 + (armor * 0.06))));
        float percentage = percent / 100;

        return (1-percentage) * damage;
    }
    public float getFinalDamage() {
        float physicalDamage;
        float magicDamage;
        if(attacking_type == true_attacking_type){
            physicalDamage = CalculatePhysicalDamage();
        } else {
            physicalDamage = CalculateRangerDamage();
        }
        magicDamage = CalculateMagicDamage();
        if (attacking.getEncodeId() != null && Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal(attacking.getEncodeId()));
        }
        return physicalDamage + magicDamage;
    }
}
