package de.lightplugins.master;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.willfp.eco.core.Eco;
import com.willfp.eco.core.EcoPlugin;
import de.lightplugins.commands.AshuraCommandManager;
import de.lightplugins.commands.tabcompletion.AshuraTabCompletion;
import de.lightplugins.itemdrop.ItemDrop;
import de.lightplugins.files.FileManager;
import de.lightplugins.util.ColorTranslation;
import de.lightplugins.util.Util;
import dev.lone.itemsadder.api.ItemsAdder;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public class ItemHolo extends JavaPlugin {

    public static ItemHolo getInstance;

    public static FileManager settings;
    public static FileManager messages;

    public static ColorTranslation colorTranslation;
    public static Util util;
    public Boolean isEco = false;
    public Boolean isItemsAdder = false;

    public Boolean isOraxen = false;

    public static InventoryManager borderMenuManager;



    public void onLoad() {

        Bukkit.getLogger().log(Level.FINE, "[lightItemHolo] Starting lightItemHolo");

        getInstance = this;

        settings = new FileManager(this, "settings.yml");
        messages = new FileManager(this, "messages.yml");

        colorTranslation = new ColorTranslation();
        util = new Util();

    }

    public void onEnable() {

        /*  SetUp WorldGuard */

        for(Plugin pluginName : Bukkit.getServer().getPluginManager().getPlugins()) {

            if (pluginName.getName().equals("EcoItems")) {
                Plugin newPlugin = this.getServer().getPluginManager().getPlugin("EcoItems");
                if (newPlugin instanceof EcoPlugin) {
                    getLogger().info("[lightItemHolo] Successfully hooked into EcoItems");
                    isEco = true;
                }
            }

            if (pluginName.getName().equals("ItemsAdder")) {
                Plugin newPlugin = this.getServer().getPluginManager().getPlugin("ItemsAdder");
                if (newPlugin instanceof ItemsAdder) {
                    getLogger().info("[lightItemHolo] Successfully hooked into ItemsAdder");
                    isItemsAdder = true;
                }
            }

            if (pluginName.getName().equals("Oraxen")) {
                Plugin newPlugin = this.getServer().getPluginManager().getPlugin("Oraxen");
                if (newPlugin instanceof ItemsAdder) {
                    getLogger().info("[lightItemHolo] Successfully hooked into Oraxen");
                    isItemsAdder = true;
                }
            }
        }

        /*######################################*/

        Bukkit.getLogger().log(Level.INFO, "[lightItemHolo] Register Commands and TabCompletions ...");

        Objects.requireNonNull(this.getCommand("lholo")).setExecutor(new AshuraCommandManager(this));
        Objects.requireNonNull(this.getCommand("lholo")).setTabCompleter(new AshuraTabCompletion());

        Bukkit.getLogger().log(Level.INFO, "[lightItemHolo] Enable visual item names with colors ...");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ItemDrop(), this);

        Bukkit.getLogger().log(Level.FINE, "[lightItemHolo] Successfully started lightItemHolo.");

    }

    public void onDisable() { }
}