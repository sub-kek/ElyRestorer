package me.subkek.elyrestorer.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import me.subkek.elyrestorer.ElyRestorer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class JsonUtils {
    private final ElyRestorer plugin = ElyRestorer.getInstance();
    private File dataFile = null;
    private JSONObject jsonData = null;

    public void init() {
        plugin.getDataFolder().mkdir();
        dataFile = new File(plugin.getDataFolder(), "data.json");

        try {
            if (!dataFile.exists()) {
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(Files.newOutputStream(dataFile.toPath()), StandardCharsets.UTF_8));
                pw.println("{}");
                pw.flush();
            }
        } catch (Exception e) { e.printStackTrace(); }

        reloadJson();
    }

    public void reloadJson() {
        try {
            try (InputStreamReader hashReader = new InputStreamReader(Files.newInputStream(dataFile.toPath()), StandardCharsets.UTF_8)) {
                jsonData = (JSONObject) new JSONParser().parse(hashReader);
            } catch (Throwable e) {
                new RuntimeException("Invalid data.json! It will be reset.", e).printStackTrace();
                jsonData = new JSONObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveJson() {
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(Files.newOutputStream(dataFile.toPath()), StandardCharsets.UTF_8));
                Gson g = new GsonBuilder().setPrettyPrinting().create();
                pw.println(g.toJson(new JsonParser().parse(jsonData.toString())));
                pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void put(String key, String value) {
        jsonData.put(key, value);
    }

    public String getString(String key) {
        return jsonData.get(key).toString();
    }

    public boolean has(String key) {
        return jsonData.containsKey(key);
    }
}
