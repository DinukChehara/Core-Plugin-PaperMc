package me.tomqnto.core.commands.rank;

import me.tomqnto.core.managers.CommandActions;
import me.tomqnto.core.managers.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SetRankCommand implements CommandExecutor, TabCompleter {

    private final CommandActions commandActions;

    public SetRankCommand(CommandActions commandActions) {
        this.commandActions = commandActions;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (!(sender.hasPermission("core.rank.setrank")) && sender instanceof Player){
            sender.sendMessage("You do not have permission to execute this command.");
            return true;
        }

        if (args.length==0)
            return false;

        if (Bukkit.getPlayer(args[0])==null){
            sender.sendMessage("Could not find player " + args[0]);
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);

        String rankName = args[1].toUpperCase();

        if (Arrays.stream(Rank.values()).map(Enum::name).collect(Collectors.toCollection(ArrayList::new)).contains(rankName)) {
            commandActions.setRank(sender, player, Rank.valueOf(rankName));
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
