package me.tomqnto.core.commands.rank;

import me.tomqnto.core.managers.PlayerManager;
import me.tomqnto.core.managers.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ResetRank implements CommandExecutor {

    private final PlayerManager playerManager;

    public ResetRank(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (args.length==0)
            return false;

        if (!Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()){
            sender.sendMessage("Could not find player " + args[0] + ", this player has not played on this server before.");
            return true;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);

        if (playerManager.hasRank(player)){

            String prefixBefore = playerManager.getRank(player).getPrefix();
            prefixBefore = prefixBefore + ChatColor.RESET;
            String change = prefixBefore + " → " + Rank.DEFAULT.getPrefix();
            if (sender instanceof Player senderPlayer){

                int senderLevel = playerManager.getRank(senderPlayer).getLevel();
                int targetLevel = playerManager.getRank(player).getLevel();

                if (senderPlayer.isOp()){
                    playerManager.setRank(player, Rank.DEFAULT);
                    senderPlayer.sendMessage("Reset " + player.getName() + "'s rank: " + change);
                    if (player.isOnline()){
                        ((Player) player).sendMessage("Reset your rank: " + change);
                    }
                } else if (senderLevel <= targetLevel){
                    senderPlayer.sendMessage("You do not have permission to do this.");
                }

            } else if (sender instanceof ConsoleCommandSender consoleCommandSender){

                playerManager.setRank(player, Rank.DEFAULT);
                consoleCommandSender.sendMessage("Reset " + player.getName() + "'s rank: " + change);
                if (player.isOnline()){
                    ((Player) player).sendMessage("Reset your rank: " + change);
                }

            }
        } else
            sender.sendMessage("This player does not have a rank to remove.");

        return true;
    }
}
