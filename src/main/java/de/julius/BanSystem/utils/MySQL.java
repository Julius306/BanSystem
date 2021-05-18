package de.julius.BanSystem.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    FileConfiguration mysqlfile = YamlConfiguration.loadConfiguration(new File("plugins/BanSystem", "MySQL.yml"));

    public static Connection connection;

    private final String host = mysqlfile.getString("host");
    private final String port = mysqlfile.getString("port");
    private final String database = mysqlfile.getString("database");
    private final String username = mysqlfile.getString("username");
    private final String password = mysqlfile.getString("password");

    public boolean isConnected() {
        return connection != null;
    }

    public void connect() throws SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port +
                    "/" + database, username, password);
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static Connection getConnection() {return connection;}

}
