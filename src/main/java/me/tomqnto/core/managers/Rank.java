package me.tomqnto.core.managers;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;


public enum Rank {

    OWNER("Owner", NamedTextColor.GOLD, NamedTextColor.YELLOW, true, false, 5),
    ADMIN("Admin", NamedTextColor.DARK_RED, NamedTextColor.RED, true, false, 4),
    MOD("Mod", NamedTextColor.DARK_GREEN, NamedTextColor.GREEN, true, false, 3),
    HELPER("Helper", NamedTextColor.DARK_BLUE, NamedTextColor.BLUE, true, false, 2),
    DEFAULT("Default", NamedTextColor.GRAY, NamedTextColor.WHITE, false, false, 1);


    private final String name;
    private final NamedTextColor tagColor;
    private final NamedTextColor chatColor;
    private final boolean tagBold, italic;
    private final int level;

    Rank(String name, NamedTextColor tagColor, NamedTextColor chatColor, boolean tagBold, boolean italic, int level) {
        this.name = name;
        this.tagColor = tagColor;
        this.chatColor = chatColor;
        this.tagBold = tagBold;
        this.italic = italic;
        this.level = level;
    }

    public String getName(){
        return this.name;
    }

    public NamedTextColor getTagColor() {
        return this.tagColor;
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

        String prefix = '[' + this.name + ']';

        if (this.tagBold && this.italic)
            prefix = "§o" + "§l" + prefix;
        else if (this.tagBold)
            prefix = "§l" + prefix;
        else if (this.italic)
            prefix = "§o" + prefix;

        prefix = prefix + "§r";

        return prefix;
    }

    public String getPrefixColored(){

        String prefix = this.getPrefix();
        ChatColor color = ChatColor.valueOf(this.getTagColor().toString().toUpperCase());

        return color + prefix + ChatColor.RESET;

    }

}
