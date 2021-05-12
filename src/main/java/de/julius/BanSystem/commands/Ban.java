package de.julius.BanSystem.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class Ban implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            ConsoleCommandSender p = Bukkit.getConsoleSender();
            if (p.hasPermission("BanSystem.ban")) {
                if (args.length == 2) {
                    if (tryParse(args[1]) != null) {
                        int r = Integer.parseInt(args[1]);
                        if (r > 0 && r < 21 || r == 100) {
                            if (Bukkit.getPlayer(args[0]) != null) {
                                Player t = Bukkit.getPlayer(args[0]);
                                String target = t.toString();
                                UUID tUUID = t.getUniqueId();
                                System.out.println("Online");
                            } else if (getUUID(args[0]) != null) {
                                String tUUID = getUUID(args[0]);
                                Player t = Bukkit.getPlayer(tUUID);
                                String target = (args[0]);
                                System.out.println("Offline");
                            } else {
                                p.sendMessage("Player not existing");
                                System.out.println("Error");
                            }
                        } else {
                            System.out.println("Ban reasons");
                        }
                    } else {
                        System.out.println("null");
                    }
                } else {
                    System.out.println("usage");
                }
            } else {
                System.out.println("no permissions");
            }
        }

        return false;
    }

    public static Integer tryParse(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getUUID(String s) {

        String strURL = "https://api.mojang.com/users/profiles/minecraft/" + s;
        System.out.println(strURL);
        String UUID = null;

        try {
            URL url = new URL(strURL);
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement js = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject obj = js.getAsJsonObject();
            UUID = obj.get("id").getAsString();
            return UUID;
        } catch (IOException e) {
            System.out.println("Error");
        }
        return UUID;
    }

    public void banReason() {

    }
}
