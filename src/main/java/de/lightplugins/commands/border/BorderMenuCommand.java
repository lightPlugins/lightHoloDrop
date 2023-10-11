package de.lightplugins.commands.border;

import de.lightplugins.master.Ashura;
import de.lightplugins.plotborder.BorderMenu;
import de.lightplugins.util.SubCommand;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;

public class BorderMenuCommand extends SubCommand {
    @Override
    public String getName() {
        return "border";
    }

    @Override
    public String getDescription() {
        return "open the plotsquared border menu";
    }

    @Override
    public String getSyntax() {
        return "/a border";
    }

    @Override
    public boolean perform(Player player, String[] args) throws ExecutionException, InterruptedException {

        if(args.length != 1) {
            Ashura.util.sendMessage(player, "&cFalscher Befehl, meintest du /a border ?");
            return false;
        }

        BorderMenu.INVENTORY.open(player);

        return false;
    }
}
