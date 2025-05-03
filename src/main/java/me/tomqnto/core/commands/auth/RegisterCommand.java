package me.tomqnto.core.commands.auth;

import me.tomqnto.core.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RegisterCommand implements CommandExecutor {

    private final PlayerManager playerManager;

    public RegisterCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)){
            sender.sendMessage("Only players can execute this command");
            return true;
        }

        if (playerManager.isRegistered(player)){
            player.sendRichMessage("<gray>You are already registered");
            return true;
        }

        if (!(args.length>=2)){
            player.sendRichMessage("<red>Missing arguments<br><red>Usage: /register <password> <password>");
            return true;
        }

        if (!(args[0]).equals(args[1])){
            player.sendRichMessage("<red>Password does not match with confirmation password.");
            return true;
        }

        if (args[0].length()<5){
            player.sendRichMessage("<red>Password must be at least 5 characters long.");
            return true;
        }

        playerManager.register(player, args[0]);
        player.sendRichMessage("<bold><green>Successfully registered!");

        return true;
    }
}
