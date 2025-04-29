package me.tomqnto.core.commands;

import me.tomqnto.core.managers.CommandManager;
import me.tomqnto.core.managers.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RemoveRankCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (args.length==0)
            return false;

        Player player = Bukkit.getPlayer(args[0]);

        CommandManager.removeRank(sender, player);


        return true;
    }
}
