package de.lightplugins.events;
import de.lightplugins.database.tables.FirstJoin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnFirstJoin implements Listener {


    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        FirstJoin firstJoin = new FirstJoin();

    }
}
