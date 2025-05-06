package me.tomqnto.core.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TabManager implements Runnable{

    private final PlayerManager playerManager;

    static int x = 0;

    public TabManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }


    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){

            String rankPrefix = playerManager.getRank(player).getPrefix();
            String coloredPlayerName = ChatColor.valueOf(playerManager.getRank(player).getChatColor().toString().toUpperCase()) + player.getName();

            String header = "Hello " + player.getName();
            String footer = "<br><green>Have a nice time!";

            if (x>=header.length())
                x=0;

            String animatedHeader = header.substring(0, x) + "<white>" + header.charAt(x) + "</white>" + header.substring(x + 1);
            animatedHeader = "<bold><yellow>" + animatedHeader + "<br>";

            player.setPlayerListName(rankPrefix + " " + coloredPlayerName);
            player.sendPlayerListHeaderAndFooter(MiniMessage.miniMessage().deserialize(animatedHeader), MiniMessage.miniMessage().deserialize(footer));
        }
        x++;
    }
}
