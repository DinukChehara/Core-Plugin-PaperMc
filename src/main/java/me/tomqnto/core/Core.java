package me.tomqnto.core;

import me.tomqnto.core.commands.rank.RemoveRankCommand;
import me.tomqnto.core.commands.rank.SetRankCommand;
import me.tomqnto.core.listeners.PlayerJoinListener;
import me.tomqnto.core.managers.ChatManager;
import me.tomqnto.core.managers.CommandActions;
import me.tomqnto.core.managers.PlayerData;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {


    @Override
    public void onEnable() {

        final PlayerData playerData = new PlayerData();
        final CommandActions commandActions = new CommandActions(playerData);

        getServer().getPluginManager().registerEvents(new ChatManager(playerData), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerData), this);

        getCommand("setrank").setExecutor(new SetRankCommand(commandActions));
        getCommand("removerank").setExecutor(new RemoveRankCommand(commandActions));
    }

    public static Core getInstance(){
        return getPlugin(Core.class);
    }

}
