package me.tomqnto.core.managers;

import me.tomqnto.core.Core;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

public class PlayerData {

    private final Core plugin = Core.getInstance();
    private final HashMap<UUID, Rank> RANKS = new HashMap<>();
    private final HashMap<UUID, PermissionAttachment> PERMISSIONS = new HashMap<>();
    private final HashMap<UUID, String> LOGINS = new HashMap<>();
    private final HashMap<UUID, Instant> LOGIN_EXPIRATION = new HashMap<>();

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

    public void registerPlayer(Player player, String password){
        LOGINS.put(player.getUniqueId(), password);
        addLoginExpiration(player);
    }

    public void unregisterPlayer(Player player){
        if (isRegistered(player)){
            LOGINS.remove(player.getUniqueId());
            player.kick(Component.text("You were unregistered from the server").color(NamedTextColor.RED));
        }
    }

    public boolean isRegistered(Player player){
        return LOGINS.containsKey(player.getUniqueId());
    }

    public Instant getLoginExpiration(Player player){
        return LOGIN_EXPIRATION.get(player.getUniqueId());
    }

    public boolean isLoginExpired(Player player){
        return Instant.now().isAfter(getLoginExpiration(player));
    }

    public void setLoggedIn(Player player, boolean l){
        PersistentDataContainer container = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey("core", "logged-in");
        container.set(key, PersistentDataType.BOOLEAN, l);
    }

    public boolean isLoggedIn(Player player){
        NamespacedKey key = new NamespacedKey("core", "logged-in");
        return Boolean.TRUE.equals(player.getPersistentDataContainer().get(key, PersistentDataType.BOOLEAN));
    }

    public String getPassword(Player player){
        return LOGINS.get(player.getUniqueId());
    }

    public void addLoginExpiration(Player player){
        LOGIN_EXPIRATION.put(player.getUniqueId(), Instant.now().plus(Duration.ofMinutes(30)));
    }

}
