package me.tomqnto.core.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public enum ServerState {

    MAINTENANCE("<red>Server under maintenance", "§4Server under maintenance §7| §6Only staff can join", 3, true),
    PUBLIC("<green>Server public | Join Now!", "§aServer Online! §7| §eJoin Now!", 1, true);


    private final String kickMessage;
    private final String motd;
    private final int rankLevel;
    private final boolean includeHigherLevels;

    ServerState(String kickMessage, String motd, int rankLevel, boolean includeHigherLevels) {
        this.kickMessage = kickMessage;
        this.motd = motd;
        this.rankLevel = rankLevel;
        this.includeHigherLevels = includeHigherLevels;
    }

    public Component getKickMessage() {
        return MiniMessage.miniMessage().deserialize(kickMessage);
    }

    public String getMotd() {
        return motd;
    }

    public int getRankLevel() {
        return rankLevel;
    }

    public boolean isIncludeHigherLevels() {
        return includeHigherLevels;
    }

}
