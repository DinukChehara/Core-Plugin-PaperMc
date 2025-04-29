package me.tomqnto.core.commands.rank;

import me.tomqnto.core.managers.CommandActions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RemoveRankCommand implements CommandExecutor {

    private final CommandActions commandActions;

    public RemoveRankCommand(CommandActions commandActions) {
        this.commandActions = commandActions;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (!(sender.hasPermission("core.rank.removerank")) && sender instanceof Player){
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

        commandActions.removeRank(sender, player);

        return true;
    }
}
