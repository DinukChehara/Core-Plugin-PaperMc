package me.tomqnto.core.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class TabManager implements Runnable{

    private final PlayerManager playerManager;

    static int x = 0;

    public TabManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }


    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){

            String rankPrefix = playerManager.getRank(player).getPrefixSerialized();
            String coloredPlayerName = ChatColor.valueOf(playerManager.getRank(player).getChatColor().toString().toUpperCase()) + player.getName();

            double tpsInt = Bukkit.getServer().getTPS()[0];
            tpsInt = Math.round(tpsInt * 100) / 100.0;
            String tps = "";

            int pingInt = player.getPing();
            String ping = "";

            if (pingInt<=90)
                ping = "<green>" + pingInt;
            else if (pingInt<=180)
                ping = "<yellow>" + pingInt;
            else
                ping = "<red>" + pingInt;

            if (tpsInt<16)
                tps = "<red>" + tpsInt;
            else if (tpsInt<18)
                tps = "<yellow>" + tpsInt;
            else
                tps = "<green>" + tpsInt;

            String header = "Hello " + player.getName();
            String footer = "<br><green>TPS: " + tps + "<dark_gray> | <green>Ping: " + ping + "ms";

            if (x>=header.length())
                x=0;

            String animatedHeader = header.substring(0, x) + "<color:#ffffaa>" + header.charAt(x) + "</color>" + header.substring(x + 1);
            animatedHeader = "<bold><yellow>" + animatedHeader + "<br>";

            player.setPlayerListName(rankPrefix + " " + coloredPlayerName);
            player.sendPlayerListHeaderAndFooter(MiniMessage.miniMessage().deserialize(animatedHeader), MiniMessage.miniMessage().deserialize(footer));
        }
        x++;
    }
}
