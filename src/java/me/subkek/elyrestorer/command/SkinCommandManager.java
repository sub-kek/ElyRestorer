package me.subkek.elyrestorer.command;

import me.subkek.elyrestorer.ElyRestorer;
import me.subkek.elyrestorer.command.subcommands.ResetCommand;
import me.subkek.elyrestorer.command.subcommands.SetCommand;
import me.subkek.elyrestorer.command.subcommands.UpdateCommand;
import me.subkek.elyrestorer.type.SubCommand;
import me.subkek.elyrestorer.utils.Formatter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SkinCommandManager implements CommandExecutor, TabCompleter {
    private final ElyRestorer plugin = ElyRestorer.getInstance();
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();
    public SkinCommandManager() {
        subCommands.add(new SetCommand());
        subCommands.add(new ResetCommand());
        subCommands.add(new UpdateCommand());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            for (SubCommand subCommand : subCommands) {
                if (args[0].equalsIgnoreCase(subCommand.getName())) {
                    subCommand.perform(sender, args);
                    return true;
                }
            }
        } else {
            sender.sendMessage(plugin.language.get("help-header"));
            for (SubCommand subCommand : subCommands) {
                sender.sendMessage(Formatter.format(plugin.language.get("help-command"), subCommand.getSyntax(), subCommand.getDescription()));
            }
            sender.sendMessage(plugin.language.get("help-footer"));

            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> returnList = new ArrayList<>();
        switch (args.length) {
            case 1 -> {
                for (SubCommand subCommand : subCommands) {
                    if (subCommand.hasPermissions(sender)) returnList.add(subCommand.getName());
                }
            }
            case 2 -> {
                switch (args[0]) {
                    case "set", "reset", "update" -> {
                        for (Player player : plugin.getServer().getOnlinePlayers()) {
                            String name = player.getName();
                            if (name.toLowerCase().contains(args[1].toLowerCase())) returnList.add(name);
                        }
                    }
                }
            }
            case 3 -> {
                switch (args[0]) {
                    case "set" -> returnList.add("<skin>");
                }
            }
        }
        return returnList;
    }
}