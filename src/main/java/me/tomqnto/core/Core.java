package me.tomqnto.core;

import me.tomqnto.core.commands.auth.LoginCommand;
import me.tomqnto.core.commands.auth.RegisterCommand;
import me.tomqnto.core.commands.auth.UnregisterCommand;
import me.tomqnto.core.commands.rank.RemoveRankCommand;
import me.tomqnto.core.commands.rank.SetRankCommand;
import me.tomqnto.core.managers.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {


    @Override
    public void onEnable() {

        final ConfigManager configManager = new ConfigManager(this);
        final PlayerManager playerManager = new PlayerManager(configManager);

        configManager.load();

        getServer().getPluginManager().registerEvents(new ChatManager(playerManager), this);
        getServer().getPluginManager().registerEvents(new AuthManager(playerManager), this);

        getCommand("setrank").setExecutor(new SetRankCommand(playerManager));
        getCommand("removerank").setExecutor(new RemoveRankCommand(playerManager));
        getCommand("register").setExecutor(new RegisterCommand(playerManager));
        getCommand("login").setExecutor(new LoginCommand(playerManager));
        getCommand("unregister").setExecutor(new UnregisterCommand(playerManager));
    }

    public static Core getInstance(){
        return getPlugin(Core.class);
    }

}
