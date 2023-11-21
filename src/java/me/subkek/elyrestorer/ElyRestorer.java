package me.subkek.elyrestorer;

import me.subkek.elyrestorer.command.SkinCommandManager;
import me.subkek.elyrestorer.event.PlayerHandler;
import me.subkek.elyrestorer.event.ServerHandler;
import me.subkek.elyrestorer.json.JsonUtils;
import me.subkek.elyrestorer.lang.FileLanguage;
import me.subkek.elyrestorer.type.AsyncTask;
import me.subkek.elyrestorer.type.Task;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ElyRestorer extends JavaPlugin {
    private static ElyRestorer instance;
    public ArrayList<Task> tasks = new ArrayList<>();
    public ArrayList<AsyncTask> asyncTasks = new ArrayList<>();
    public Logger LOGGER = getLogger();
    public FileLanguage language;
    public JsonUtils jsonUtils;

    public static ElyRestorer getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        jsonUtils = new JsonUtils();
        jsonUtils.init();

        language = new FileLanguage();
        language.init("ru_RU");

        getCommand("elyrestorer").setExecutor(new SkinCommandManager());

        getServer().getPluginManager().registerEvents(new ServerHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerHandler(), this);
    }
}
