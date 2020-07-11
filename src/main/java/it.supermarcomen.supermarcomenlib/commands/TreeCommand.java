package it.supermarcomen.supermarcomenlib.commands;

import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Map;

public abstract class TreeCommand implements Command {

    private Map<String, Command> subCommands;

    public TreeCommand(Map<String, Command> subCommands) {
        this.subCommands = subCommands;
    }

    public abstract void executeDefaultCommand(CommandSender sender, String name, String[] args);

    public abstract void executeUnknownCommand(CommandSender sender, String name, String[] args);

    @Override
    public void executeCommand(CommandSender sender, String name, String[] args) {
        if (args.length == 0) {
            executeDefaultCommand(sender, name, args);
            return;
        }

        Command subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            subCommand.executeCommand(sender, args[0], Arrays.copyOfRange(args, 1, args.length));
        } else {
            executeUnknownCommand(sender, args[0], Arrays.copyOfRange(args, 1, args.length));
        }
    }
}