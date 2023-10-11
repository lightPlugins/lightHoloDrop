package de.lightplugins.commands.essentials;

import de.lightplugins.master.Ashura;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SunCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = (Player) commandSender;

        if(args.length != 0) {
            Ashura.util.sendMessage(player, "&cDas ist der falsche Befehl. Meintest du /sun ?");
            return false;
        }

        if(!player.hasPermission("ashura.command.sun")) {
            Ashura.util.sendMessage(player, "&cFür diesen Befehl hast du keine Berechtigung!");
            return false;
        }

        if(player.getWorld().isThundering()) {
            player.getWorld().setThundering(false);
        }

        if(player.getWorld().hasStorm()) {
            player.getWorld().setStorm(false);
        }

        Ashura.util.sendMessage(player, "&7Der #dc143dRegen &7verschwindet nun.");

        return false;
    }
}
