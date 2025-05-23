package me.tomqnto.core.commands.auth;

import me.tomqnto.core.Utils.PlayerMessage;
import me.tomqnto.core.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LoginCommand implements CommandExecutor {

    private final PlayerManager playerManager;

    public LoginCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {


        if (!(sender instanceof Player player)){
            sender.sendMessage("Only players can execute this command");
            return true;
        }

        if  (!playerManager.isRegistered(player)){
            PlayerMessage.notRegistered(player, true, true);
            return true;
        }

        if (playerManager.isLoggedIn(player)){
            PlayerMessage.alreadyLoggedIn(player);
            return true;
        }

        if (!(args.length>=1)){
            player.sendRichMessage("<red>Missing arguments<br><red>Usage: /login <password>");
            return true;
        }

        if (!(args[0]).equals(playerManager.getPassword(player))){
            player.sendRichMessage("<red>Password incorrect.");
            return true;
        }

        playerManager.setLoggedIn(player, true);
        player.sendRichMessage("<green>Successfully logged-in to the server!");
        playerManager.setExpiration(player);
        playerManager.setPlayerAddress(player);
        playerManager.setLoggedIn(player, true);

        return true;
    }

}
