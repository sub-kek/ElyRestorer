package me.subkek.elyrestorer.event;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import me.subkek.elyrestorer.ElyRestorer;
import me.subkek.elyrestorer.type.Task;
import me.subkek.elyrestorer.utils.Formatter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ServerHandler implements Listener {
    private final ElyRestorer plugin = ElyRestorer.getInstance();

    @EventHandler(ignoreCancelled = true)
    public void onServerTickStart(ServerTickStartEvent event) {
        if (plugin.tasks.isEmpty()) return;

        for (int i = 0; i < plugin.tasks.size() - 1; i++) {
            Task task = plugin.tasks.get(i);
            task.execute();
            plugin.LOGGER.info(Formatter.format("Successfully executed task #{0}", String.valueOf(task.getTaskId())));
        }

        plugin.tasks.clear();
    }
}
