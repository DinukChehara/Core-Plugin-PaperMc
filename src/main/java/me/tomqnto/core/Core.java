package me.tomqnto.core;

import me.tomqnto.core.commands.auth.LoginCommand;
import me.tomqnto.core.commands.auth.RegisterCommand;
import me.tomqnto.core.commands.auth.UnregisterCommand;
import me.tomqnto.core.commands.rank.ResetRank;
import me.tomqnto.core.commands.rank.SetRankCommand;
import me.tomqnto.core.commands.serverState.SetServerStateCommand;
import me.tomqnto.core.listeners.PlayerListeners;
import me.tomqnto.core.managers.*;
import me.tomqnto.core.packets.ProtocolLibHook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Core extends JavaPlugin {


    @Override
    public void onEnable() {

        final ConfigManager configManager = new ConfigManager(this);
        final ServerManager serverManager = new ServerManager(configManager);
        final PermissionManager permissionManager = new PermissionManager();
        final PlayerManager playerManager = new PlayerManager(configManager);
        final TabManager tabManager = new TabManager(playerManager);

        // loading resources
        configManager.load();

        // tasks
        BukkitTask tabList = Bukkit.getScheduler().runTaskTimer(this, tabManager, 0, 3);

        // dependency check
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib")!=null)
            ProtocolLibHook.register(playerManager);

        // other checks
        if (serverManager.getState() == null)
            serverManager.setState(ServerState.PUBLIC);
        else {
            serverManager.setMotd(serverManager.getState().getMotd());
        }

        // registering event listeners
        getServer().getPluginManager().registerEvents(new ChatManager(playerManager), this);
        getServer().getPluginManager().registerEvents(new AuthManager(playerManager), this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(playerManager, serverManager), this);

        // registering commands
        getCommand("setrank").setExecutor(new SetRankCommand(playerManager));
        getCommand("resetrank").setExecutor(new ResetRank(playerManager));
        getCommand("register").setExecutor(new RegisterCommand(playerManager));
        getCommand("login").setExecutor(new LoginCommand(playerManager));
        getCommand("unregister").setExecutor(new UnregisterCommand(playerManager));
        getCommand("setstate").setExecutor(new SetServerStateCommand(serverManager));

    }

    public static Core getInstance(){
        return getPlugin(Core.class);
    }

}
