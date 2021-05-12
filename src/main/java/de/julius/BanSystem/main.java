package de.julius.BanSystem;

import de.julius.BanSystem.commands.Ban;
import de.julius.BanSystem.utils.banreason;
import de.julius.BanSystem.utils.messages;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class main extends JavaPlugin {

    public static main plugin;

    @Override
    public void onEnable() {
        getLogger().info("The plugin is now starting");
        messages.createMessagesFile();
        banreason.createBanFile();

        getCommand("ban").setExecutor(new Ban());
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin is now stopping");
    }

    public static main Main () {
        return plugin;
    }

}
