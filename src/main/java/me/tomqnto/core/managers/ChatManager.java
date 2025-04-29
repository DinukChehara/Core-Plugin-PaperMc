package me.tomqnto.core.managers;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatManager implements Listener {



    @EventHandler
    public void onChat(AsyncChatEvent event){
        event.renderer((source, sourceDisplayName, message, viewer) -> {

            Rank sourceRank = PlayerData.getRank(source);

            Component formattedMessage = sourceDisplayName
                    .append(Component.text(" » "))
                    .append(message);

            if (sourceRank!=null){

                String rankPrefix = sourceRank.getPrefix();
                NamedTextColor chatColor = sourceRank.getChatColor();
                NamedTextColor tagColor = sourceRank.getTagColor();

                if  (!(viewer instanceof final Player recipient))
                    return message;

                Component rankComponent = Component.text(rankPrefix).color(tagColor);

                return rankComponent.append(Component.space()).append(formattedMessage.color(chatColor));
            } else {
                return formattedMessage;
            }
            
        });
    }

}
