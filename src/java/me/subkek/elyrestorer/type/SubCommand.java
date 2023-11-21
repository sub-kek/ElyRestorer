package me.subkek.elyrestorer.type;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    public abstract String getName();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract boolean hasPermissions(CommandSender sender);
    public abstract boolean canExecute(CommandSender sender);
    public abstract void perform(CommandSender sender, String[] args);
}