package me.tomqnto.core.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;


public enum Rank {

    OWNER("<gold>Owner", NamedTextColor.YELLOW, 5),
    ADMIN("<dark_red>Admin", NamedTextColor.RED, 4),
    MOD("<dark_green>Mod", NamedTextColor.GREEN, 3),
    HELPER("<dark_aqua>Helper", NamedTextColor.AQUA, 2),
    MEDIA("<dark_purple>Media", NamedTextColor.LIGHT_PURPLE, 1),
    MVP("<dark_blue>MVP", NamedTextColor.BLUE, 1),
    DEFAULT("<gray>Default", NamedTextColor.WHITE, 1);


    private final String tag;
    private final NamedTextColor chatColor;
    private final int level;

    Rank(String tag, NamedTextColor chatColor,int level) {
        this.tag = tag;
        this.chatColor = chatColor;
        this.level = level;
    }

    public String getTag(){
        return this.tag;
    }

    public Component getTagDeserialized(){
        return MiniMessage.miniMessage().deserialize(this.tag + "<reset>");
    }

    public String getTagSerialized(){

        LegacyComponentSerializer legacy = LegacyComponentSerializer.legacySection();

        return legacy.serialize(this.getTagDeserialized());
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

    public String getPrefixSerialized(){

        String prefix = this.getTagSerialized();
        prefix = "§8[§r" + prefix + "§8]§r";

        return prefix;
    }

    public Component getPrefixDeserialized(){
        return MiniMessage.miniMessage().deserialize("<dark_gray>[</dark_gray>" + this.getTag() + "<reset>" + "<dark_gray>]</dark_gray>");
    }

}
