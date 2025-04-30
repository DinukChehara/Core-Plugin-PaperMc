package me.tomqnto.core.commands.auth;

import me.tomqnto.core.managers.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LoginCommand implements CommandExecutor {

    private final PlayerData playerData;

    public LoginCommand(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {


        if (!(sender instanceof Player player)){
            sender.sendMessage("Only players can execute this command");
            return true;
        }
        if (playerData.isLoggedIn(player)){
            player.sendRichMessage("<red>You are already logged-in");
            return true;
        }

        if (!(args.length>=1)){
            player.sendRichMessage("<red>Missing arguments<br><red>Usage: /register <password>");
            return true;
        }

        if (!(args[0]).equals(playerData.getPassword(player))){
            player.sendRichMessage("<red>Password inccorect.");
            return true;
        }

        playerData.addLoginExpiration(player);
        playerData.setLoggedIn(player, true);
        player.sendRichMessage("<bold><green>Successfully logged-in to the server.!");

        return true;
    }

}
