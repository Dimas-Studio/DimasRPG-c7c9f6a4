package com.dimasrpg.config;


public interface ConfigValuesInterface {
    /**
     * Добавляет значения в словарь.
     * @param name Название предмета/брони
     * @param type Тип урона/брони
     * @param value Количество урона/брони
     */
    private static void put(String name, String type, float value) { }
    private static void clearDefaultConfigValues() { }
    private static float getValue(String name, String type) {
        return 0.0F;
    }
    private static String[] getTypes() {
        return new String[0];
    }
}
