package me.tomqnto.core.commands;

import me.tomqnto.core.managers.CommandManager;
import me.tomqnto.core.managers.Rank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SetRankCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (args.length==0)
            return false;

        OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
        String rankName = args[1].toUpperCase();

        if (Arrays.stream(Rank.values()).map(Enum::name).collect(Collectors.toCollection(ArrayList::new)).contains(rankName)) {

            CommandManager.setRank(sender, player, Rank.valueOf(rankName));

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
