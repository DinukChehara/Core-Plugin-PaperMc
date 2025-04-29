package me.tomqnto.core.managers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {

    private static HashMap<UUID, Rank> RANKS = new HashMap<>();

    public static Rank getRank(OfflinePlayer player){
        return RANKS.get(player.getUniqueId());
    }

    public static void setRank(OfflinePlayer player, Rank rank){
        RANKS.put(player.getUniqueId(), rank);
    }

    public static void setRank(String playerName, Rank rank){
       setRank(Bukkit.getOfflinePlayer(playerName), rank);
    }

    public static void removeRank(OfflinePlayer player){
        if (hasRank(player))
            RANKS.remove(player.getUniqueId());
    }

    public static boolean hasRank(OfflinePlayer player){
        return RANKS.containsKey(player.getUniqueId());
    }

}
