package me.subkek.elyrestorer.command.subcommands;

import me.subkek.elyrestorer.ElyRestorer;
import me.subkek.elyrestorer.task.GetSkinTask;
import me.subkek.elyrestorer.type.SubCommand;
import me.subkek.elyrestorer.utils.Formatter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand extends SubCommand {
    private final ElyRestorer plugin = ElyRestorer.getInstance();


    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getDescription() {
        return plugin.language.get("set-command-description");
    }

    @Override
    public String getSyntax() {
        return plugin.language.get("set-command-syntax");
    }

    @Override
    public boolean hasPermissions(CommandSender sender) {
        return sender.hasPermission("elyrestorer.set");
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

        try {
            if (args.length >= 3 && sender.hasPermission("elyrestorer.set.others")) {
                plugin.jsonUtils.put(args[1], args[2]);
                plugin.jsonUtils.saveJson();

                plugin.asyncTasks.add(new GetSkinTask(args[1], args[2], true, (Player) sender));
            } else {
                if (!canExecute(sender)) {
                    sender.sendMessage(Formatter.format(plugin.language.get("only-player-error"), true));
                    return;
                }

                plugin.jsonUtils.put(sender.getName(), args[1]);
                plugin.jsonUtils.saveJson();

                plugin.asyncTasks.add(new GetSkinTask(sender.getName(), args[1], true, (Player) sender));
            }
        } catch (Exception ignored) {
            sender.sendMessage(Formatter.format(plugin.language.get("invalid-arguments-error"), true, getSyntax()));
        }
    }
}
