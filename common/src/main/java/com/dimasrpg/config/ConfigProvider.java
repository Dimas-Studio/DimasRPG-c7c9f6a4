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
 * Класс для работы с файлом конфига.
 */
public final class ConfigProvider {
    private ConfigProvider() { }

    /**
     * Путь до директории с конфигами.
     */
    private static final Path CONFDIR = getConfigDirectory();

    /**
     * Получает путь до файла в папке с конфигами
     * @param modId Id мода
     * @param file имя файла
     * @return Путь до файла
     */
    public static File getPath(final String modId, final String file) {
        return ConfigProvider.CONFDIR.resolve(modId).resolve(file).toFile();
    }

    /**
     * Проверяет директорию на существование и создаёт её в противном случае.
     * @param path путь до директории
     * @return true, если директория существует или её удалось создать
     */
    public static boolean checkAndCreateDir(final Path path) {
        File folder = path.toFile();
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                DimasRPG.LOGGER.info(
                        "Folder " + path + " created successfully!"
                );
                return true;
            }
            DimasRPG.LOGGER.error("Failed to create folder " + path);
            return false;
        }
        return true;
    }

    /**
     * Инициализация директории конфигов с определённым типом.
     * @param type тип конфига
     * @param modId ID мода
     * @return true в случае успешного создания
     */
    public static boolean initConfigTypeFolder(final String type, final String modId) {
        Path modConfigs = ConfigProvider.CONFDIR.resolve(modId);
        if (!checkAndCreateDir(modConfigs)) {
            return false;
        }
        Path typeConfigs = modConfigs.resolve(type);
        return checkAndCreateDir(typeConfigs);
    }


    /**
     * Чтение конфиг файла.
     * @param file файл для чтения
     * @return json объект с содержимым файла или null в случае ошибки
     */
    public static JsonObject readConfig(final File file) {
        try {
            BufferedReader reader =  new BufferedReader(new FileReader(file));
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();
            return json;
        } catch (IOException e) {
            DimasRPG.LOGGER.warn("Could not read config file.");
        }
        return null;
    }

    /**
     * Запуск всех конфиг-файлов на чтение.
     */
    public static void createReadAllConfigs() {
        WeaponConfigFile.init(DimasRPG.MOD_ID);
        ArmorConfigFile.init(DimasRPG.MOD_ID);
        BulletConfigFile.init(DimasRPG.MOD_ID);
    }
}
