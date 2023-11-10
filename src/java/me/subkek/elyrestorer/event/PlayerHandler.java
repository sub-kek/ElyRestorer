package me.subkek.elyrestorer.event;

import me.subkek.elyrestorer.ElyRestorer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerHandler implements Listener {
    private final ElyRestorer plugin = ElyRestorer.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

    }
}
