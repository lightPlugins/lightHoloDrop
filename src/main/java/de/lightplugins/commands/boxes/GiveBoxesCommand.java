package de.lightplugins.commands.boxes;

import de.lightplugins.boxes.BoxesManager;
import de.lightplugins.master.Ashura;
import de.lightplugins.util.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class GiveBoxesCommand extends SubCommand {
    @Override
    public String getName() {
        return "box";
    }

    @Override
    public String getDescription() {
        return "Give a box";
    }

    @Override
    public String getSyntax() {
        return "/a box give <boxID> <playerName> <amount>";
    }

    @Override
    public boolean perform(Player player, String[] args) {

        FileConfiguration boxes = Ashura.boxes.getConfig();
        Set<String> boxIDs = boxes.getConfigurationSection("boxes").getKeys(false);

        if(args.length == 5) {

            String commandArg = args[1];

            try {

                int amount = Integer.parseInt(args[4]);

                if(!commandArg.equalsIgnoreCase("give")) {
                    Ashura.util.sendMessage(player, "&cDiesen Befehl gibt es nicht &7!");
                    return false;
                }

                if (!player.hasPermission("ashura.admin.boxes.give")) {
                    Ashura.util.sendMessage(player, "&cDu hast für diesen Befehl keine Berechtigung&7!");
                    return false;
                }

                if(!boxIDs.contains(args[2])) {
                    Ashura.util.sendMessage(player, "&cDie angegebene Box existiert nicht&7!");

                    return false;
                }

                BoxesManager boxesManager = new BoxesManager();

                ItemStack is = boxesManager.getBox(args[2]);

                if(is == null) {
                    Ashura.util.sendMessage(player,
                            "&cEs gibt probleme in der boxes.yml &7- &cBitte überprüfen&7!");
                    return false;
                }

                is.setAmount(amount);

                Player target = Bukkit.getPlayer(args[3]);

                if(target == null) {
                    Ashura.util.sendMessage(player, "&cDer angegebene Spieler wurde nicht gefunden&7!");
                    return false;
                }


                if(Ashura.util.isInventoryFull(target)) {
                    target.getWorld().dropItemNaturally(target.getLocation(), is);
                    Ashura.util.sendMessage(player, "&7Der Spieler #dc143d" + target.getName() + " &7hat #dc143d"
                            + args[2] + " &7bekommen. &cDa sein Inventar voll war, wurde dem Spieler die Box gedroppt&7.");
                    return false;
                }

                target.getInventory().addItem(is);
                Ashura.util.sendMessage(player, "&7Der Spieler #dc143d" + target.getName() + " &7hat #dc143d"
                        + args[2] + " &7bekommen.");
                return false;



            } catch (NumberFormatException e) {
                Ashura.util.sendMessage(player, "&cDie angegebene Menge ist keine Zahl&7!");
                return false;
            }
        }
        return false;
    }
}
