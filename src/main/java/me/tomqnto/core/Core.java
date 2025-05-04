package me.tomqnto.core;

import me.tomqnto.core.commands.auth.LoginCommand;
import me.tomqnto.core.commands.auth.RegisterCommand;
import me.tomqnto.core.commands.auth.UnregisterCommand;
import me.tomqnto.core.commands.rank.RemoveRankCommand;
import me.tomqnto.core.commands.rank.SetRankCommand;
import me.tomqnto.core.commands.serverState.SetServerStateCommand;
import me.tomqnto.core.listeners.PlayerLoginListener;
import me.tomqnto.core.managers.*;
import me.tomqnto.core.packets.Packets;
import org.bukkit.plugin.java.JavaPlugin;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.ProtocolLibrary;

public final class Core extends JavaPlugin {


    @Override
    public void onEnable() {

        final ConfigManager configManager = new ConfigManager(this);
        final PlayerManager playerManager = new PlayerManager(configManager);
        final ServerManager serverManager = new ServerManager(configManager);

        configManager.load();

        if (serverManager.getState() == null)
            serverManager.setState(ServerState.PUBLIC);
        else {
            serverManager.setMotd(serverManager.getState().getMotd());
        }

        getServer().getPluginManager().registerEvents(new ChatManager(playerManager), this);
        getServer().getPluginManager().registerEvents(new AuthManager(playerManager), this);
        getServer().getPluginManager().registerEvents(new PlayerLoginListener(playerManager, serverManager), this);

        getCommand("setrank").setExecutor(new SetRankCommand(playerManager));
        getCommand("removerank").setExecutor(new RemoveRankCommand(playerManager));
        getCommand("register").setExecutor(new RegisterCommand(playerManager));
        getCommand("login").setExecutor(new LoginCommand(playerManager));
        getCommand("unregister").setExecutor(new UnregisterCommand(playerManager));
        getCommand("setstate").setExecutor(new SetServerStateCommand(serverManager));
    }

    public static Core getInstance(){
        return getPlugin(Core.class);
    }

}
