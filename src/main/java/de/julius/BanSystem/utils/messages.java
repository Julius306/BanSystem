package de.julius.BanSystem.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class messages {

    public static void createMessagesFile () {
        File messages = new File("plugins/BanSystem", "messages.yml");
        FileConfiguration messagesconfig = YamlConfiguration.loadConfiguration(messages);
        if(!messages.exists()) {
            try {
                messagesconfig.save(messages);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
