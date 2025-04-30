package me.tomqnto.core.managers;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatManager implements Listener {

    private static PlayerData playerData;

    public ChatManager(PlayerData playerData) {
        ChatManager.playerData = playerData;
    }

    @EventHandler
    public void onChat(AsyncChatEvent event){
        event.renderer((source, sourceDisplayName, message, viewer) -> {

            Rank sourceRank = playerData.getRank(source);



            if (sourceRank!=null){

                String rankPrefix = sourceRank.getPrefix();
                NamedTextColor chatColor = sourceRank.getChatColor();
                NamedTextColor tagColor = sourceRank.getTagColor();

                if  (!(viewer instanceof final Player recipient))
                    return message;

                Component formattedMessage = sourceDisplayName.color(tagColor)
                        .append(Component.text(" » "))
                        .append(message).color(chatColor);

                Component rankComponent = Component.text(rankPrefix).color(tagColor);

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
