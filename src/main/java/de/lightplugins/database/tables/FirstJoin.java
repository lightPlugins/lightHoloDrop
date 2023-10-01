package de.lightplugins.database.tables;

import de.lightplugins.master.Ashura;
import de.lightplugins.util.TableStatements;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class FirstJoin {

    private final String tableName = "first_join";

    public void createMoneyTable() {

        String tableStatement;
        TableStatements tableStatements = new TableStatements(Ashura.getInstance);
        FileConfiguration settings = Ashura.settings.getConfig();

        tableStatement = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "uuid TEXT,"
                + "name TEXT,"
                + "joinDate DATETIME,"
                + "PRIMARY KEY (uuid))";

        if(settings.getBoolean("mysql.enable")) {
            tableStatement = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                    + "uuid TEXT,"
                    + "name TEXT,"
                    + "joinDate DATETIME,"
                    + "PRIMARY KEY (uuid))";
        }

        tableStatements.createTableStatement(tableStatement);
    }

    public CompletableFuture<Boolean> playerAlreadyExists(String uuid) {

        return CompletableFuture.supplyAsync(() -> {

            Connection connection = null;
            PreparedStatement ps = null;

            try {

                connection = Ashura.getInstance.ds.getConnection();

                ps = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE uuid=?");
                ps.setString(1, uuid);

                ResultSet rs = ps.executeQuery();

                if(rs.next()) {
                    String playerUUID = rs.getString("uuid");

                    OfflinePlayer offlinePlayer = Bukkit.getPlayer(playerUUID);

                    return offlinePlayer != null;
                }
                return false;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;

            } finally {
                if(connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if(ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
