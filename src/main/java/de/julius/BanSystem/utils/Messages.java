package de.julius.BanSystem.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Messages {

    public static void createMessagesTable () {

        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS messages (name VARCHAR(100), message VARCHAR(1000),PRIMARY KEY (name))");
            ps.executeUpdate();
            createMessage("Ban Message", "§4You are banned from the Server \n\n §fReason:§c %reason%" +
                    "\n§fExpires:§c %expires% \n\n§eServerNetwork.xyz");
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    private static void createMessage (String name, String message) {
        PreparedStatement st = null;
        try {
            st = MySQL.getConnection().prepareStatement("INSERT IGNORE INTO Messages VALUES (?,?)");
            st.setString(1, name);
            st.setString(2, message);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
