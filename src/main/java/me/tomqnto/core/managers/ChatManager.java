package me.tomqnto.core.managers;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.tomqnto.core.Utils.PlayerMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatManager implements Listener {

    private final PlayerManager playerManager;

    public ChatManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncChatEvent event){

        // Disabling chat during login/registering

        if (!(playerManager.isRegistered(event.getPlayer()))){
            event.setCancelled(true);
            PlayerMessage.notRegistered(event.getPlayer(), true, true);

        } else if (!(playerManager.isLoggedIn(event.getPlayer()))){
            event.setCancelled(true);
            PlayerMessage.notLoggedIn(event.getPlayer(), true, true);
        }

        // Chat rendering

        event.renderer((source, sourceDisplayName, message, viewer) -> {

            Rank sourceRank = playerManager.getRank(source);

            if (sourceRank!=null){

                Component rankPrefix = sourceRank.getPrefixDeserialized();
                NamedTextColor chatColor = sourceRank.getChatColor();

                if  (!(viewer instanceof final Player recipient))
                    return message;


                Component formattedMessage = sourceDisplayName
                        .append(Component.text(" » "))
                        .append(message).color(chatColor);


                return rankPrefix.append(Component.space()).append(formattedMessage);
            } else {
                Component formattedMessage = sourceDisplayName
                        .append(Component.text(" » "))
                        .append(message);
                return formattedMessage;
            }
            
        });
    }

}
