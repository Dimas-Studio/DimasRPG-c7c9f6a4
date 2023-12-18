package com.dimasrpg.config;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

import com.dimasrpg.DimasRPG;
import com.google.gson.*;
import com.google.common.reflect.TypeToken;

import static com.dimasrpg.ExampleExpectPlatform.getConfigDirectory;


/**
 * Класс для работы с файлом конфига оружий
 */
public class WeaponConfigFile {
    public static String NAME = "weapon";
    // Gson переменная для конвертации словаря в json строку и наоборот
    public static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting().create();

    // Инициализация конфига (вызывается из основного класса мода)
    public static void init(String mod_id) {
        if (!ConfigProvider.initConfigTypeFolder(NAME, mod_id)){
            return;
        }
        boolean one_config_is_valid = false;
        File[] config_files = ConfigProvider.CONFDIR.resolve(mod_id).resolve(NAME).toFile().listFiles();
        if (config_files == null || config_files.length == 0) {
            DimasRPG.LOGGER.warn(NAME + "directory is empty, generate default file");
            WeaponConfigValues.setDefaultConfigValues();
            generateDefaultConfig(ConfigProvider.CONFDIR.resolve(mod_id).resolve(NAME).resolve("minecraft.json").toFile());
            return;
        }
        for (File file : config_files) {
            JsonObject file_content = ConfigProvider.readConfig(file);
            if (file_content == null){
                DimasRPG.LOGGER.warn(file + " is empty!");
                continue;
            }
            if (!validateConfig(file_content)){
                DimasRPG.LOGGER.warn(file + "is invalid!");
                continue;
            }
            one_config_is_valid = true;
            for (Map.Entry<String, JsonElement> entry : file_content.entrySet()) {
                String name = entry.getKey();
                Type pattern = new TypeToken<Map<String, Float>>() {}.getType();
                Map<String, Float> innerMap = new Gson().fromJson(entry.getValue(), pattern);
                String type = innerMap.keySet().iterator().next();
                Float value = innerMap.get(type);
                WeaponConfigValues.put(name, type, value);
            }
        }
        if (!one_config_is_valid) {
            DimasRPG.LOGGER.warn("No valid files are in directory, using default values");
            WeaponConfigValues.setDefaultConfigValues();
        }
    }

    private static Boolean validateConfig(JsonObject json) {
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            try {
                String name = entry.getKey();
                Type pattern = new TypeToken<Map<String, Float>>() {}.getType();
                Map<String, Float> innerMap = new Gson().fromJson(entry.getValue(), pattern);
                String type = innerMap.keySet().iterator().next();
                Float value = innerMap.get(type);

                if (name == null || name.isEmpty()) {
                    return false;
                }
                if (type == null || type.isEmpty()) {
                    return false;
                }
                if (value < 0) {
                    return false;
                }
                if (innerMap.size() != 1) {
                    return false;
                }
                if (!type.matches("^(magic|range|melee|admin)$")) {
                    return false;
                }
                if (!name.matches("^[a-z0-9_-]+:[a-z0-9+_-]+$")) {
                    return false;
                }
            } catch (Exception e){
                return false;
            }
        }
        return true;
    }

    private static void generateDefaultConfig(File file) {
        // Сортировка переменных по умолчанию для удобства чтения
        Object[] names = WeaponConfigValues.getKeys().toArray();
        Arrays.sort(names);
        System.out.println(names);
        // Общий словарь всего конфига
        HashMap<String, HashMap<String, Float>> items = new HashMap<>();

        for(Object name : names) {
            if(((String) name).matches("\\w+:\\w+")) {    // Провкрка на: "minecraft:creeper"
                HashMap<String, Float> innerMap = new HashMap<>();
                String[] types = WeaponConfigValues.getTypes((String) name);
                for (String type : types) {
                    Float value = WeaponConfigValues.getValue((String) name, type);
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