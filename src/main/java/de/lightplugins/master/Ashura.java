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

public class Ashura extends JavaPlugin {

    public static Ashura getInstance;

    public static FileManager settings;
    public static FileManager messages;
    public static FileManager boxes;
    public static FileManager trades;
    public static FileManager border;

    public static ColorTranslation colorTranslation;
    public static Util util;
    public Boolean isWorldGuard = false;
    public Boolean isEcoJobs = false;

    public static InventoryManager borderMenuManager;



    public void onLoad() {

        Bukkit.getLogger().log(Level.FINE, "[lightAshura] Starting lightAshura");

        getInstance = this;

        settings = new FileManager(this, "settings.yml");
        messages = new FileManager(this, "messages.yml");
        boxes = new FileManager(this, "boxes.yml");
        trades = new FileManager(this, "trades.yml");
        border = new FileManager(this, "borders.yml");

        colorTranslation = new ColorTranslation();

        util = new Util();

    }

    public void onEnable() {

        /*  SetUp WorldGuard */

        for(Plugin pluginName : Bukkit.getServer().getPluginManager().getPlugins()) {

            if (pluginName.getName().equals("WorldGuard")) {
                Plugin newPlugin = this.getServer().getPluginManager().getPlugin("WorldGuard");
                if (newPlugin instanceof WorldGuardPlugin) {
                    getLogger().info("[lightAshura] Successfully hooked into WorldGuard");
                    isWorldGuard = true;
                }
            }
        }

        /*######################################*/

        Bukkit.getLogger().log(Level.INFO, "[lightAshura] Register Commands and TabCompletions ...");

        Objects.requireNonNull(this.getCommand("a")).setExecutor(new AshuraCommandManager(this));
        Objects.requireNonNull(this.getCommand("a")).setTabCompleter(new AshuraTabCompletion());


        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ItemDrop(), this);

        borderMenuManager = new InventoryManager(this);
        borderMenuManager.init();

        Bukkit.getLogger().log(Level.FINE, "[lightAshura] Successfully started lightAshrua.");

    }

    public void onDisable() { }
}