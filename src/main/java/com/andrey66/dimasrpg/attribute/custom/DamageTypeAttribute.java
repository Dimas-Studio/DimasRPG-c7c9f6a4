package com.andrey66.dimasrpg.attribute.custom;

import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.Objects;

// Класс для нового аттрибута типа урона
public class DamageTypeAttribute extends Attribute{
    private final double defaultValue;
    private final String descriptionId;
    public DamageTypeAttribute(String name, String type) {
        super(name, 1);
        this.descriptionId = name;
        if (Objects.equals(type, "range")) {
            this.defaultValue = 2;
        } else if (Objects.equals(type, "magic")) {
            this.defaultValue = 3;
        } else {
            this.defaultValue = 1;
        }

    }
}