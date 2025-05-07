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

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;


public class AuthManager implements Listener {

    private final PlayerManager playerManager;

    public AuthManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event){
        if (!(playerManager.isRegistered(event.getPlayer()))){
            playerManager.setLoggedIn(event.getPlayer(), false);
            PlayerMessage.notRegistered(event.getPlayer(), true, true);

        } else if (playerManager.getPlayerAddress(event.getPlayer())!=null && playerManager.hasAddressChanged(event.getPlayer())){
            PlayerMessage.addressChanged(event.getPlayer());
            PlayerMessage.notLoggedIn(event.getPlayer(), true, true);
            playerManager.setLoggedIn(event.getPlayer(), false);
            event.getPlayer().sendRichMessage(playerManager.getPlayerAddress(event.getPlayer()) + " now: " + event.getPlayer().getAddress().toString());

        } else if (playerManager.getExpiration(event.getPlayer())!=null && playerManager.hasLoginExpired(event.getPlayer())) {
            PlayerMessage.loginExpired(event.getPlayer());
            PlayerMessage.notLoggedIn(event.getPlayer(), true, true);
            playerManager.setLoggedIn(event.getPlayer(), false);

        } else if (playerManager.isLoggedIn(event.getPlayer())){
            playerManager.setLoggedIn(event.getPlayer(), true);
            Duration duration = Duration.between(Instant.now(), Instant.parse(playerManager.getExpiration(event.getPlayer())));
            String durationString = String.format("%01dh %02dm %02ds", duration.toSeconds()/3600, (duration.toSeconds()%3600)/60, duration.toSeconds()%60);
            event.getPlayer().sendRichMessage("<green>Login session continued. It will expire in <yellow>" + durationString);
        } else{
            event.getPlayer().sendRichMessage("<red>An error occurred, rejoin again. If that doesn't work please contact staff");
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        if (playerManager.getPlayerAddress(event.getPlayer())!=null && playerManager.hasAddressChanged(event.getPlayer()))
            playerManager.setLoggedIn(event.getPlayer(), false);
        else if (playerManager.getExpiration(event.getPlayer())!=null && playerManager.hasLoginExpired(event.getPlayer()))
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
