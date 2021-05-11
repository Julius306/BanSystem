package de.julius.BanSystem;

import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("The plugin is now starting");
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin is now stopping");
    }
}
