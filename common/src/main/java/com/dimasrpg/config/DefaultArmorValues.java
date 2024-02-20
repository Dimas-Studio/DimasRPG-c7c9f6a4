package com.dimasrpg.config;

public final class DefaultArmorValues {
    DefaultArmorValues() { }
    private static final float LEATHER_HELMET_MELEE = 1.0F;
    private static final float LEATHER_HELMET_RANGE = 1.0F;
    private static final float LEATHER_HELMET_MAGIC = 1.0F;
    private static final float LEATHER_CHESTPLATE_MELEE =  3.0F;
    private static final float LEATHER_CHESTPLATE_RANGE =  3.0F;
    private static final float LEATHER_CHESTPLATE_MAGIC =  3.0F;
    private static final float LEATHER_LEGGINGS_MELEE =  2.0F;
    private static final float LEATHER_LEGGINGS_RANGE =  2.0F;
    private static final float LEATHER_LEGGINGS_MAGIC =  2.0F;
    private static final float LEATHER_BOOTS_MELEE =  1.0F;
    private static final float LEATHER_BOOTS_RANGE =  1.0F;
    private static final float LEATHER_BOOTS_MAGIC =  1.0F;
    private static final float CHAINMAIL_HELMET_MELEE =  2.0F;
    private static final float CHAINMAIL_HELMET_RANGE =  2.0F;
    private static final float CHAINMAIL_HELMET_MAGIC =  2.0F;
    private static final float CHAINMAIL_CHESTPLATE_MELEE =  6.0F;
    private static final float CHAINMAIL_CHESTPLATE_RANGE =  6.0F;
    private static final float CHAINMAIL_CHESTPLATE_MAGIC =  6.0F;
    private static final float CHAINMAIL_LEGGINGS_MELEE =  4.0F;
    private static final float CHAINMAIL_LEGGINGS_RANGE =  4.0F;
    private static final float CHAINMAIL_LEGGINGS_MAGIC =  4.0F;
    private static final float CHAINMAIL_BOOTS_MELEE =  1.0F;
    private static final float CHAINMAIL_BOOTS_RANGE =  1.0F;
    private static final float CHAINMAIL_BOOTS_MAGIC =  1.0F;
    private static final float IRON_HELMET_MELEE =  2.0F;
    private static final float IRON_HELMET_RANGE =  2.0F;
    private static final float IRON_HELMET_MAGIC =  2.0F;
    private static final float IRON_CHESTPLATE_MELEE =  6.0F;
    private static final float IRON_CHESTPLATE_RANGE =  6.0F;
    private static final float IRON_CHESTPLATE_MAGIC =  6.0F;
    private static final float IRON_LEGGINGS_MELEE =  5.0F;
    private static final float IRON_LEGGINGS_RANGE =  5.0F;
    private static final float IRON_LEGGINGS_MAGIC =  5.0F;
    private static final float IRON_BOOTS_MELEE =  2.0F;
    private static final float IRON_BOOTS_RANGE =  2.0F;
    private static final float IRON_BOOTS_MAGIC =  2.0F;
    private static final float DIAMOND_HELMET_MELEE =  3.0F;
    private static final float DIAMOND_HELMET_RANGE =  3.0F;
    private static final float DIAMOND_HELMET_MAGIC =  3.0F;
    private static final float DIAMOND_CHESTPLATE_MELEE =  8.0F;
    private static final float DIAMOND_CHESTPLATE_RANGE =  8.0F;
    private static final float DIAMOND_CHESTPLATE_MAGIC =  8.0F;
    private static final float DIAMOND_LEGGINGS_MELEE =  6.0F;
    private static final float DIAMOND_LEGGINGS_RANGE =  6.0F;
    private static final float DIAMOND_LEGGINGS_MAGIC =  6.0F;
    private static final float DIAMOND_BOOTS_MELEE =  3.0F;
    private static final float DIAMOND_BOOTS_RANGE =  3.0F;
    private static final float DIAMOND_BOOTS_MAGIC =  3.0F;
    private static final float GOLDEN_HELMET_MELEE =  2.0F;
    private static final float GOLDEN_HELMET_RANGE =  2.0F;
    private static final float GOLDEN_HELMET_MAGIC =  2.0F;
    private static final float GOLDEN_CHESTPLATE_MELEE =  0.0F;
    private static final float GOLDEN_CHESTPLATE_RANGE =  0.0F;
    private static final float GOLDEN_CHESTPLATE_MAGIC =  0.0F;
    private static final float GOLDEN_LEGGINGS_MELEE =  3.0F;
    private static final float GOLDEN_LEGGINGS_RANGE =  3.0F;
    private static final float GOLDEN_LEGGINGS_MAGIC =  3.0F;
    private static final float GOLDEN_BOOTS_MELEE =  1.0F;
    private static final float GOLDEN_BOOTS_RANGE =  1.0F;
    private static final float GOLDEN_BOOTS_MAGIC =  1.0F;
    private static final float TURTLE_HELMET_MELEE =  2.0F;
    private static final float TURTLE_HELMET_RANGE =  2.0F;
    private static final float TURTLE_HELMET_MAGIC =  2.0F;
    private static final float NETHERITE_HELMET_MELEE =  8.0F;
    private static final float NETHERITE_HELMET_RANGE =  8.0F;
    private static final float NETHERITE_HELMET_MAGIC =  8.0F;
    private static final float NETHERITE_CHESTPLATE_MELEE =  6.0F;
    private static final float NETHERITE_CHESTPLATE_RANGE =  6.0F;
    private static final float NETHERITE_CHESTPLATE_MAGIC =  6.0F;
    private static final float NETHERITE_LEGGINGS_MELEE =  3.0F;
    private static final float NETHERITE_LEGGINGS_RANGE =  3.0F;
    private static final float NETHERITE_LEGGINGS_MAGIC =  3.0F;
    private static final float NETHERITE_BOOTS_MELEE = 3.0F;
    private static final float NETHERITE_BOOTS_RANGE = 3.0F;
    private static final float NETHERITE_BOOTS_MAGIC = 3.0F;
    /**
     * Устанавливает по умолчанию,
     * которые будут использоваться при отсутствии
     * или провале валидации конфиг файла брони.
     */
    public static void init() {
        ArmorConfigValues.put(
                "minecraft:leather_helmet",
                "melee",
                LEATHER_HELMET_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:leather_helmet",
                "range",
                LEATHER_HELMET_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:leather_helmet",
                "magic",
                LEATHER_HELMET_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:leather_chestplate",
                "melee",
                LEATHER_CHESTPLATE_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:leather_chestplate",
                "range",
                LEATHER_CHESTPLATE_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:leather_chestplate",
                "magic",
                LEATHER_CHESTPLATE_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:leather_leggings",
                "melee",
                LEATHER_LEGGINGS_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:leather_leggings",
                "range",
                LEATHER_LEGGINGS_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:leather_leggings",
                "magic",
                LEATHER_LEGGINGS_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:leather_boots",
                "melee",
                LEATHER_BOOTS_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:leather_boots",
                "range",
                LEATHER_BOOTS_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:leather_boots",
                "magic",
                LEATHER_BOOTS_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:chainmail_helmet",
                "melee",
                CHAINMAIL_HELMET_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:chainmail_helmet",
                "range",
                CHAINMAIL_HELMET_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:chainmail_helmet",
                "magic",
                CHAINMAIL_HELMET_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:chainmail_chestplate",
                "melee",
                CHAINMAIL_CHESTPLATE_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:chainmail_chestplate",
                "range",
                CHAINMAIL_CHESTPLATE_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:chainmail_chestplate",
                "magic",
                CHAINMAIL_CHESTPLATE_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:chainmail_leggings",
                "melee",
                CHAINMAIL_LEGGINGS_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:chainmail_leggings",
                "range",
                CHAINMAIL_LEGGINGS_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:chainmail_leggings",
                "magic",
                CHAINMAIL_LEGGINGS_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:chainmail_boots",
                "melee",
                CHAINMAIL_BOOTS_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:chainmail_boots",
                "range",
                CHAINMAIL_BOOTS_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:chainmail_boots",
                "magic",
                CHAINMAIL_BOOTS_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:iron_helmet",
                "melee",
                IRON_HELMET_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:iron_helmet",
                "range",
                IRON_HELMET_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:iron_helmet",
                "magic",
                IRON_HELMET_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:iron_chestplate",
                "melee",
                IRON_CHESTPLATE_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:iron_chestplate",
                "range",
                IRON_CHESTPLATE_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:iron_chestplate",
                "magic",
                IRON_CHESTPLATE_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:iron_leggings",
                "melee",
                IRON_LEGGINGS_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:iron_leggings",
                "range",
                IRON_LEGGINGS_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:iron_leggings",
                "magic",
                IRON_LEGGINGS_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:iron_boots",
                "melee",
                IRON_BOOTS_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:iron_boots",
                "range",
                IRON_BOOTS_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:iron_boots",
                "magic",
                IRON_BOOTS_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:diamond_helmet",
                "melee",
                DIAMOND_HELMET_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:diamond_helmet",
                "range",
                DIAMOND_HELMET_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:diamond_helmet",
                "magic",
                DIAMOND_HELMET_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:diamond_chestplate",
                "melee",
                DIAMOND_CHESTPLATE_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:diamond_chestplate",
                "range",
                DIAMOND_CHESTPLATE_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:diamond_chestplate",
                "magic",
                DIAMOND_CHESTPLATE_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:diamond_leggings",
                "melee",
                DIAMOND_LEGGINGS_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:diamond_leggings",
                "range",
                DIAMOND_LEGGINGS_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:diamond_leggings",
                "magic",
                DIAMOND_LEGGINGS_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:diamond_boots",
                "melee",
                DIAMOND_BOOTS_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:diamond_boots",
                "range",
                DIAMOND_BOOTS_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:diamond_boots",
                "magic",
                DIAMOND_BOOTS_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:golden_helmet",
                "melee",
                GOLDEN_HELMET_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:golden_helmet",
                "range",
                GOLDEN_HELMET_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:golden_helmet",
                "magic",
                GOLDEN_HELMET_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:golden_chestplate",
                "melee",
                GOLDEN_CHESTPLATE_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:golden_chestplate",
                "range",
                GOLDEN_CHESTPLATE_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:golden_chestplate",
                "magic",
                GOLDEN_CHESTPLATE_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:golden_leggings",
                "melee",
                GOLDEN_LEGGINGS_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:golden_leggings",
                "range",
                GOLDEN_LEGGINGS_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:golden_leggings",
                "magic",
                GOLDEN_LEGGINGS_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:golden_boots",
                "melee",
                GOLDEN_BOOTS_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:golden_boots",
                "range",
                GOLDEN_BOOTS_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:golden_boots",
                "magic",
                GOLDEN_BOOTS_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:turtle_helmet",
                "melee",
                TURTLE_HELMET_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:turtle_helmet",
                "range",
                TURTLE_HELMET_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:turtle_helmet",
                "magic",
                TURTLE_HELMET_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:netherite_helmet",
                "melee",
                NETHERITE_HELMET_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:netherite_helmet",
                "range",
                NETHERITE_HELMET_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:netherite_helmet",
                "magic",
                NETHERITE_HELMET_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:netherite_chestplate",
                "melee",
                NETHERITE_CHESTPLATE_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:netherite_chestplate",
                "range",
                NETHERITE_CHESTPLATE_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:netherite_chestplate",
                "magic",
                NETHERITE_CHESTPLATE_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:netherite_leggings",
                "melee",
                NETHERITE_LEGGINGS_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:netherite_leggings",
                "range",
                NETHERITE_LEGGINGS_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:netherite_leggings",
                "magic",
                NETHERITE_LEGGINGS_MAGIC
        );
        ArmorConfigValues.put(
                "minecraft:netherite_boots",
                "melee",
                NETHERITE_BOOTS_MELEE
        );
        ArmorConfigValues.put(
                "minecraft:netherite_boots",
                "range",
                NETHERITE_BOOTS_RANGE
        );
        ArmorConfigValues.put(
                "minecraft:netherite_boots",
                "magic",
                NETHERITE_BOOTS_MAGIC
        );
    }
}
