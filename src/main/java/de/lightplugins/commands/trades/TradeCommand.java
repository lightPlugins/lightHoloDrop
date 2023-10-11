package de.lightplugins.commands.trades;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import teammt.villagerguiapi.classes.VillagerInventory;
import teammt.villagerguiapi.classes.VillagerTrade;

import java.util.ArrayList;
import java.util.List;

public class TradeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;


        List<VillagerTrade> trades = new ArrayList<>(); // Create a new list of items
        // Create a trade with 1 DIRT and 1 DIRT for GLASS with 10 max uses
        trades.add(new VillagerTrade(new ItemStack(Material.DIRT), new ItemStack(Material.DIRT),
                new ItemStack(Material.GLASS), 10));
        // Create a trade for 1 DIRT for 1 GLASS with 15 max uses
        trades.add(new VillagerTrade(new ItemStack(Material.DIRT), new ItemStack(Material.GLASS), 15));
        // Create a trade for 1 ANVIL for 1 GLASS with 10 max uses
        trades.add(new VillagerTrade(new ItemStack(Material.ANVIL), new ItemStack(Material.GLASS), 10));
        // Create the object
        VillagerInventory cr = new VillagerInventory(trades, player);

        // Set the top title
        cr.setName(ChatColor.translateAlternateColorCodes('&', "&aHello"));

        // Open for player
        cr.open();


        return false;
    }
}
