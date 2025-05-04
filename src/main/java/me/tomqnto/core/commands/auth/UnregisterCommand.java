package me.tomqnto.core.commands.auth;

import me.tomqnto.core.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnregisterCommand implements CommandExecutor {

    private final PlayerManager playerManager;

    public UnregisterCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (args.length==0){
            if (sender instanceof Player)
                sender.sendRichMessage("<red>Missing arguments<br><yellow>Usage: /unregister <player>");
            else{
                sender.sendMessage("Missing arguments");
                sender.sendMessage("Usage: /unregister <player>");
            }
            return true;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);

        if (!(playerManager.isRegistered(player))){
            if (sender instanceof Player){
                sender.sendRichMessage("<gray>This player is not registered.");
            } else
                sender.sendMessage("This player is not registered.");
            return true;
        }

        playerManager.setLoggedIn(player, false);
        playerManager.unregister(player);
        if (player.isOnline())
            ((Player) player).sendRichMessage("<red><bold>You are not registered!</red><br><yellow>Use /register <password> <password></yellow>");

        if (sender instanceof Player)
            sender.sendRichMessage("<green>Successfully unregistered " + args[0]);
        else
            sender.sendMessage("Successfully unregistered " + args[0]);

        return true;
    }
}
