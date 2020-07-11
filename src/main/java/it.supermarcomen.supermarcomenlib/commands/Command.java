package it.supermarcomen.supermarcomenlib.commands;

import org.bukkit.command.CommandSender;

public interface Command {
    void executeCommand(CommandSender sender, String name, String[] args);
}
