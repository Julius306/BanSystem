package de.julius.BanSystem.listeners;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.julius.BanSystem.commands.Ban;
import de.julius.BanSystem.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BannedPlayerJoinEvent implements Listener {



    @EventHandler
    public void bannedPlayerCheck (AsyncPlayerPreLoginEvent e) {

        String UUID = getUUID(e.getName());
        if(valueCheck(UUID)){
            PreparedStatement ps = null;
            try {
                ps = MySQL.getConnection().prepareStatement("SELECT * FROM messages WHERE name = ?");
                ps.setString(1, "Ban Message");
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    String message = rs.getString("message");
                    ps = MySQL.getConnection().prepareStatement("SELECT * FROM bans WHERE UUID = ?");
                    ps.setString(1, UUID.replace("-", ""));
                    ResultSet result = ps.executeQuery();
                    result.next();
                    message = message.replace("%reason%", result.getString("Reason"));
                    message = message.replace("%expires%", result.getString("Unban"));
                    e.setKickMessage(message);
                } else {
                    e.setKickMessage("MySQL not working");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
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

    public Boolean valueCheck (String strUUID) {
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
