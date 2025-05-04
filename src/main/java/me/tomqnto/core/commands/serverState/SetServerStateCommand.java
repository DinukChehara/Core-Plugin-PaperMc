package me.tomqnto.core.commands.serverState;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import me.tomqnto.core.managers.ServerManager;
import me.tomqnto.core.managers.ServerState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SetServerStateCommand implements CommandExecutor {

    private final ServerManager serverManager;

    public SetServerStateCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (args.length==0){
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<gray>Missing arguments<br><yellow>Usage: /setstate [PUBLIC|MAINTENANCE]"));
            return true;
        }

        serverManager.setState(ServerState.valueOf(args[0]));
        sender.sendRichMessage("<green>Successfully set server state to " + ServerState.valueOf(args[0]).name().toLowerCase() + " mode.");

        return true;

    }
}
