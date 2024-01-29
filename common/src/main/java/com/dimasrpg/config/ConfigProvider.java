package com.dimasrpg.config;

import com.dimasrpg.DimasRPG;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import static com.dimasrpg.ExampleExpectPlatform.getConfigDirectory;

/**
 * Класс для работы с файлом конфига
 */
public class ConfigProvider {
    public static Path CONFDIR = getConfigDirectory();

    public static boolean checkAndCreateDir(Path path) {
        File folder = path.toFile();

        // Проверяем, существует ли папка, и создаем ее, если необходимо
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                DimasRPG.LOGGER.info("Folder " + path + " created successfully!");
                return true;
            }
            DimasRPG.LOGGER.error("Failed to create folder " + path);
            return false;
        }
        return true;
    }

    public static boolean initConfigTypeFolder(String type, String mod_id) {
        Path mod_configs = ConfigProvider.CONFDIR.resolve(mod_id);
        if (!checkAndCreateDir(mod_configs)) {
            return false;
        }
        Path type_configs = mod_configs.resolve(type);
        return checkAndCreateDir(type_configs);
    }

    public static JsonObject readConfig(File file) {
        try {
            // Переменная для чтения файла
            BufferedReader reader =  new BufferedReader(new FileReader(file));
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            // Закрытие файла для чтения
            reader.close();
            return json;
        } catch (IOException e) {
            // Невозможно открыть файл
            DimasRPG.LOGGER.warn("Could not read config file.");
            e.printStackTrace();
        }
        return null;
    }

    public static void createReadAllConfigs(){
        WeaponConfigFile.init(DimasRPG.MOD_ID);
        ArmorConfigFile.init(DimasRPG.MOD_ID);
        BulletConfigFile.init(DimasRPG.MOD_ID);
    }
}
