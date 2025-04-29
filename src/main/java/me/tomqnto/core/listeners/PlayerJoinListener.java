package me.tomqnto.core.listeners;

import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import me.tomqnto.core.managers.PlayerData;
import me.tomqnto.core.managers.Rank;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoinFirstTime(PlayerJoinEvent event){

        if (!(event.getPlayer().hasPlayedBefore()) || PlayerData.getRank(event.getPlayer())==null){
            PlayerData.setRank(event.getPlayer(), Rank.DEFAULT);
        }

    }

}
