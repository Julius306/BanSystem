package de.julius.BanSystem.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MySQLFile {

    public static void createMySQLFile () {
        File mysqlfile = new File("plugins/BanSystem", "MySQL.yml");
        FileConfiguration mysqlconf = YamlConfiguration.loadConfiguration(mysqlfile);
        if(!mysqlfile.exists()) {

            mysqlconf.set("host", "localhost");
            mysqlconf.set("port", "3306");
            mysqlconf.set("database", "bansystem");
            mysqlconf.set("username", "root");
            mysqlconf.set("password", "");

            try {
                mysqlconf.save(mysqlfile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
