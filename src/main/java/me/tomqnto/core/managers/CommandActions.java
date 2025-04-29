package me.tomqnto.core.managers;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandActions {

    private final PlayerData playerData;

    public CommandActions(PlayerData playerData) {
        this.playerData = playerData;
    }

    public void setRank(CommandSender commandSender, OfflinePlayer target, Rank rank){

        String prefixBefore;
        String prefixAfter = rank.getPrefix();
        String change;
        prefixAfter = ChatColor.valueOf(rank.getTagColor().toString().toUpperCase()) + prefixAfter + ChatColor.RESET;
        int senderLevel;
        int targetLevel;
        String permission = "core.rank." + rank.getName().toLowerCase();

        if (playerData.hasRank(target)){
            targetLevel = playerData.getRank(target).getLevel();
            prefixBefore = playerData.getRank(target).getPrefix();
            prefixBefore = ChatColor.valueOf(playerData.getRank(target).getTagColor().toString().toUpperCase()) + prefixBefore + ChatColor.RESET;
        }
        else{
            targetLevel = Rank.DEFAULT.getLevel();
            prefixBefore = "None";
        }

        change = prefixBefore + " → " + prefixAfter;

        if (commandSender instanceof Player senderPlayer){

            if (playerData.hasRank(senderPlayer))
                senderLevel = playerData.getRank(senderPlayer).getLevel();
            else
                senderLevel = Rank.DEFAULT.getLevel();

            if (senderLevel <= targetLevel && playerData.getRank(senderPlayer)!=Rank.OWNER)
                senderPlayer.sendMessage("You do not have permission to do this.");
            else{
                playerData.setRank(target, rank);
                senderPlayer.sendMessage("Changed " + target.getName() + "'s rank: " + change);
                playerData.addPermission(target, permission);
                if (target.isOnline())
                    ((Player) target).sendMessage("Changed your rank: " + change);
            }

        } else if (commandSender instanceof ConsoleCommandSender){

            playerData.setRank(target, rank);
            commandSender.sendMessage("Changed " + target.getName() + "'s rank: " + change);
            playerData.addPermission(target, permission);
            if (target.isOnline())
                ((Player) target).sendMessage("Changed your rank: " + change);

        }

    }

    public void removeRank(CommandSender commandSender, OfflinePlayer target){

        if (playerData.hasRank(target)){

            String permission = "core.rank." + playerData.getRank(target).getName().toLowerCase();

            String prefixBefore = playerData.getRank(target).getPrefix();
            prefixBefore = ChatColor.valueOf(playerData.getRank(target).getTagColor().toString().toUpperCase()) + prefixBefore + ChatColor.RESET;
            String change = prefixBefore + " → " + "None";
            if (commandSender instanceof Player senderPlayer){

                int senderLevel = playerData.getRank(senderPlayer).getLevel();
                int targetLevel = playerData.getRank(target).getLevel();

                if (senderLevel <= targetLevel && playerData.getRank(senderPlayer)!=Rank.OWNER)
                    senderPlayer.sendMessage("You do not have permission to do this.");
                else{
                    playerData.removeRank(target);
                    senderPlayer.sendMessage("Removed " + target.getName() + "'s rank: " + change);
                    playerData.removePermission(target, permission);
                    if (target.isOnline()){
                        ((Player) target).sendMessage("Removed your rank: " + change);
                    }
                }

            } else if (commandSender instanceof ConsoleCommandSender consoleCommandSender){

                playerData.removeRank(target);
                consoleCommandSender.sendMessage("Removed " + target.getName() + "'s rank: " + change);
                playerData.removePermission(target, permission);
                if (target.isOnline()){
                    ((Player) target).sendMessage("Removed your rank: " + change);
                }

            }
        } else
            commandSender.sendMessage("This player does not have a rank to remove.");


    }

}
