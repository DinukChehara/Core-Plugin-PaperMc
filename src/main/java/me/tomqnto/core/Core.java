package me.tomqnto.core;

import me.tomqnto.core.commands.RemoveRankCommand;
import me.tomqnto.core.commands.SetRankCommand;
import me.tomqnto.core.listeners.PlayerJoinListener;
import me.tomqnto.core.managers.ChatManager;
import me.tomqnto.core.managers.PlayerData;
import me.tomqnto.core.managers.Rank;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new ChatManager(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);


        getCommand("setrank").setExecutor(new SetRankCommand());
        getCommand("removerank").setExecutor(new RemoveRankCommand());
    }
}
