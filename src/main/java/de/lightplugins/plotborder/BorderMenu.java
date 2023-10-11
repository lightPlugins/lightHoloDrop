package de.lightplugins.plotborder;

import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.configuration.Configuration;
import com.plotsquared.core.configuration.ConfigurationUtil;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.BlockBucket;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotManager;
import com.plotsquared.core.plot.flag.FlagContainer;
import com.sk89q.worldedit.world.block.BlockState;
import de.lightplugins.master.Ashura;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BorderMenu implements InventoryProvider {


    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("BANK_LEVEL_MENU")
            .provider(new BorderMenu())
            .size(3,9)
            .title(Ashura.colorTranslation.hexTranslation("#dc143d&lBORDER"))
            .manager(Ashura.borderMenuManager)
            .build();


    @Override
    public void init(Player player, InventoryContents inventoryContents) {

        Pagination pagination = inventoryContents.pagination();
        FileConfiguration border = Ashura.border.getConfig();

        int borderCounter =
                Objects.requireNonNull(border.getConfigurationSection("border.borders")).getKeys(false).size();

        ClickableItem[] levelItems = new ClickableItem[borderCounter];

        ItemStack glass  = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta glassMeta = glass.getItemMeta();
        assert glassMeta != null;

        glassMeta.setDisplayName(" ");
        glass.setItemMeta(glassMeta);

        inventoryContents.fill(ClickableItem.empty(glass));

        int i = 0;
        for(String path : Objects.requireNonNull(border.getConfigurationSection("border.borders")).getKeys(false)) {
            i ++;

            Material material = Material.valueOf(border.getString("border.borders." + path + ".material"));
            String permission = border.getString("border.borders." + path + ".needed-permission");
            String title = border.getString("border.borders." + path + ".displayname");

            ItemStack is = new ItemStack(material);
            ItemMeta im = is.getItemMeta();
            assert im != null;
            assert permission != null;
            im.setDisplayName(Ashura.colorTranslation.hexTranslation(title));

            List<String> lore = new ArrayList<>();

            border.getStringList("border.borders." + path + ".lore").forEach(line -> {
                lore.add(Ashura.colorTranslation.hexTranslation(line));
            });

            im.setLore(lore);
            is.setItemMeta(im);


            if(!player.hasPermission(permission)) {

                Material material1 = Material.valueOf(border.getString("border.no-permission-item.material"));
                String displayname1 = Ashura.colorTranslation.hexTranslation(border.getString(
                        "border.no-permission-item.displayName"));
                String extraLore = Ashura.colorTranslation.hexTranslation(border.getString(
                        "border.no-permission-item.extra-lore"));
                is.setType(material1);
                im.setDisplayName(displayname1);

                List<String> noPermLore = new ArrayList<>();
                noPermLore.add(extraLore);

                is.setItemMeta(im);

            }


            levelItems[i - 1] = ClickableItem.of(is, e -> {


                if(!player.hasPermission(permission)) {
                    Ashura.util.sendMessage(player, "&cDu hast für diese Border keine Rechte.");
                    return;
                }

                PlotAPI plotAPI = new PlotAPI();

                Plot plot = plotAPI.wrapPlayer(player.getName()).getCurrentPlot();

                if(plot == null) {
                    Ashura.util.sendMessage(player, "&cDu stehst auf keinem Plot!");
                    return;
                }

                if(!plot.isOwner(player.getUniqueId())) {
                    Ashura.util.sendMessage(player, "&cDu kannst nur auf deinem Plot die Border ändern!");
                    return;
                }

                String mat = border.getString("border.borders." + path + ".material");
                BlockBucket test = ConfigurationUtil.BLOCK_BUCKET.parseString(mat + ":0");

                if(plot.getConnectedPlots().size() > 1) {
                    for(Plot currentPlot : plot.getConnectedPlots()) {
                        currentPlot.getPlotModificationManager().setComponent("border", test.toPattern(), null, null);
                    }
                } else {
                    plot.getPlotModificationManager().setComponent("border", test.toPattern(), null, null);
                }
            });
        }

        pagination.setItems(levelItems);
        pagination.setItemsPerPage(7);

        pagination.addToIterator(inventoryContents.newIterator(SlotIterator.Type.HORIZONTAL, 1,1));

        ItemStack previousPage = new ItemStack(Material.ARROW);
        ItemMeta previousPageMeta = previousPage.getItemMeta();
        assert previousPageMeta != null;
        previousPageMeta.setDisplayName(Ashura.colorTranslation.hexTranslation("&7Zurück"));

        previousPage.setItemMeta(previousPageMeta);

        ItemStack nextPage = new ItemStack(Material.ARROW);
        ItemMeta nextPageMeta = previousPage.getItemMeta();
        assert nextPageMeta != null;
        nextPageMeta.setDisplayName(Ashura.colorTranslation.hexTranslation("&7Nächste Seite"));

        nextPage.setItemMeta(nextPageMeta);

        ItemStack backButton = new ItemStack(Material.REDSTONE);
        ItemMeta backButtonMeta = previousPage.getItemMeta();
        assert backButtonMeta != null;
        backButtonMeta.setDisplayName(Ashura.colorTranslation.hexTranslation("&cSchließen"));

        backButton.setItemMeta(backButtonMeta);

        inventoryContents.set(2, 2, ClickableItem.of(previousPage, e -> {
            INVENTORY.open(player, pagination.previous().getPage());
        }));

        inventoryContents.set(2, 6, ClickableItem.of(nextPage, e -> {
            INVENTORY.open(player, pagination.next().getPage());
        }));

        inventoryContents.set(2, 4, ClickableItem.of(backButton, e -> {
            player.closeInventory();
        }));

    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
