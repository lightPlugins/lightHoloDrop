package de.lightplugins.commands.tabcompletion;

import de.lightplugins.master.Light;
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
            arguments.add("reload");
            return arguments;
        }
        return null;
    }
}
