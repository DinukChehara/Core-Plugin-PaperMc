package me.tomqnto.core.Utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerMessage {

    public static void failAction(Player player, String reason){
        player.sendRichMessage(reason);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
    }

    public static void notRegistered(Player player, boolean sendUsage, boolean playSound){
        String usage = "";
        if (sendUsage)
            usage = "<br><gold>Usage: /register <password> <password>";
        player.sendRichMessage("<red>You are not registered" + usage);
        if (playSound)
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
    }

    public static void notLoggedIn(Player player, boolean sendUsage, boolean playSound){
        String usage = "";
        if (sendUsage)
            usage = "<br><gold>Usage: /login <password>";
        player.sendRichMessage("<red>You are not logged in" + usage);
        if (playSound)
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
    }

    public static void alreadyRegistered(Player player){
        failAction(player, "<gray>You are already registered");
    }

    public static void alreadyLoggedIn(Player player){
        failAction(player, "<gray>You are already logged in");
    }

}
