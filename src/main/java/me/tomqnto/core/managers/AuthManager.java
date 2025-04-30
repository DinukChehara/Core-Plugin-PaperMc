package me.tomqnto.core.managers;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AuthManager implements Listener {

    private final PlayerData playerData;

    public AuthManager(PlayerData playerData) {
        this.playerData = playerData;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event){

        if (!(playerData.isRegistered(event.getPlayer()))){

            event.getPlayer().sendRichMessage("<red><bold>You are not registered</red><br><yellow>Use /register <password> <password></yellow>");
            playerData.setLoggedIn(event.getPlayer(), false);

        } else if (playerData.isLoginExpired(event.getPlayer())){

                event.getPlayer().sendRichMessage("<red><bold>You are not logged-in</red><br><yellow>Use /login <password></yellow>");
                playerData.setLoggedIn(event.getPlayer(), false);

        }else{

            Instant expirationInstant = playerData.getLoginExpiration(event.getPlayer());
            Duration duration = Duration.between(Instant.now(), expirationInstant);

            LocalTime expirationTime = LocalTime.now().plus(duration);
            String expirationTimeString = expirationTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            event.getPlayer().sendRichMessage("<green>Login session continued<br><green>Your login will expire at:<yellow> " + expirationTimeString);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        if (!(playerData.isRegistered(player))) {
            event.setCancelled(true);

        } else if (!(playerData.isLoggedIn(player))){
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onChatEvent(AsyncChatEvent event){

        if (!(playerData.isRegistered(event.getPlayer()))){
            event.setCancelled(true);
            event.getPlayer().sendRichMessage("<red>You need to be registered to do this.");

        } else if (!(playerData.isLoggedIn(event.getPlayer()))){
            event.setCancelled(true);
            event.getPlayer().sendRichMessage("<red>You need to login to do this.");

        }
    }

}
