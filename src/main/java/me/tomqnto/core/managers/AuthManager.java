package me.tomqnto.core.managers;

import me.tomqnto.core.Utils.PlayerMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class AuthManager implements Listener {

    private final PlayerManager playerManager;

    public AuthManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event){
        if (!(playerManager.isRegistered(event.getPlayer()))){
            playerManager.setLoggedIn(event.getPlayer(), false);
            PlayerMessage.notRegistered(event.getPlayer(), true, true);
        } else{
            PlayerMessage.notLoggedIn(event.getPlayer(), true, true);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        playerManager.setLoggedIn(event.getPlayer(), false);
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

    @EventHandler
    public void onPlayerRunCommand(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        String command = event.getMessage();

        if (!playerManager.isRegistered(player) && !(command.startsWith("/register") || command.startsWith("/login"))){
            event.setCancelled(true);
            PlayerMessage.notRegistered(player, true, true);

        } else if (!playerManager.isLoggedIn(player) && !(command.startsWith("/register") || command.startsWith("/login"))) {
            event.setCancelled(true);
            PlayerMessage.notLoggedIn(player, true, true);
        }

    }

}
