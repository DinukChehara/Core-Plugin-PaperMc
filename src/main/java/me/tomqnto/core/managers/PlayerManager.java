package me.tomqnto.core.managers;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;

public class PlayerManager {

    private final ConfigManager configManager;

    public PlayerManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public String getConfigPath(OfflinePlayer player, String key){
        return "player." + player.getName() + "." + key;
    }

    public void setRank(OfflinePlayer player, Rank rank){
        configManager.getConfig().set(getConfigPath(player, "rank"), rank.name());
        configManager.save();
    }

    public Rank getRank(OfflinePlayer player){
        if (configManager.getConfig().contains(getConfigPath(player, "rank")))
            return  Rank.valueOf(configManager.getConfig().getString(getConfigPath(player, "rank")));

        setRank(player, Rank.DEFAULT);
        return Rank.DEFAULT;
    }

    public boolean hasRank(OfflinePlayer player){
        return configManager.getConfig().contains(getConfigPath(player, "rank"));
    }


    public void register(Player player, String password){
        configManager.getConfig().set(getConfigPath(player, "password"), password);
        setExpiration(player);
        setPlayerAddress(player);
        setLoggedIn(player, true);
        configManager.save();
    }

    public void unregister(OfflinePlayer player){
        configManager.getConfig().set(getConfigPath(player, "password"), "");
        configManager.save();
    }

    public boolean isRegistered(OfflinePlayer player){
        return configManager.getConfig().contains(getConfigPath(player, "password")) && !(configManager.getConfig().getString(getConfigPath(player, "password")).equals(""));
    }

    public boolean isLoggedIn(OfflinePlayer player){
        return configManager.getConfig().getBoolean(getConfigPath(player, "is-logged-in"));
    }

    public void setLoggedIn(OfflinePlayer player, boolean l){
        configManager.getConfig().set(getConfigPath(player, "is-logged-in"), l);
        configManager.save();
    }

    public String getPassword(OfflinePlayer player){
        return configManager.getConfig().getString(getConfigPath(player, "password"));
    }

    public void setPlayerAddress(Player player){
        configManager.getConfig().set(getConfigPath(player, "address"),
                player.getAddress().getAddress().toString());
        configManager.save();
    }

    public String getPlayerAddress(OfflinePlayer player){
        return configManager.getConfig().getString(getConfigPath(player, "address"));
    }

    public boolean hasAddressChanged(Player player){
        return !getPlayerAddress(player).equals(player.getAddress().getAddress().toString());
    }

    public void setExpiration(OfflinePlayer player){
        configManager.getConfig().set(getConfigPath(player, "expiration-time"), Instant.now().plus(Duration.ofMinutes(10)).toString());
        configManager.save();
    }

    public String getExpiration(OfflinePlayer player){
        return configManager.getConfig().getString(getConfigPath(player, "expiration-time"));
    }

    public boolean hasLoginExpired(OfflinePlayer player){
        return Instant.now().toString().equals(getExpiration(player));
    }

}
