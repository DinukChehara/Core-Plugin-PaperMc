package me.tomqnto.core.listeners;

import me.tomqnto.core.Core;
import me.tomqnto.core.managers.PlayerData;
import me.tomqnto.core.managers.Rank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PlayerData playerData;

    public PlayerJoinListener(PlayerData playerData) {
        this.playerData = playerData;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoinFirstTime(PlayerJoinEvent event){

        if (!(event.getPlayer().hasPlayedBefore()) || playerData.getRank(event.getPlayer())==null){
            playerData.setRank(event.getPlayer(), Rank.DEFAULT);
        }
    }

}
