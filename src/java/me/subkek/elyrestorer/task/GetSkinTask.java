package me.subkek.elyrestorer.task;

import me.subkek.elyrestorer.ElyRestorer;
import me.subkek.elyrestorer.type.AsyncTask;
import me.subkek.elyrestorer.type.SkinProperty;
import me.subkek.elyrestorer.type.TaksType;
import me.subkek.elyrestorer.utils.Formatter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

public class GetSkinTask extends AsyncTask {
    private final ElyRestorer plugin = ElyRestorer.getInstance();

    private String playerName, skinName;
    private boolean needCallback;
    private CommandSender callbackTo;

    public GetSkinTask(String playerName, String skinName, boolean needCallback, CommandSender callbackTo) {
        super(TaksType.GET_SKIN);
        this.playerName = playerName;
        this.skinName = skinName;
        this.needCallback = needCallback;
        this.callbackTo = callbackTo;
    }

    @Override
    public void execute() {
        Runnable runnable = () -> {
            JSONObject jsonObject = getJSONResponse(Formatter.format("http://skinsystem.ely.by/textures/signed/{0}?proxy=true", skinName));

            if (jsonObject == null) {
                if (needCallback) plugin.tasks.add(new SendMessageTask(callbackTo, Formatter.format(plugin.language.get("skin-not-found"), true)));
                return;
            }

            JSONObject properties = (JSONObject) ((JSONArray) jsonObject.get("properties")).get(0);

            String value = properties.get("value").toString();
            String signature = properties.get("signature").toString();

            SkinProperty skinProperty = new SkinProperty(value, signature);

            plugin.tasks.add(new ApplySkinTask(playerName, skinProperty, needCallback, callbackTo));
        };

        start(runnable);
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
