package me.subkek.elyrestorer;

import me.subkek.elyrestorer.task.ApplySkinTask;
import me.subkek.elyrestorer.type.SkinProperty;
import me.subkek.elyrestorer.utils.Formatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SkinGetter {
    private final ElyRestorer plugin = ElyRestorer.getInstance();

    public void addToQueue(String playerName, String skinName) {
        Runnable runnable = () -> {
            JSONObject jsonObject = getJSONResponse(Formatter.format("http://skinsystem.ely.by/textures/signed/{0}?proxy=true", skinName));

            JSONObject properties = (JSONObject) ((JSONArray) jsonObject.get("properties")).get(0);

            String value = properties.get("value").toString();
            String signature = properties.get("signature").toString();

            SkinProperty skinProperty = new SkinProperty(value, signature);

            plugin.tasks.add(new ApplySkinTask(playerName, skinProperty));
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private JSONObject getJSONResponse(String link) {
        try {
            String response = getTextResponse(link, true);
            if (response != null) {
                return (JSONObject) new JSONParser().parse(response);
            }
            return null;
        } catch (ParseException e) {
            return null;
        }
    }

    private String getTextResponse(String link, boolean joinLines) {
        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            connection.setDefaultUseCaches(false);
            connection.addRequestProperty("User-Agent", "Mozilla/5.0");
            connection.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            connection.addRequestProperty("Pragma", "no-cache");
            Collector<CharSequence, ?, String> c = joinLines ? Collectors.joining() : Collectors.joining("\n");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String reply = reader.lines().collect(c);
            reader.close();
            return reply;
        } catch (IOException e) {
            return null;
        }
    }
}