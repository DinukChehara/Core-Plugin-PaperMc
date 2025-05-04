package me.tomqnto.core.managers;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class ServerManager implements Listener {

    private final ConfigManager configManager;

    public ServerManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void setState(ServerState state){
        setMotd(state.getMotd());
        configManager.getConfig().set("server.state", state.name());
        configManager.save();
    }

    public ServerState getState(){
        try{
        return ServerState.valueOf(configManager.getConfig().getString("server.state"));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    public boolean hasState(){
        return configManager.getConfig().getString("server.state") != null;
    }

    public void setMotd(String motd){
        Bukkit.getServer().setMotd(motd);
    }


}
