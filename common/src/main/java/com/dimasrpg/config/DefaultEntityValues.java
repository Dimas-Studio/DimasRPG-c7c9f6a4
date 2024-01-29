package com.dimasrpg.config;


public class DefaultEntityValues {
    public static void init(){

        EntityConfigValues.put("minecraft:lightning_bolt","magic",5.0F);
        EntityConfigValues.put("minecraft:fireball","magic",25.0F);
        EntityConfigValues.put("minecraft:small_fireball","magic",5.0F);
        EntityConfigValues.put("minecraft:wither_skull","magic",6.0F);
        EntityConfigValues.put("minecraft:wither_skull","range",6.0F);
        EntityConfigValues.put("minecraft:shulker_bullet","range",3.0F);
        EntityConfigValues.put("minecraft:shulker_bullet","magic",5.0F);
        EntityConfigValues.put("minecraft:evoker_fangs","melee",5.0F);
        EntityConfigValues.put("minecraft:evoker_fangs","range",5.0F);
        EntityConfigValues.put("minecraft:evoker_fangs","magic",5.0F);
        EntityConfigValues.put("minecraft:command_block_minecart","admin",100.0F);
        EntityConfigValues.put("minecraft:arrow","range",10.0F);
        EntityConfigValues.put("minecraft:spectral_arrow","range",8.0F);
        EntityConfigValues.put("minecraft:spectral_arrow","magic",3.0F);
        EntityConfigValues.put("minecraft:trident","range",8.0F);
        EntityConfigValues.put("minecraft:llama_spit","magic",7.0F);


    }
}

    /*
    *   Здесь описываются стандартные значения для
    *   Если нет файла конфигурации, то будут использоваться эти значения
    *
    *
     */