package me.tomqnto.core.listeners;

import me.tomqnto.core.managers.PlayerManager;
import me.tomqnto.core.managers.ServerManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PlayerManager playerManager;
    private final ServerManager serverManager;

    public PlayerJoinListener(PlayerManager playerManager, ServerManager serverManager) {
        this.playerManager = playerManager;
        this.serverManager = serverManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        int playerRankLevel = playerManager.getRank(player).getLevel();
            Bukkit.getLogger().info(String.valueOf(serverManager.getState().getRankLevel()));

        if (serverManager.getState().getRankLevel()>playerRankLevel){
            player.kick(Component.text(ChatColor.translateAlternateColorCodes('&',"&4You got kicked\n\n&8----------------------------\n" + serverManager.getState().getMessage())));
            Bukkit.getLogger().info("Kicked player, rank too low");
        }
    }
}
