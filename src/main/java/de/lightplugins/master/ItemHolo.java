package de.lightplugins.master;

import com.willfp.eco.core.EcoPlugin;
import de.lightplugins.commands.AshuraCommandManager;
import de.lightplugins.commands.tabcompletion.AshuraTabCompletion;
import de.lightplugins.itemdrop.ItemDrop;
import de.lightplugins.files.FileManager;
import de.lightplugins.util.ColorTranslation;
import de.lightplugins.util.Util;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

/*
 * ----------------------------------------------------------------------------
 *  This software and its source code, including text, graphics, and images,
 *  are the sole property of lightPlugins ("Author").
 *
 *  Unauthorized reproduction or distribution of this software, or any portion
 *  of it, may result in severe civil and criminal penalties, and will be
 *  prosecuted to the maximum extent possible under the law.
 * ----------------------------------------------------------------------------
 */

/**
 * This software is developed and maintained by lightPlugins.
 * For inquiries, please contact @discord: .light4coding.
 *
 * @version 1.0
 * @since 2023-11-14
 */

public class ItemHolo extends JavaPlugin {

    public static ItemHolo getInstance;
    public static final String consolePrefix = "§r[light§cHolo§r] ";

    public static FileManager settings;
    public static FileManager messages;

    public static ColorTranslation colorTranslation;
    public static Util util;
    public Boolean isEco = false;
    public Boolean isItemsAdder = false;


    public void onLoad() {

        Bukkit.getConsoleSender().sendMessage(consolePrefix + "Starting lightItemHolo");

        getInstance = this;

        settings = new FileManager(this, "settings.yml");
        messages = new FileManager(this, "messages.yml");

        colorTranslation = new ColorTranslation();
        util = new Util();

    }

    public void onEnable() {


        Bukkit.getConsoleSender().sendMessage("\n" +
                "  _      _____ _____ _    _ _______ §c_    _  ____  _      ____  §r\n" +
                " | |    |_   _/ ____| |  | |__   __§c| |  | |/ __ \\| |    / __ \\ §r\n" +
                " | |      | || |  __| |__| |  | |  §c| |__| | |  | | |   | |  | |§r\n" +
                " | |      | || | |_ |  __  |  | |  §c|  __  | |  | | |   | |  | |§r\n" +
                " | |____ _| || |__| | |  | |  | |  §c| |  | | |__| | |___| |__| |§r\n" +
                " |______|_____\\_____|_|  |_|  |_|  §c|_|  |_|\\____/|______\\____/ §r\n" +
                "\n\n" + ChatColor.RESET +
                "      Version: §c1.0.0    §rAuthor: §clightPlugins\n" +
                "      §rThank you for using lightHolo. If you came in trouble feel free to join\n" +
                "      my §cDiscord §rserver: https://discord.gg/G2EuzmSW\n");

        /*  SetUp Eco plugins */

        for(Plugin pluginName : Bukkit.getServer().getPluginManager().getPlugins()) {

            if (pluginName.getName().equals("EcoItems")) {
                Bukkit.getConsoleSender().sendMessage(consolePrefix + "Successfully hooked into §cEcoItems");
                isEco = true;
            }

            /*  SetUp ItemsAdder plugin */

            if (pluginName.getName().equals("ItemsAdder")) {
                Bukkit.getConsoleSender().sendMessage(consolePrefix + "Successfully hooked into §cItemsAdder");
                isItemsAdder = true;
            }
        }

        /*######################################*/

        Bukkit.getConsoleSender().sendMessage(consolePrefix + "Register §cCommands §rand §cTabCompletions §r...");

        Objects.requireNonNull(this.getCommand("lholo")).setExecutor(new AshuraCommandManager(this));
        Objects.requireNonNull(this.getCommand("lholo")).setTabCompleter(new AshuraTabCompletion());

        Bukkit.getConsoleSender().sendMessage();

        Bukkit.getConsoleSender().sendMessage(consolePrefix + "Register §cvisual item names §rwith §ccolors §r...");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ItemDrop(), this);

        Bukkit.getConsoleSender().sendMessage(consolePrefix + "Successfully started " + consolePrefix);

    }

    public void onDisable() { }
}