package de.julius.BanSystem.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class banreason {

    public static void createBanFile () {

        File banreasons = new File("plugins/BanSystem", "reasons.yml");
        FileConfiguration banreasonsconf = YamlConfiguration.loadConfiguration(banreasons);

        if(!banreasons.exists()) {

            banreasonsconf.set(" ", "If you want to change the duration to permanent, just set it to 0." +
                    " The duration is measured in seconds.");

            banreasonsconf.set("1.reason", "KillAura");
            banreasonsconf.set("1.duration", 100);

            banreasonsconf.set("2.reason", "No Knockback");
            banreasonsconf.set("2.duration", 100);

            banreasonsconf.set("3.reason", "Fly Hack");
            banreasonsconf.set("3.duration", 100);

            banreasonsconf.set("4.reason", "Speedhack");
            banreasonsconf.set("4.duration", 100);

            banreasonsconf.set("5.reason", "Slight insult");
            banreasonsconf.set("5.duration", 100);

            banreasonsconf.set("6.reason", "Insult");
            banreasonsconf.set("6.duration", 100);

            banreasonsconf.set("7.reason", "Racism");
            banreasonsconf.set("7.duration", 100);

            banreasonsconf.set("8.reason", "Terrorism");
            banreasonsconf.set("8.duration", 100);

            banreasonsconf.set("9.reason", "Provocation");
            banreasonsconf.set("9.duration", 100);

            banreasonsconf.set("10.reason", "Spreading hate");
            banreasonsconf.set("10.duration", 100);

            banreasonsconf.set("11.reason", "Griefing");
            banreasonsconf.set("11.duration", 100);

            banreasonsconf.set("12.reason", "Bad behavior");
            banreasonsconf.set("12.duration", 100);

            banreasonsconf.set("13.reason", "Extremely bad behavior");
            banreasonsconf.set("13.duration", 100);

            banreasonsconf.set("14.reason", "Bug using");
            banreasonsconf.set("14.duration", 100);

            banreasonsconf.set("15.reason", "Spamming");
            banreasonsconf.set("15.duration", 100);

            banreasonsconf.set("16.reason", "Ban evasion");
            banreasonsconf.set("16.duration", 100);

            banreasonsconf.set("17.reason", "X-Ray");
            banreasonsconf.set("17.duration", 100);

            banreasonsconf.set("18.reason", "Inappropriate Building");
            banreasonsconf.set("18.duration", 100);

            banreasonsconf.set("19.reason", "Hacking");
            banreasonsconf.set("19.duration", 100);

            banreasonsconf.set("20.reason", "Extreme Hacking");
            banreasonsconf.set("20.duration", 100);

            banreasonsconf.set("100.reason", "Permanent");
            banreasonsconf.set("100.duration", 100);

            try {
                banreasonsconf.save(banreasons);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
