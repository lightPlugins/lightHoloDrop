package de.lightplugins.commands.trades;

import de.lightplugins.master.Ashura;
import de.lightplugins.traders.EcoLookup;
import de.lightplugins.util.SubCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import teammt.villagerguiapi.classes.VillagerInventory;
import teammt.villagerguiapi.classes.VillagerTrade;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CustomTradeCommand extends SubCommand {
    @Override
    public String getName() {
        return "trades";
    }

    @Override
    public String getDescription() {
        return "open a custom trade gui";
    }

    @Override
    public String getSyntax() {
        return "/a trades <tradeMenuName>";
    }

    @Override
    public boolean perform(Player player, String[] args) throws ExecutionException, InterruptedException {

        FileConfiguration trades = Ashura.trades.getConfig();

        if(!player.hasPermission("ashura.command.trades")) {
            Ashura.util.sendMessage(player, "&cDu hast für diesen Befehl keine Berechtigung&7!");
            return false;
        }

        if(args.length != 2) {
            Ashura.util.sendMessage(player,
                    "&cDieser Befehl existiert nicht. Meintest du #dc143d/a trades <trader> &c?");
            return false;
        }

        String tradeName = args[1];

        for(String path : Objects.requireNonNull(trades.getConfigurationSection("trades")).getKeys(false)) {

            if(tradeName.equalsIgnoreCase(path)) {

                boolean isTradeEnabled = trades.getBoolean("trades." + path + ".enable");

                if(!isTradeEnabled) {
                    Ashura.util.sendMessage(player,
                            "&cDieser Trader ist momentan #dc143ddeaktiviert&c.");
                }

                String inventoryName = Ashura.colorTranslation.hexTranslation(
                        trades.getString("trades." + path + ".name"));

                EcoLookup ecoLookup = new EcoLookup();
                CompletableFuture<List<VillagerTrade>> completableFuture =
                        ecoLookup.getVillagerTrade(trades.getStringList("trades." + path + ".offers"));

                List<VillagerTrade> villagerTrades = completableFuture.get();

                VillagerInventory villagerInventory = new VillagerInventory(villagerTrades, player);
                villagerInventory.setName(inventoryName);
                villagerInventory.open();

                return false;

            }
        }

        Ashura.util.sendMessage(player,
                "&cDieser Trader wurde nicht gefunden");

        return false;
    }
}
