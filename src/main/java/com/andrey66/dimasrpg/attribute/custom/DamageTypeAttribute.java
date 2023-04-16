package com.andrey66.dimasrpg.attribute.custom;

import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.Objects;

// Класс для нового аттрибута типа урона
public class DamageTypeAttribute extends Attribute{
    public DamageTypeAttribute(String name, Double type) {
        super(name, type);
    }

    public static double getTypeFromString(String type) {
        if (Objects.equals(type, "range")) {
            return  2;
        } else if (Objects.equals(type, "magic")) {
            return  3;
        } else {
            return  1;
        }
    }
}