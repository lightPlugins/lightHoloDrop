package de.lightplugins.commands.tabcompletion;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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

public class AshuraTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = (Player) commandSender;

        if(player.hasPermission("lightholo.admin.command.reload")) {
            if(args.length == 1) {
                List<String> arguments = new ArrayList<>();
                arguments.add("reload");
                return arguments;
            }
        }
        return null;
    }
}
