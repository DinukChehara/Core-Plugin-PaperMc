package me.tomqnto.core.managers;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ServerManager implements Listener {

    private final ConfigManager configManager;

    public ServerManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void setState(ServerState state){
        Bukkit.getServer().setMotd(state.getMessage());
        configManager.getConfig().set("server.state", state.name());
        configManager.save();
    }

    public ServerState getState(){
        return ServerState.valueOf(configManager.getConfig().getString("server.state"));
    }


}
