package me.tomqnto.core.commands.auth;

import me.tomqnto.core.managers.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RegisterCommand implements CommandExecutor {

    private final PlayerData playerData;

    public RegisterCommand(PlayerData commandActions) {
        this.playerData = commandActions;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)){
            sender.sendMessage("Only players can execute this command");
            return true;
        }

        if (playerData.isRegistered(player)){
            player.sendRichMessage("<red>You are already registered");
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

        playerData.registerPlayer(player, args[0]);
        playerData.setLoggedIn(player, true);
        player.sendRichMessage("<bold><green>Successfully registered!");

        return true;
    }
}
