package me.subkek.elyrestorer.event;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import me.subkek.elyrestorer.ElyRestorer;
import me.subkek.elyrestorer.type.AsyncTask;
import me.subkek.elyrestorer.type.Task;
import me.subkek.elyrestorer.utils.Formatter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ServerHandler implements Listener {
    private final ElyRestorer plugin = ElyRestorer.getInstance();

    @EventHandler(ignoreCancelled = true)
    public void onServerTickStart(ServerTickStartEvent event) {
        for (int i = 0; i < plugin.tasks.size(); i++) {
            Task task = plugin.tasks.get(i);
            task.execute();
            plugin.LOGGER.info(Formatter.format("Successfully executed task #{0}", String.valueOf(task.getTaskId())));
        }

        for (int i = 0; i < plugin.asyncTasks.size(); i++) {
            AsyncTask task = plugin.asyncTasks.get(i);
            task.execute();
        }

        if (!plugin.tasks.isEmpty()) plugin.tasks.clear();
        if (!plugin.asyncTasks.isEmpty()) plugin.asyncTasks.clear();
    }
}
