package com.dimasrpg.config;


import java.util.HashMap;

public final class DefaultArmorValues {
    private static final HashMap<String, HashMap<String, Float>> CONTENT = new HashMap<>(){{
        put("minecraft:leather_helmet", new HashMap<>(){{
            put("melee", 1.0F);
            put("range", 1.0F);
            put("magic", 1.0F);
        }});
        put("minecraft:leather_chestplate", new HashMap<>(){{
            put("melee", 3.0F);
            put("range", 3.0F);
            put("magic", 3.0F);
        }});
        put("minecraft:leather_leggings", new HashMap<>(){{
            put("melee", 2.0F);
            put("range", 2.0F);
            put("magic", 2.0F);
        }});
        put("minecraft:leather_boots", new HashMap<>(){{
            put("melee", 1.0F);
            put("range", 1.0F);
            put("magic", 1.0F);
        }});
        put("minecraft:chainmail_helmet", new HashMap<>(){{
            put("melee", 2.0F);
            put("range", 2.0F);
            put("magic", 2.0F);
        }});
        put("minecraft:chainmail_chestplate", new HashMap<>(){{
            put("melee", 6.0F);
            put("range", 6.0F);
            put("magic", 6.0F);
        }});
        put("minecraft:chainmail_leggings", new HashMap<>(){{
            put("melee", 4.0F);
            put("range", 4.0F);
            put("magic", 4.0F);
        }});
        put("minecraft:chainmail_boots", new HashMap<>(){{
            put("melee", 1.0F);
            put("range", 1.0F);
            put("magic", 1.0F);
        }});
        put("minecraft:iron_helmet", new HashMap<>(){{
            put("melee", 2.0F);
            put("range", 2.0F);
            put("magic", 2.0F);
        }});
        put("minecraft:iron_chestplate", new HashMap<>(){{
            put("melee", 6.0F);
            put("range", 6.0F);
            put("magic", 6.0F);
        }});
        put("minecraft:iron_leggings", new HashMap<>(){{
            put("melee", 5.0F);
            put("range", 5.0F);
            put("magic", 5.0F);
        }});
        put("minecraft:iron_boots", new HashMap<>(){{
            put("melee", 2.0F);
            put("range", 2.0F);
            put("magic", 2.0F);
        }});
        put("minecraft:diamond_helmet", new HashMap<>(){{
            put("melee", 3.0F);
            put("range", 3.0F);
            put("magic", 3.0F);
        }});
        put("minecraft:diamond_chestplate", new HashMap<>(){{
            put("melee", 8.0F);
            put("range", 8.0F);
            put("magic", 8.0F);
        }});
        put("minecraft:diamond_leggings", new HashMap<>(){{
            put("melee", 6.0F);
            put("range", 6.0F);
            put("magic", 6.0F);
        }});
        put("minecraft:diamond_boots", new HashMap<>(){{
            put("melee", 3.0F);
            put("range", 3.0F);
            put("magic", 3.0F);
        }});
        put("minecraft:golden_helmet", new HashMap<>(){{
            put("melee", 2.0F);
            put("range", 2.0F);
            put("magic", 2.0F);
        }});
        put("minecraft:golden_chestplate", new HashMap<>(){{
            put("melee", 0.0F);
            put("range", 0.0F);
            put("magic", 0.0F);
        }});
        put("minecraft:golden_leggings", new HashMap<>(){{
            put("melee", 3.0F);
            put("range", 3.0F);
            put("magic", 3.0F);
        }});
        put("minecraft:golden_boots", new HashMap<>(){{
            put("melee", 1.0F);
            put("range", 1.0F);
            put("magic", 1.0F);
        }});
        put("minecraft:turtle_helmet", new HashMap<>(){{
            put("melee", 2.0F);
            put("range", 2.0F);
            put("magic", 2.0F);
        }});
        put("minecraft:netherite_helmet", new HashMap<>(){{
            put("melee", 8.0F);
            put("range", 8.0F);
            put("magic", 8.0F);
        }});
        put("minecraft:netherite_chestplate", new HashMap<>(){{
            put("melee", 6.0F);
            put("range", 6.0F);
            put("magic", 6.0F);
        }});
        put("minecraft:netherite_leggings", new HashMap<>(){{
            put("melee", 3.0F);
            put("range", 3.0F);
            put("magic", 3.0F);
        }});
        put("minecraft:netherite_boots", new HashMap<>(){{
            put("melee", 3.0F);
            put("range", 3.0F);
            put("magic", 3.0F);
        }});
    }};

    private DefaultArmorValues() {}

    /**
     * Возвращает словарь со значениями по умолчанию, которые будут использоваться при отсутствии
     * или провале валидации конфиг файла брони.
     */
    public static HashMap<String, HashMap<String, Float>> getContent() {
        return CONTENT;
    }
}
