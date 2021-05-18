package de.julius.BanSystem.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.julius.BanSystem.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Ban implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        File banlist = new File("plugins/BanSystem", "banlist.yml");
        FileConfiguration banlistconf = YamlConfiguration.loadConfiguration(banlist);

        File banmessage = new File("plugins/BanSystem", "banmessage.yml");
        FileConfiguration banmessageconf = YamlConfiguration.loadConfiguration(banmessage);

        File banreason = new File("plugins/BanSystem", "reasons.yml");
        FileConfiguration banreasonconf = YamlConfiguration.loadConfiguration(banreason);

        if (!(sender instanceof Player)) {
            ConsoleCommandSender p = Bukkit.getConsoleSender();
            if (p.hasPermission("BanSystem.ban")) {
                if (args.length == 2) {
                    if (tryParse(args[1]) != null) {
                        int r = Integer.parseInt(args[1]);
                        if (r > 0 && r < 21 || r == 100) {
                            if (Bukkit.getPlayer(args[0]) != null) {
                                Player t = Bukkit.getPlayer(args[0]);
                                UUID tUUID = t.getUniqueId();
                                String reasonint = Integer.toString(r);
                                String strduration = banreasonconf.getString(reasonint + ".duration");
                                String reason = banreasonconf.getString(reasonint + ".reason");
                                if(!valueCheck(tUUID)) {
                                    if (tryParse(strduration) != null) {
                                        long duration = (Long.parseLong(strduration) * 1000 * 60);
                                        if (duration <= 0) {
                                            duration = 0;
                                            try {
                                                PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO " +
                                                        "Bans(Name, UUID, Reason, Duration, Ban, Unban) VALUES (?,?,?,?,?,?)");
                                                ps.setString(1, t.getDisplayName());
                                                ps.setString(2, tUUID.toString().replace("-", ""));
                                                ps.setString(3, reason);
                                                ps.setString(4, Long.toString(duration));
                                                ps.setString(5, getCurrentDate());
                                                ps.setString(6, "Never");
                                                ps.executeUpdate();
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }

                                        } else {



                                        }
                                    } else {
                                        System.out.println("No number");
                                    }
                                } else {
                                    System.out.println("Already banned");
                                }
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
        String UUID;

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
        return null;
    }


    /*public String longToDate (long l) {

        System.out.println(l);
        System.out.println(TimeUnit.MILLISECONDS.toDays(l));
        long days = TimeUnit.MILLISECONDS.toDays(l);
        long years = days / 365;
        long months = days / 30;
        long hours = TimeUnit.MILLISECONDS.toHours(l) - TimeUnit.DAYS.toHours(days);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(l) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l));

        String time = String.format("%d years %d months %d days %d hours %d minutes %d seconds", years, months, days, hours, minutes, seconds);

        return time;
    }*/

    public String getCurrentDate () {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }

    public String getFutureDate (long l) {
        long current = System.currentTimeMillis();
        long then = l + current;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime ldt = new Timestamp(then).toLocalDateTime();

        return dtf.format(ldt);
    }

    public Boolean valueCheck (UUID u) {
        String strUUID = u.toString();
        PreparedStatement ps = null;
        try {
            ps = MySQL.getConnection().prepareStatement("SELECT UUID FROM Bans WHERE UUID = ?");
            ps.setString(1, strUUID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
