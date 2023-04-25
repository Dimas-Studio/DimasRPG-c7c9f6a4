package com.andrey66.dimasrpg.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.nio.file.Path;

import com.andrey66.dimasrpg.DimasRPG;
import com.google.common.reflect.TypeToken;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraftforge.fml.loading.FMLPaths;

// Класс для работы с файлом конфига оружий
public class DimasRPGWeaponsCommonConfig {
    // Переменная для хранения пути к файлу конфига
    private static File file;

    // Gson переменная для конвертации словаря в json строку и наоборот
    public static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting().create();

    // Инициализация конфига (вызывается из основного класса мода)
    public static void initConfig(String mod_id) {

        // Установка базовых значений конфига
        ConfigWeaponsValues.setDefaultConfigValues();

        // Получение пути дирректории хранения конфигов
        // TODO: Добавить папку для конфигов мода
        Path configDir = FMLPaths.CONFIGDIR.get();
        file = configDir.resolve(mod_id + "-weapons-common.json").toFile();

        if(!file.exists()) {
            // Конфиг файл не найден, создаём новый с значениями по умолчанию
            // TODO: комментарии в json файл
            DimasRPG.LOGGER.info("Could not find weapon config, generating new default config.");
            saveConfig();
        }
        else {
            // Конфиг файл найден, начинаем чтение
            DimasRPG.LOGGER.info("Reading config values from file.");
            readConfig();
        }
    }

    // Метод чтения конфигов из файла
    private static void readConfig() {
        try {
            // Переменная для чтения файла
            BufferedReader reader =  new BufferedReader(new FileReader(file));
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

            // Валидация json структуры
            if (!validateConfig(json)) {
                DimasRPG.LOGGER.error("Weapon config file have wrong syntax, use default config.");
            } else {
                // Очитска значений по умолчанию и последующее заполнение параметрами из файла
                ConfigWeaponsValues.clearDefaultConfigValues();

                for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                    String name = entry.getKey();
                    Type pattern = new TypeToken<Map<String, Float>>() {
                    }.getType();
                    Map<String, Float> innerMap = new Gson().fromJson(entry.getValue(), pattern);
                    String type = innerMap.keySet().iterator().next();
                    Float value = innerMap.get(type);
                    ConfigWeaponsValues.put(name, type, value);
                }
            }
            // Закрытие файла для чтения
            reader.close();

        } catch (IOException e) {
            // Невозможно открыть файл
            DimasRPG.LOGGER.warn("Could not read config file.");
            e.printStackTrace();
        }
    }

    // Метод сохранения файла с конфигами по умолчанию
    private static void saveConfig() {

        // Сортировка переменных по умолчанию для удобства чтения
        Object[] names = ConfigWeaponsValues.getKeys().toArray();
        Arrays.sort(names);

        // Общий словарь всего конфига
        HashMap<String, HashMap<String, Float>> items = new HashMap<>();

        for(Object name : names) {
            if(((String) name).matches("\\w+:\\w+")) {    // Провкрка на: "minecraft:creeper"
                HashMap<String, Float> innerMap = new HashMap<>();
                String type = ConfigWeaponsValues.getType((String) name);
                Float value = ConfigWeaponsValues.getValue((String) name);
                innerMap.put(type, value);
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

    // Метод для проверки структуры json переменной
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
                if (!type.matches("^(magic|range|melee)$")) {
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
}
