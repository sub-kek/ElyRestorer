package me.subkek.elyrestorer.command.subcommands;

import me.subkek.elyrestorer.ElyRestorer;
import me.subkek.elyrestorer.task.GetSkinTask;
import me.subkek.elyrestorer.type.SubCommand;
import me.subkek.elyrestorer.utils.Formatter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UpdateCommand extends SubCommand {
    private final ElyRestorer plugin = ElyRestorer.getInstance();

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return plugin.language.get("update-command-description");
    }

    @Override
    public String getSyntax() {
        return plugin.language.get("update-command-syntax");
    }

    @Override
    public boolean hasPermissions(CommandSender sender) {
        return sender.hasPermission("elyrestorer.update");
    }

    @Override
    public boolean canExecute(CommandSender sender) {
        return (sender instanceof Player);
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!hasPermissions(sender)) {
            sender.sendMessage(Formatter.format(plugin.language.get("no-permission-error"), true));
            return;
        }
        if (!canExecute(sender)) {
            sender.sendMessage(Formatter.format(plugin.language.get("only-player-error"), true));
            return;
        }

        try {
            if (args.length >= 2 & sender.hasPermission("elyrestorer.update.others")) {
                plugin.jsonUtils.put(args[1], args[1]);
                plugin.jsonUtils.saveJson();

                plugin.asyncTasks.add(new GetSkinTask(args[1], plugin.jsonUtils.getString(args[1]), true, (Player) sender));
            } else {
                if (!canExecute(sender)) {
                    sender.sendMessage(Formatter.format(plugin.language.get("only-player-error"), true));
                    return;
                }

                plugin.jsonUtils.put(sender.getName(), sender.getName());
                plugin.jsonUtils.saveJson();

                plugin.asyncTasks.add(new GetSkinTask(sender.getName(), plugin.jsonUtils.getString(sender.getName()), true, (Player) sender));
            }
        } catch (Exception ignored) {
            sender.sendMessage(Formatter.format(plugin.language.get("invalid-arguments-error"), true, getSyntax()));
        }
    }
}
