package me.tomqnto.core.managers;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;


public enum Rank {

    OWNER("Owner", NamedTextColor.GOLD, NamedTextColor.YELLOW, true, false, 5),
    ADMIN("Admin", NamedTextColor.DARK_RED, NamedTextColor.RED, true, false, 4),
    MOD("Mod", NamedTextColor.DARK_GREEN, NamedTextColor.GREEN, true, false, 3),
    HELPER("Helper", NamedTextColor.DARK_AQUA, NamedTextColor.AQUA, true, false, 2),
    YOUTUBE("§cYou§rtube", NamedTextColor.WHITE, NamedTextColor.WHITE, true, false, 1),
    DEFAULT("Default", NamedTextColor.GRAY, NamedTextColor.WHITE, false, false, 1);


    private final String displayName;
    private final NamedTextColor nameColor;
    private final NamedTextColor chatColor;
    private final boolean tagBold, italic;
    private final int level;

    Rank(String displayName, NamedTextColor nameColor, NamedTextColor chatColor, boolean tagBold, boolean italic, int level) {
        this.displayName = displayName;
        this.nameColor = nameColor;
        this.chatColor = chatColor;
        this.tagBold = tagBold;
        this.italic = italic;
        this.level = level;
    }

    public String getDisplayName(){
        return this.displayName;
    }

    public NamedTextColor getNameColor() {
        return this.nameColor;
    }

    public NamedTextColor getChatColor() {
        return this.chatColor;
    }

    public int getLevel(){
        return this.level;
    }

    public boolean isEqualOrHigherTo(Rank compare){
        return this.level >= compare.level;
    }

    public String getPrefix(){

        ChatColor tagColor = ChatColor.valueOf(this.getNameColor().toString().toUpperCase());
        String prefix = tagColor + this.displayName + ChatColor.RESET;

        if (this.tagBold && this.italic)
            prefix = "§o" + "§l" + prefix;
        else if (this.tagBold)
            prefix = "§l" + prefix;
        else if (this.italic)
            prefix = "§o" + prefix;

        prefix = "§8[" + prefix + "§8]§r";

        return prefix;
    }

}
