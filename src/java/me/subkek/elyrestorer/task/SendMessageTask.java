package me.subkek.elyrestorer.task;

import me.subkek.elyrestorer.type.TaksType;
import me.subkek.elyrestorer.type.Task;
import org.bukkit.command.CommandSender;

public class SendMessageTask extends Task {
    private final CommandSender sender;
    private final String message;

    public SendMessageTask(CommandSender sender, String message) {
        super(TaksType.SEND_MESSAGE);
        this.sender = sender;
        this.message = message;
    }

    @Override
    public void execute() {
        if (sender == null) return;
        sender.sendMessage(message);
    }
}
