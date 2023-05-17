package com.andrey66.dimasrpg.config;

import com.andrey66.dimasrpg.DimasRPG;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Класс для работы с файлом конфига брони
public class DimasRPGEntityDamageCommonConfig {

    // Gson переменная для конвертации словаря в json строку и наоборот
    public static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting().create();

    // Инициализация конфига (вызывается из основного класса мода)
    public static void initConfig(String mod_id, String config_folder) {

        // Установка базовых значений конфига
        ConfigEntityDamageValues.setDefaultConfigValues();

        // Получение пути дирректории хранения конфигов
        Path configDir = FMLPaths.CONFIGDIR.get();
        Path folderPath = configDir.resolve(config_folder);
        File folder = folderPath.toFile();

        // Проверяем, существует ли папка, и создаем ее, если необходимо
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                DimasRPG.LOGGER.info("Folder " + config_folder + " created successfully!");
            } else {
                DimasRPG.LOGGER.error("Failed to create folder " + config_folder);
            }
        }

        File file = folderPath.resolve(mod_id + "-entity-damage-common.json").toFile();
        if(!file.exists()) {
            // Конфиг файл не найден, создаём новый с значениями по умолчанию
            DimasRPG.LOGGER.info("Could not find entity armor config, generating new default config.");
            saveConfig(file);
        }
        else {
            // Конфиг файл найден, начинаем чтение
            DimasRPG.LOGGER.info("Reading config armor values from file.");
            readConfig(file);
        }
    }

    // Метод чтения конфигов из файла
    private static void readConfig(File file) {
        try {
            // Переменная для чтения файла
            BufferedReader reader =  new BufferedReader(new FileReader(file));
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

            // Валидация json структуры
            if (!validateConfig(json)) {
                DimasRPG.LOGGER.error("Armor config file have wrong syntax, use default config.");
            } else {
                // Очитска значений по умолчанию и последующее заполнение параметрами из файла
                ConfigEntityDamageValues.clearDefaultConfigValues();

                for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                    String name = entry.getKey();
                    Type pattern = new TypeToken<Map<String, Float>>() {}.getType();
                    Map<String, Float> innerMap = new Gson().fromJson(entry.getValue(), pattern);
                    for (Map.Entry<String, Float> entry1 : innerMap.entrySet()) {
                        String type = entry1.getKey();
                        Float value = entry1.getValue();
                        ConfigEntityDamageValues.put(name, new HashMap<>(){{put(type, value);}});
                    }
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
    private static void saveConfig(File file) {

        // Сортировка переменных по умолчанию для удобства чтения
        Object[] names = ConfigEntityDamageValues.getKeys().toArray();
        Arrays.sort(names);

        // Общий словарь всего конфига
        HashMap<String, HashMap<String, Float>> items = new HashMap<>();

        for(Object name : names) {
            if(((String) name).matches("\\w+:\\w+") || (name.equals("player"))) {    // Провкрка на: "minecraft:creeper"
                HashMap<String, Float> innerMap = new HashMap<>();
                for (Map.Entry<String, Float> entry : Objects.requireNonNull(ConfigEntityDamageValues.getTypes((String) name)).entrySet()){
                    String type = entry.getKey();
                    Float value = entry.getValue();
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
            DimasRPG.LOGGER.warn("Could not save armor config file.");
            e.printStackTrace();
        }
    }

    // Метод для проверки структуры json переменной
    private static Boolean validateConfig(JsonObject json) {
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            try {
                String name = entry.getKey();
                if (name == null || name.isEmpty()) {
                    return false;
                }
                if (!(name.matches("^[a-z0-9_-]+:[a-z0-9+_-]+$") || name.equals("player"))) {
                    return false;
                }

                Type pattern = new TypeToken<Map<String, Float>>() {}.getType();
                Map<String, Float> innerMap = new Gson().fromJson(entry.getValue(), pattern);
                for (Map.Entry<String, Float> entry1 : innerMap.entrySet()) {
                    String type = entry1.getKey();
                    if (type == null || type.isEmpty()) {
                        return false;
                    }
                }
            } catch (Exception e){
                return false;
            }
        }
        return true;
    }
}
