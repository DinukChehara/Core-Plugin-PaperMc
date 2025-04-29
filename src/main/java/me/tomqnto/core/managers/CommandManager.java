package me.tomqnto.core.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandManager{

    public static void setRank(CommandSender commandSender, OfflinePlayer target, Rank rank){

        String prefixBefore;
        String prefixAfter = rank.getPrefix();
        prefixAfter = ChatColor.valueOf(rank.getTagColor().toString().toUpperCase()) + prefixAfter + ChatColor.RESET;
        int senderLevel;
        int targetLevel;

        if (PlayerData.hasRank(target)){
            targetLevel = PlayerData.getRank(target).getLevel();
            prefixBefore = PlayerData.getRank(target).getPrefix();
            prefixBefore = ChatColor.valueOf(PlayerData.getRank(target).getTagColor().toString().toUpperCase()) + prefixBefore + ChatColor.RESET;
        }
        else{
            targetLevel = Rank.DEFAULT.getLevel();
            prefixBefore = "None";
        }

        String change = prefixBefore + " → " + prefixAfter;

        if (commandSender instanceof Player senderPlayer){

            if (PlayerData.hasRank(senderPlayer))
                senderLevel = PlayerData.getRank(senderPlayer).getLevel();
            else
                senderLevel = Rank.DEFAULT.getLevel();

            if (senderLevel <= targetLevel && PlayerData.getRank(senderPlayer)!=Rank.OWNER)
                senderPlayer.sendMessage("You do not have permission to do this.");
            else{
                PlayerData.setRank(target, rank);
                senderPlayer.sendMessage("Changed " + target.getName() + "'s rank: " + change);
                if (target.isOnline())
                    ((Player) target).sendMessage("Changed your rank: " + change);
            }

        } else if (commandSender instanceof ConsoleCommandSender){

            PlayerData.setRank(target, rank);
            commandSender.sendMessage("Changed " + target.getName() + "'s rank: " + change);
            if (target.isOnline())
                ((Player) target).sendMessage("Changed your rank: " + change);

        }

    }

    public static void removeRank(CommandSender commandSender, OfflinePlayer target){

        if (PlayerData.hasRank(target)){
            String prefixBefore = PlayerData.getRank(target).getPrefix();
            prefixBefore = ChatColor.valueOf(PlayerData.getRank(target).getTagColor().toString().toUpperCase()) + prefixBefore + ChatColor.RESET;
            String change = prefixBefore + " → " + "None";
            if (commandSender instanceof Player senderPlayer){

                int senderLevel = PlayerData.getRank(senderPlayer).getLevel();
                int targetLevel = PlayerData.getRank(target).getLevel();

                if (senderLevel <= targetLevel && PlayerData.getRank(senderPlayer)!=Rank.OWNER)
                    senderPlayer.sendMessage("You do not have permission to do this.");
                else{
                    PlayerData.removeRank(target);
                    senderPlayer.sendMessage("Removed " + target.getName() + "'s rank: " + change);
                    if (target.isOnline()){
                        ((Player) target).sendMessage("Removed your rank: " + change);
                    }
                }

            } else if (commandSender instanceof ConsoleCommandSender consoleCommandSender){

                PlayerData.removeRank(target);
                consoleCommandSender.sendMessage("Removed " + target.getName() + "'s rank: " + change);
                if (target.isOnline()){
                    ((Player) target).sendMessage("Removed your rank: " + change);
                }

            }
        } else
            commandSender.sendMessage("This player does not have a rank to remove.");


    }

}
