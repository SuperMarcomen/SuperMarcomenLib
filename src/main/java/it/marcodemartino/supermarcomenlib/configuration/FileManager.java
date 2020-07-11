package it.marcodemartino.supermarcomenlib.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class FileManager {

    private final JavaPlugin plugin;
    private HashMap<String, Config> configs = new HashMap<>();

    public FileManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Get the config by the name(Don't forget the .yml)
     *
     * @param name
     * @return
     */
    public Config getConfig(String name) {
        if (!configs.containsKey(name))
            configs.put(name, new Config(name));

        return configs.get(name);
    }

    public class Config {

        private String name;
        private File file;
        private YamlConfiguration config;

        public Config(String name) {
            this.name = name;
        }

        /**
         * Saves the config as long as the config isn't empty
         *
         * @return
         */
        public Config save() {
            if ((this.config == null) || (this.file == null))
                return this;
            try {
                if (config.getConfigurationSection("").getKeys(true).size() != 0)
                    config.save(this.file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return this;
        }

        /**
         * Saves the config as long as the config isn't empty
         *
         * @return
         */
        public Config createConfig() {
            if (this.file == null)
                file = new File(plugin.getDataFolder(), this.name);

            if (this.config == null)
                return this;

            try {
                if (config.getConfigurationSection("").getKeys(true).size() != 0)
                    config.save(this.file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return this;
        }

        /**
         * Gets the config as a YamlConfiguration
         *
         * @return
         */
        public YamlConfiguration getConfig() {
            if (this.config == null)
                reload();

            return this.config;
        }

        /**
         * Saves the default config (Will overwrite anything in the current config's file)
         * <p>
         * Don't forget to reload after!
         *
         * @return
         */
        public Config saveDefaultConfig() {
            file = new File(plugin.getDataFolder(), this.name);

            plugin.saveResource(this.name, false);

            return this;
        }

        /**
         * Reloads the config
         *
         * @return
         */
        public Config reload() {
            if (file == null)
                this.file = new File(plugin.getDataFolder(), this.name);

            this.config = YamlConfiguration.loadConfiguration(file);

            try {
                Reader defConfigStream = new InputStreamReader(plugin.getResource(this.name), StandardCharsets.UTF_8);

                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                this.config.setDefaults(defConfig);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * Copies the config from the resources to the config's default settings.
         * <p>
         * Force = true ----> Will add any new values from the default file
         * <p>
         * Force = false ---> Will NOT add new values from the default file
         *
         * @param force
         * @return
         */
        public Config copyDefaults(boolean force) {
            getConfig().options().copyDefaults(force);
            return this;
        }

        /**
         * An easy way to set a value into the config
         *
         * @param key
         * @param value
         * @return
         */
        public Config setValue(String key, Object value) {
            getConfig().set(key, value);
            return this;
        }

        /**
         * An easy way to get a value from the config
         *
         * @param key
         * @return
         */
        public Object getValue(String key) {
            return getConfig().get(key);
        }
    }

}
