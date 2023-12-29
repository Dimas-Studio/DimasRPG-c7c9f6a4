package com.dimasrpg.config;


public interface ConfigValuesInterface {
    public static void put(String name, String type, float value) {}
    public static void clearDefaultConfigValues() {}
    public static float getValue(String name, String type) {return 0.0F;}
    public static String[] getTypes() {return new String[0];}
}
