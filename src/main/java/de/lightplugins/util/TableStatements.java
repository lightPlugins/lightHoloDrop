package de.lightplugins.util;

import de.lightplugins.master.Ashura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableStatements {
    public Ashura plugin;
    public TableStatements(Ashura plugin) {
        this.plugin = plugin;
    }

    public void createTableStatement(String statement) {

        Connection connection = null;
        PreparedStatement ps = null;

        try {

            connection = plugin.ds.getConnection();

            ps = connection.prepareStatement(statement);
            ps.executeUpdate();
            //connection.commit();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();

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
    }
}
