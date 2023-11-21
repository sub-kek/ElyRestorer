package me.subkek.elyrestorer.event;

import me.subkek.elyrestorer.ElyRestorer;
import me.subkek.elyrestorer.task.GetSkinTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerHandler implements Listener {
    private final ElyRestorer plugin = ElyRestorer.getInstance();

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!plugin.jsonUtils.has(event.getPlayer().getName()))
            plugin.jsonUtils.put(event.getPlayer().getName(), event.getPlayer().getName());

        plugin.asyncTasks.add(new GetSkinTask(event.getPlayer().getName(), event.getPlayer().getName(), false, null));
    }
}
