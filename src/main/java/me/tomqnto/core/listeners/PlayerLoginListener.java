package me.tomqnto.core.listeners;

import me.tomqnto.core.managers.PlayerManager;
import me.tomqnto.core.managers.ServerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    private final PlayerManager playerManager;
    private final ServerManager serverManager;

    public PlayerLoginListener(PlayerManager playerManager, ServerManager serverManager) {
        this.playerManager = playerManager;
        this.serverManager = serverManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event){

        Player player = event.getPlayer();
        int playerRankLevel = playerManager.getRank(player).getLevel();

        if (player.isBanned()){
            Component message = MiniMessage.miniMessage().deserialize("<dark_red>You are banned<br><darK_gray>----------------------------<br><red>If you think this is a mistake<br>contact staff");
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, message);
        }

        if (serverManager.getState().getRankLevel()>playerRankLevel){
            Component message = MiniMessage.miniMessage().deserialize("<dark_red>You got kicked<br><darK_gray>----------------------------<br>");
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, message.append(serverManager.getState().getKickMessage()));
        }
    }
    
}
