package de.lightplugins.commands.essentials;

import de.lightplugins.master.Ashura;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;


public class DayTimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(args.length != 0) { return false;  }

        long dayTime = 4500;
        long velocity = 20;

        Player player = (Player) commandSender;

        if(!player.hasPermission("ashura.admin.command.day")) {
            Ashura.util.sendMessage(player, "&cDu hast für diesen Befehl keine Berechtigung&7!");
            return false;
        }

        Ashura.util.sendMessage(player,
                "&7Du hast die Zeit auf #dc143d" + dayTime + " &7gestellt");

        new BukkitRunnable() {

            long currentTime = player.getWorld().getTime();
            int counter = 0;

            @Override
            public void run() {

                counter ++;

                if(counter >= 5000) {
                    player.getWorld().setTime(currentTime);
                    cancel();
                }

                if(dayTime != currentTime) {

                    if(currentTime > dayTime) {
                        currentTime += (dayTime - currentTime - velocity) / velocity;
                        player.getWorld().setTime(currentTime);
                    } else {
                        currentTime += (dayTime - currentTime + velocity) / (velocity);
                        player.getWorld().setTime(currentTime);
                    }

                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Ashura.getInstance, 0, 1);

        return false;
    }
}
