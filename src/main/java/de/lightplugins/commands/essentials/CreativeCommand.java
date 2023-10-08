package de.lightplugins.commands.essentials;

import de.lightplugins.master.Ashura;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreativeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = (Player) commandSender;

        if(args.length == 1) {

            if(!player.hasPermission("ashura.admin.command.gmc.other")) {
                Ashura.util.sendMessage(player, "&cDu hast für diesen Befehl keine Berechtigung&7!");
            }

            Player target = Bukkit.getPlayer(args[0]);
            if(target == null) {
                Ashura.util.sendMessage(player,
                        "&cDieser Spieler existiert nicht&7!");
                return false;
            }

            target.setGameMode(GameMode.CREATIVE);

            Ashura.util.sendMessage(player,
                    "&7Du hast den Spielmodus von #dc143d" + args[0] + " &7auf #dc143dKreativ &7gesetzt");
            Ashura.util.sendMessage(target,
                    "&7Dein Spielmodus wurde auf #dc143dKreativ &7gesetzt");

            return false;

        }

        if(args.length == 0) {

            if(!player.hasPermission("ashura.admin.command.gmc")) {
                Ashura.util.sendMessage(player, "&cDu hast für diesen Befehl keine Berechtigung&7!");
            }

            player.setGameMode(GameMode.CREATIVE);

            Ashura.util.sendMessage(player,
                    "&7Du hast deinen Spielmodus auf #dc143dKreativ &7gesetzt");

            return false;

        }

        return false;
    }
}
