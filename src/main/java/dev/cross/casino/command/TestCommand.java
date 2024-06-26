package dev.cross.casino.command;

import dev.cross.blissfulcore.ui.display.WindowDisplay;
import dev.cross.casino.ui.display.RouletteDisplay;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.isOp()) return false;
        if (commandSender instanceof Player p) {
            WindowDisplay display = new RouletteDisplay();

            p.sendMessage("Spawned Casino Display");
            Location loc = p.getLocation().add(0, 1.5, 0);
            display.spawn(loc);

        }
        return true;
    }
}
