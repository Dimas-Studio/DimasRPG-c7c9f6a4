package com.dimasrpg.config;

import com.dimasrpg.DimasRPG;
import com.google.common.reflect.TypeToken;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Класс для работы с файлом конфига брони.
 */
public final class ArmorConfigFile {
    private ArmorConfigFile() { }


    /**
     * Имя конфига.
     */
    private static final String NAME = "armor";


    /**
     * Gson переменная для конвертации словаря в json строку и наоборот.
     */
    public static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting().create();


    /**
     * Инициализация конфига (вызывается из основного класса мода).
     * @param modId Id мода
     */
    public static void init(final String modId) {
        if (!ConfigProvider.initConfigTypeFolder(NAME, modId)) {
            return;
        }
        boolean oneConfigIsValid = false;
        File[] configFiles = ConfigProvider.getPath(modId, NAME).listFiles();
        if (configFiles == null || configFiles.length == 0) {
            DimasRPG.LOGGER.warn(
                    NAME + " directory is empty, generate default file"
            );
            ArmorConfigValues.setDefaultConfigValues();
            generateDefaultConfig(ConfigProvider.getPath(
                    modId,
                    "minecraft.json")
            );
            return;
        }
        for (File file : configFiles) {
            JsonObject fileContent = ConfigProvider.readConfig(file);
            if (fileContent == null) {
                DimasRPG.LOGGER.warn(file + " is empty!");
                continue;
            }
            if (!validateConfig(fileContent)) {
                DimasRPG.LOGGER.warn(file + " is invalid!");
                continue;
            }
            oneConfigIsValid = true;
            for (Map.Entry<String, JsonElement> entry
                    : fileContent.entrySet()) {
                String name = entry.getKey();
                Type pattern = new TypeToken<Map<String, Float>>() { }
                        .getType();
                Map<String, Float> innerMap =
                        new Gson().fromJson(entry.getValue(), pattern);
                for (String type : innerMap.keySet()) {
                    Float value = innerMap.get(type);
                    ArmorConfigValues.put(name, type, value);
                }
            }
        }
        if (!oneConfigIsValid) {
            DimasRPG.LOGGER.warn(
                    "No valid files are in directory, using default values"
            );
            ArmorConfigValues.setDefaultConfigValues();
        }
    }

    private static Boolean validateConfig(final JsonObject json) {
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            try {
                String name = entry.getKey();
                Type pattern = new TypeToken<Map<String, Float>>() { }
                        .getType();
                Map<String, Float> innerMap =
                        new Gson().fromJson(entry.getValue(), pattern);
                for (String type : innerMap.keySet()) {
                    Float value = innerMap.get(type);
                    ArmorConfigValues.put(name, type, value);
                    if (name == null || name.isEmpty()) {
                        return false;
                    }
                    if (type == null || type.isEmpty()) {
                        return false;
                    }
                    if (value < 0) {
                        return false;
                    }
                    if (innerMap.isEmpty()) {
                        return false;
                    }
                    if (!type.matches("^(magic|range|melee|admin)$")) {
                        return false;
                    }
                    if (!name.matches("^[a-z0-9_-]+:[a-z0-9+_-]+$")) {
                        return false;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    private static void generateDefaultConfig(final File file) {
        // Сортировка переменных по умолчанию для удобства чтения
        Object[] names = ArmorConfigValues.getKeys().toArray();
        Arrays.sort(names); //TODO алфавит!
        // Общий словарь всего конфига
        HashMap<String, HashMap<String, Float>> items = new HashMap<>();

        for (Object name : names) {
            if (((String) name).matches("\\w+:\\w+")) {
                HashMap<String, Float> innerMap = new HashMap<>();
                String[] types = ArmorConfigValues.getTypes((String) name);
                for (String type : types) {
                    Float value
                            = ArmorConfigValues.getValue((String) name, type);
                    innerMap.put(type, value);
                }
                items.put((String) name, innerMap);
            }
        }

        // Формирование json строки для создания по ней файла
        String jsonConfig = GSON.toJson(items);

        try {
            // Создание файла
            FileWriter writer = new FileWriter(file.toPath().toFile());
            writer.write(jsonConfig);
            writer.close();
        } catch (IOException e) {
            // невозможно создать файл
            DimasRPG.LOGGER.warn("Could not save config file.");
            e.printStackTrace();
        }
    }
}
