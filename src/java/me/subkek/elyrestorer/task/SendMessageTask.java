package me.subkek.elyrestorer.task;

import me.subkek.elyrestorer.type.TaksType;
import me.subkek.elyrestorer.type.Task;
import org.bukkit.entity.Player;

public class SendMessageTask extends Task {
    private final Player player;
    private final String message;

    public SendMessageTask(Player player, String message) {
        super(TaksType.SEND_MESSAGE);
        this.player = player;
        this.message = message;
    }

    @Override
    public void execute() {
        if (player == null || !player.isOnline()) return;
        player.sendMessage(message);
    }
}
