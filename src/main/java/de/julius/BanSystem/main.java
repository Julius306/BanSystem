package de.julius.BanSystem;

import de.julius.BanSystem.commands.Ban;
import de.julius.BanSystem.listeners.BannedPlayerJoinEvent;
import de.julius.BanSystem.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class main extends JavaPlugin {

    public static main plugin;

    public MySQL SQL;

    @Override
    public void onEnable() {
        MySQLFile.createMySQLFile();
        if(connectSQL()) {
            getLogger().info("The plugin is now starting");
            createBanTable();
            Messages.createMessagesTable();
            BanReason.createBanFile();

            getCommand("ban").setExecutor(new Ban());

            Bukkit.getPluginManager().registerEvents(new BannedPlayerJoinEvent(), this);

        } else {
            getLogger().info("MySQL couldn't connect");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin is now stopping");
        SQL.disconnect();
    }

    public static main Main () {
        return plugin;
    }

    public boolean connectSQL() {
        this.SQL = new MySQL();
        try {
            SQL.connect();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }


    public void createBanTable () {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Bans " +
                    "(Name VARCHAR(100),UUID VARCHAR(100),Reason VARCHAR(100),Duration INT(100),Ban VARCHAR(100),Unban VARCHAR(100),PRIMARY KEY (Name))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
