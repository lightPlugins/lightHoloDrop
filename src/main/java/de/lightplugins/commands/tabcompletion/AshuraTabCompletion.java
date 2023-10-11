package de.lightplugins.commands.tabcompletion;

import de.lightplugins.master.Ashura;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AshuraTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        Player player = (Player) commandSender;

        if(args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("box");
            arguments.add("trades");
            arguments.add("border");
            arguments.add("reload");
            return arguments;
        }

        if(args.length == 2) {
            if(args[0].equalsIgnoreCase("trades")) {
                FileConfiguration trades = Ashura.trades.getConfig();
                return new ArrayList<>(trades.getConfigurationSection("trades").getKeys(false));

            }

            if(args[0].equalsIgnoreCase("box")) {
                List<String> arguments = new ArrayList<>();
                arguments.add("give");
                return arguments;

            }
        }

        if(args.length == 3) {
            if(args[1].equalsIgnoreCase("give") && args[0].equalsIgnoreCase("box")) {
                FileConfiguration boxes = Ashura.boxes.getConfig();
                return new ArrayList<>(boxes.getConfigurationSection("boxes").getKeys(false));

            }
        }


        return null;
    }
}
