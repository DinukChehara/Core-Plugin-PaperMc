package me.tomqnto.core.commands.rank;

import me.tomqnto.core.managers.PlayerManager;
import me.tomqnto.core.managers.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SetRankCommand implements CommandExecutor, TabCompleter {

    private final PlayerManager playerManager;

    public SetRankCommand(PlayerManager playerManager ) {
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
        String rankName = args[1].toUpperCase();


        if (Arrays.stream(Rank.values()).map(Enum::name).collect(Collectors.toCollection(ArrayList::new)).contains(rankName)) {
            Rank rank = Rank.valueOf(rankName);

            String prefixBefore;
            String prefixAfter = rank.getPrefix();
            String change;
            prefixAfter = prefixAfter + ChatColor.RESET;
            int senderLevel;
            int targetLevel;
            String permission = "core.rank." + rank.getDisplayName().toLowerCase();

            if (playerManager.hasRank(player)){
                targetLevel = playerManager.getRank(player).getLevel();
                prefixBefore = playerManager.getRank(player).getPrefix();
                prefixBefore = prefixBefore + ChatColor.RESET;
            }
            else{
                targetLevel = Rank.DEFAULT.getLevel();
                prefixBefore = "None";
            }

            change = prefixBefore + " → " + prefixAfter;

            if (sender instanceof Player senderPlayer){

                if (playerManager.hasRank(senderPlayer))
                    senderLevel = playerManager.getRank(senderPlayer).getLevel();
                else
                    senderLevel = Rank.DEFAULT.getLevel();

                if (senderPlayer.isOp()){
                    playerManager.setRank(player, rank);
                    senderPlayer.sendMessage("Changed " + player.getName() + "'s rank: " + change);
                    if (player.isOnline())
                        ((Player) player).sendMessage("Changed your rank: " + change);

                } else if (senderLevel <= targetLevel){
                    senderPlayer.sendMessage("You do not have permission to do this.");
                }

            } else if (sender instanceof ConsoleCommandSender){

                playerManager.setRank(player, rank);
                sender.sendMessage("Changed " + player.getName() + "'s rank: " + change);
                if (player.isOnline())
                    ((Player) player).sendMessage("Removed your rank: " + change);

            }

        }else
            sender.sendMessage("Invalid rank.");


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (args.length==2){

            return Arrays.stream(Rank.values()).map(Enum::name).collect(Collectors.toCollection(ArrayList::new));

        } else{
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toCollection(ArrayList::new));
        }
    }
}

