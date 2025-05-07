package me.tomqnto.core.commands.serverState;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import me.tomqnto.core.managers.Rank;
import me.tomqnto.core.managers.ServerManager;
import me.tomqnto.core.managers.ServerState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SetServerStateCommand implements CommandExecutor, TabCompleter {

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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (args.length>1)
            return List.of();

        List<String> states = Arrays.stream(ServerState.values()).map(Enum::name).collect(Collectors.toCollection(ArrayList::new));
        return states.stream().filter(state -> state.startsWith(args[0])).collect(Collectors.toList());

    }
}
