package me.subkek.elyrestorer.lang;

import me.subkek.elyrestorer.ElyRestorer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class FileLanguage {
    private final ElyRestorer plugin = ElyRestorer.getInstance();

    private final Properties properties = new Properties();

    public void init(String fileName) {
        InputStreamReader inputStreamReader = new InputStreamReader(
                Objects.requireNonNull(
                        plugin.getClass().getClassLoader().getResourceAsStream(fileName + ".properties")), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            properties.load(bufferedReader);
            inputStreamReader.close();
            bufferedReader.close();
        } catch (Exception ignored) {}
    }

    public String get(String key) {
        return properties.getProperty(key).replace("&", "§");
    }
}