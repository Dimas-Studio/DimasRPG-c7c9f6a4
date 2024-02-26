package com.dimasrpg.attribute.custom;

import net.minecraft.world.entity.ai.attributes.Attribute;


/**
 * Класс для атрибута рукопашного урона.
 */
public class MeleeDamageAttribute extends Attribute {

    /**
     * Созздаёт атрибут рукопашного урона.
     * @param string id атрибута
     * @param d значение урона по умолчанию
     */
    public MeleeDamageAttribute(final String string, final double d) {
        super(string, d);
    }
}
