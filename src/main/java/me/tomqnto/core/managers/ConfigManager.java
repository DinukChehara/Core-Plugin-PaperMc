package me.tomqnto.core.managers;

import me.tomqnto.core.Core;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final Core plugin;

    File file;
    FileConfiguration config;

    public ConfigManager(Core plugin) {
        this.plugin = plugin;
    }

    public void load(){

        file = new File(plugin.getInstance().getDataFolder(), "config.yml");

        if (!file.exists()){
            plugin.getInstance().saveResource("config.yml", false);
        }

        config = plugin.getInstance().getConfig();

        try{
            config.load(file);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void save(){
        try {
            config.save(file);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig(){
        return plugin.getConfig();
    }

}
