package me.tomqnto.core.managers;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class AuthManager implements Listener {

    private final PlayerManager playerManager;

    public AuthManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event){
        playerManager.setLoggedIn(event.getPlayer(), false);
        if (!(playerManager.isRegistered(event.getPlayer()))){
            event.getPlayer().sendRichMessage("<red><bold>You are not registered!</red><br><yellow>Use /register <password> <password></yellow>");
        } else{
            event.getPlayer().sendRichMessage("<red>You are not logged-in!<br><red>Use /login <password>");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(event.hasChangedPosition()){
            if (!(playerManager.isRegistered(player))) {
                event.setCancelled(true);
            } else if (!(playerManager.isLoggedIn(player))){
                event.setCancelled(true);
            }
        }

    }

}
