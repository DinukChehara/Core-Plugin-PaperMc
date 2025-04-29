package me.tomqnto.core.managers;

import me.tomqnto.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {

    public final Core plugin = Core.getInstance();
    private final HashMap<UUID, Rank> RANKS = new HashMap<>();
    private final HashMap<UUID, PermissionAttachment> PERMISSIONS = new HashMap<>();

    public Rank getRank(OfflinePlayer player){
        return RANKS.get(player.getUniqueId());
    }

    public void setRank(OfflinePlayer player, Rank rank){
        RANKS.put(player.getUniqueId(), rank);
    }

    public void setRank(String playerName, Rank rank){
       setRank(Bukkit.getOfflinePlayer(playerName), rank);
    }

    public void removeRank(OfflinePlayer player){
        if (hasRank(player)){
            RANKS.remove(player.getUniqueId());
        }
    }

    public boolean hasRank(OfflinePlayer player){
        return RANKS.containsKey(player.getUniqueId());
    }

    public void addPermission(OfflinePlayer player, String permission){
        UUID playerUUID = Bukkit.getPlayerUniqueId(player.getName());

        if (!(PERMISSIONS.containsKey(playerUUID)))
            PERMISSIONS.put(playerUUID, player.getPlayer().addAttachment(this.plugin));

        PERMISSIONS.get(playerUUID).setPermission(permission, true);

    }

    public void removePermission(OfflinePlayer player, String permission){

        UUID playerUUID = Bukkit.getPlayerUniqueId(player.getName());

        if (PERMISSIONS.containsKey(player)){
            PERMISSIONS.get(playerUUID).unsetPermission(permission);
        }

    }

}
