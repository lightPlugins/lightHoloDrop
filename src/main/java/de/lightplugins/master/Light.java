package de.lightplugins.master;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import de.lightplugins.commands.AshuraCommandManager;
import de.lightplugins.commands.tabcompletion.AshuraTabCompletion;
import de.lightplugins.itemdrop.ItemDrop;
import de.lightplugins.files.FileManager;
import de.lightplugins.util.ColorTranslation;
import de.lightplugins.util.Util;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public class Light extends JavaPlugin {

    public static Light getInstance;

    public static FileManager settings;
    public static FileManager messages;

    public static ColorTranslation colorTranslation;
    public static Util util;
    public Boolean isWorldGuard = false;
    public Boolean isEcoJobs = false;

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

            if (pluginName.getName().equals("WorldGuard")) {
                Plugin newPlugin = this.getServer().getPluginManager().getPlugin("WorldGuard");
                if (newPlugin instanceof WorldGuardPlugin) {
                    getLogger().info("[lightItemHolo] Successfully hooked into WorldGuard");
                    isWorldGuard = true;
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