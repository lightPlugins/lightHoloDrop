package de.lightplugins.commands.main;

import de.lightplugins.master.Ashura;
import de.lightplugins.util.SubCommand;
import org.bukkit.entity.Player;


public class ReloadCommand extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "reloads the configs";
    }

    @Override
    public String getSyntax() {
        return "/a reload";
    }

    @Override
    public boolean perform(Player player, String[] args) {



        if(args.length == 1) {

            if(!player.hasPermission("ashura.admin.command.reload")) {
                Ashura.util.sendMessage(player, "&cDu hast für diesen Befehl keine Berechtigung&7!");
                return false;
            }
            Ashura.settings.reloadConfig("settings.yml");
            Ashura.messages.reloadConfig("messages.yml");

            Ashura.util.sendMessage(player, "&7Configs wurden #dc143derfolgreich &7neu geladen.");
        }

        return false;
    }
}
