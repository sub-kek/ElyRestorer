package me.subkek.elyrestorer.event;

import me.subkek.elyrestorer.ElyRestorer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerHandler implements Listener {
    private final ElyRestorer plugin = ElyRestorer.getInstance();

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.skinGetter.addToQueue(event.getPlayer().getName(), event.getPlayer().getName());
    }
}
