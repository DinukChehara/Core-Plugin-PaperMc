package me.tomqnto.core.managers;

import org.bukkit.OfflinePlayer;

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


    public void register(OfflinePlayer player, String password){
        configManager.getConfig().set(getConfigPath(player, "password"), password);
        setLoggedIn(player, true);
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
    

}
