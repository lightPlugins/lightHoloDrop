package de.lightplugins.commands;

import de.lightplugins.commands.main.ReloadCommand;
import de.lightplugins.master.ItemHolo;
import de.lightplugins.util.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

public class AshuraCommandManager implements CommandExecutor {

    private final ArrayList<SubCommand> subCommands = new ArrayList<>();
    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    public ItemHolo plugin;
    public AshuraCommandManager(ItemHolo plugin) {
        this.plugin = plugin;
        //  Subcommand register here
        subCommands.add(new ReloadCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if(sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                for(int i = 0; i < subCommands.size(); i++) {
                    if(args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {

                        try {
                            if(getSubCommands().get(i).perform(player, args)) {
                                //  hello, im a dummy comment - nothing to see here
                            }

                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }
}
