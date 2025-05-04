package me.tomqnto.core.packets;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.tomqnto.core.Core;
import me.tomqnto.core.managers.PlayerManager;
import org.bukkit.entity.Player;

public class Packets {

    private final Core plugin;
    private final ProtocolManager manager;
    private final PlayerManager playerManager;


    public Packets(Core plugin, ProtocolManager manager, PlayerManager playerManager) {
        this.plugin = plugin;
        this.manager = manager;
        this.playerManager = playerManager;
    }

    // client -> server

    public void clientToServer(){

        manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.CLIENT_COMMAND) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();

                if (!(playerManager.isRegistered(player))){
                    player.sendRichMessage("<gray>You need to be registered to do this.");
                    event.setCancelled(true);
                }

                if (!(playerManager.isLoggedIn(player))){
                    player.sendRichMessage("<gray>You need to be logged in to do this.");
                    event.setCancelled(true);
                }
            }
        });

    }

}
