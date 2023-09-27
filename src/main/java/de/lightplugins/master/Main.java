package de.lightplugins.master;

import com.zaxxer.hikari.HikariDataSource;
import de.lightplugins.database.DatabaseConnection;
import de.lightplugins.files.FileManager;
import de.lightplugins.util.ColorTranslation;
import de.lightplugins.util.Util;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main getInstance;

    public HikariDataSource ds;
    public DatabaseConnection hikari;

    public static FileManager settings;
    public static FileManager messages;

    public static ColorTranslation colorTranslation;
    public static Util util;


    public void onLoad() {

        getInstance = this;

        settings = new FileManager(this, "settings.yml");
        messages = new FileManager(this, "messages.yml");

        colorTranslation = new ColorTranslation();

    }

    public void onEnable() {

        /*  Initalize Database and connect driver  */

        this.hikari = new DatabaseConnection();
        hikari.connectToDataBaseViaMariaDB();



    }

    public void onDisable() {


    }


}