package de.lightplugins.commands.main;

import de.lightplugins.master.ItemHolo;
import de.lightplugins.util.SubCommand;
import org.bukkit.entity.Player;

/*
 * ----------------------------------------------------------------------------
 *  This software and its source code, including text, graphics, and images,
 *  are the sole property of lightPlugins ("Author").
 *
 *  Unauthorized reproduction or distribution of this software, or any portion
 *  of it, may result in severe civil and criminal penalties, and will be
 *  prosecuted to the maximum extent possible under the law.
 * ----------------------------------------------------------------------------
 */

/**
 * This software is developed and maintained by lightPlugins.
 * For inquiries, please contact @discord: .light4coding.
 *
 * @version 1.0
 * @since 2023-11-14
 */

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
                ItemHolo.util.sendMessage(player, "&cDu hast für diesen Befehl keine Berechtigung&7!");
                return false;
            }
            ItemHolo.settings.reloadConfig("settings.yml");
            ItemHolo.messages.reloadConfig("messages.yml");

            ItemHolo.util.sendMessage(player, "&7Configs wurden #dc143derfolgreich &7neu geladen.");
        }

        return false;
    }
}
