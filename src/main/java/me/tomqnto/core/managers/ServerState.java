package me.tomqnto.core.managers;

import org.bukkit.ChatColor;

public enum ServerState {

    DEVELOPMENT("&cServer under Development", 4, true),
    MAINTENANCE("&cServer under maintenance", 3, true),
    PUBLIC("&aServer public | Join Now!", 1, true);


    private final String message;
    private final int rankLevel;
    private final boolean includeHigherLevels;

    ServerState(String message, int rankLevel, boolean includeHigherLevels) {
        this.message = message;
        this.rankLevel = rankLevel;
        this.includeHigherLevels = includeHigherLevels;
    }

    public String getMessage() {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public int getRankLevel() {
        return rankLevel;
    }

    public boolean isIncludeHigherLevels() {
        return includeHigherLevels;
    }
}
