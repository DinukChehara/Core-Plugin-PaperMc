package me.tomqnto.core.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.tomqnto.core.Core;
import me.tomqnto.core.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ProtocolLibHook {

    public static void register(PlayerManager playerManager){

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        if (manager!=null){
            // changing player name tag
            manager.addPacketListener(new PacketAdapter(Core.getInstance(), PacketType.Play.Server.ENTITY_METADATA) {
                @Override
                public void onPacketSending(PacketEvent event) {
                    event.getPlayer().setCustomName(playerManager.getRank(event.getPlayer()).getPrefix() + " " + ChatColor.valueOf(playerManager.getRank(event.getPlayer()).getChatColor().toString().toUpperCase()) + event.getPlayer().getName());
                    event.getPlayer().setCustomNameVisible(true);
                }
            });
        } else
            Bukkit.getLogger().warning("'manager' in ProtocolLibHook is null");


    }

}
