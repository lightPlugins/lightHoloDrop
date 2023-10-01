package de.lightplugins.events;

import de.lightplugins.master.Ashura;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class OnJoinCommands implements Listener {




    @EventHandler
    public void onJoinDoCommand(PlayerLoginEvent event) {

        Player player = event.getPlayer();

        FileConfiguration settings = Ashura.settings.getConfig();

    }
}
