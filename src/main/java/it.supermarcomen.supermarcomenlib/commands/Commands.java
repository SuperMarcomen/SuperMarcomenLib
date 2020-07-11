package commands;

import org.bukkit.plugin.java.JavaPlugin;

public class Commands {

    public static void registerCommand(JavaPlugin plugin, String name, Command command) {
        plugin.getCommand(name).setExecutor((sender, cmd, label, args) -> {
            command.executeCommand(sender, label, args);
            return true;
        });
    }
}
