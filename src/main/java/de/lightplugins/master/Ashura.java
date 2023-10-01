package de.lightplugins.master;

import com.zaxxer.hikari.HikariDataSource;
import de.lightplugins.commands.AshuraCommandManager;
import de.lightplugins.database.DatabaseConnection;
import de.lightplugins.events.BoxesOpener;
import de.lightplugins.events.OnFirstJoin;
import de.lightplugins.events.OnJoinCommands;
import de.lightplugins.files.FileManager;
import de.lightplugins.util.ColorTranslation;
import de.lightplugins.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public class Ashura extends JavaPlugin {

    public static Ashura getInstance;

    public HikariDataSource ds;
    public DatabaseConnection hikari;

    public static FileManager settings;
    public static FileManager messages;
    public static FileManager boxes;

    public static ColorTranslation colorTranslation;
    public static Util util;


    public void onLoad() {

        Bukkit.getLogger().log(Level.FINE, "[lightAshura] Starting lightAshura");

        getInstance = this;

        settings = new FileManager(this, "settings.yml");
        messages = new FileManager(this, "messages.yml");
        boxes = new FileManager(this, "boxes.yml");

        colorTranslation = new ColorTranslation();

        util = new Util();

    }

    public void onEnable() {

        /*  Initalize Database and connect driver  */

        this.hikari = new DatabaseConnection();
        // hikari.connectToDataBaseViaMariaDB();

        Bukkit.getLogger().log(Level.INFO, "[lightAshura] Register Commands and TabCompletions ...");
        Objects.requireNonNull(this.getCommand("a")).setExecutor(new AshuraCommandManager());

        PluginManager pm = Bukkit.getPluginManager();
        //pm.registerEvents(new OnJoinCommands(), this);
        pm.registerEvents(new OnFirstJoin(), this);
        pm.registerEvents(new BoxesOpener(), this);

        Bukkit.getLogger().log(Level.FINE, "[lightAshura] Successfully started lightAshrua.");

    }

    public void onDisable() { }
}