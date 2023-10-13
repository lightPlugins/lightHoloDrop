package de.lightplugins.events;

import de.lightplugins.master.Ashura;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemDrop implements Listener {


    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {

        Item item = e.getItemDrop();


        final int[] duration = {60};

        new BukkitRunnable() {
            @Override
            public void run() {

                duration[0]--;

                int stackAmount = e.getItemDrop().getItemStack().getAmount();

                if(duration[0] <= 0) {

                    Location location = item.getLocation();

                    if(location.getWorld() == null) {
                        item.remove();
                        cancel();
                        return;
                    }

                    location.getWorld().spawnParticle(Particle.LAVA, location, 20);
                    item.remove();
                    cancel();
                }
                String itemName = "";

                item.setCustomName(null);
                item.setCustomNameVisible(false);

                if(item.getItemStack().getItemMeta() == null) {
                    item.setCustomName(item.getName());
                }

                itemName = Ashura.colorTranslation.hexTranslation(
                        "&7[#dc143d" + duration[0] + "&7] " + stackAmount + " &7x " + item.getName());

                if(item.getItemStack().getItemMeta().hasDisplayName()) {
                    itemName = Ashura.colorTranslation.hexTranslation(
                            "&7[#dc143d" + duration[0] + "&7] " + stackAmount + " &7x " + item.getItemStack().getItemMeta().getDisplayName());

                }

                item.setCustomName(itemName);
                item.setCustomNameVisible(true);

            }
        }.runTaskTimer(Ashura.getInstance, 0, 20);

    }

}
