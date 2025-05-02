package me.tomqnto.core.managers;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
            event.getPlayer().sendRichMessage("<red>You need to be registered to do this.");

        } else if (!(playerManager.isLoggedIn(event.getPlayer()))){
            event.setCancelled(true);
            event.getPlayer().sendRichMessage("<red>You need to login to do this.");
        }

        // Chat rendering

        event.renderer((source, sourceDisplayName, message, viewer) -> {

            Rank sourceRank = playerManager.getRank(source);

            if (sourceRank!=null){

                String rankPrefix = sourceRank.getPrefix();
                NamedTextColor chatColor = sourceRank.getChatColor();
                NamedTextColor tagColor = sourceRank.getNameColor();

                if  (!(viewer instanceof final Player recipient))
                    return message;

                Component rankComponent = Component.text(rankPrefix);

                Component formattedMessage = sourceDisplayName
                        .append(Component.text(" » "))
                        .append(message).color(chatColor);


                return rankComponent.append(Component.space()).append(formattedMessage);
            } else {
                Component formattedMessage = sourceDisplayName
                        .append(Component.text(" » "))
                        .append(message);
                return formattedMessage;
            }
            
        });
    }

}
