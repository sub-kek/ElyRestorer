package me.subkek.elyrestorer;

import me.subkek.elyrestorer.event.PlayerHandler;
import me.subkek.elyrestorer.event.ServerHandler;
import me.subkek.elyrestorer.type.Task;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ElyRestorer extends JavaPlugin {
    private static ElyRestorer instance;
    public ArrayList<Task> tasks = new ArrayList<>();
    public Logger LOGGER = getLogger();


    public static ElyRestorer getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new ServerHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerHandler(), this);
    }
}
