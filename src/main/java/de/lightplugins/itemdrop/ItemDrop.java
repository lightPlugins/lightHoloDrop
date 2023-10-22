package de.lightplugins.itemdrop;

import de.lightplugins.master.Ashura;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class ItemDrop implements Listener {

    private Map<UUID, BukkitRunnable> itemTimers = new HashMap<>();

    @EventHandler
    public void onBlockBreakEvent(BlockDropItemEvent e) {

        e.getItems().forEach(item -> {

            UUID itemUUID = item.getUniqueId();
            ItemStack itemStack = item.getItemStack();

            if(itemStack.getItemMeta() == null) {
                return;
            }

            if(!itemTimers.containsKey(itemUUID)) {
                startTimer(item);
            }
        });
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

        Entity entity = e.getEntity();
        Location entityLocation = e.getEntity().getLocation();

        for(ItemStack itemStack : e.getDrops()) {

            if(itemStack.getItemMeta() == null) {
                break;
            }

            Item item = entityLocation.getWorld().dropItemNaturally(entityLocation, itemStack);

            UUID itemUUID = item.getUniqueId();

            if(!itemTimers.containsKey(itemUUID)) {
                startTimer(item);
            }
        }
        e.getDrops().clear();
    }

    @EventHandler
    public void onItemMerge(ItemMergeEvent e) {
        Item item = e.getEntity();
        UUID itemUUID = item.getUniqueId();

        itemTimers.remove(itemUUID);
        startTimer(item);

    }

    private void startTimer(Item item) {

        UUID itemUUID = item.getUniqueId();

        BukkitRunnable timerTask = new BukkitRunnable() {

            int timerTicks = 61;

            @Override
            public void run() {

                timerTicks--;

                int stackAmount = item.getItemStack().getAmount();

                if(timerTicks <= 0) {

                    Location location = item.getLocation();

                    if(location.getWorld() == null) {
                        item.remove();
                        cancel();
                        itemTimers.remove(itemUUID);
                        Bukkit.getLogger().log(Level.SEVERE, "TEST 4");
                        return;
                    }

                    location.getWorld().spawnParticle(Particle.LAVA, location, 20);
                    item.remove();
                    cancel();
                    itemTimers.remove(itemUUID);
                    Bukkit.getLogger().log(Level.SEVERE, "TEST 5");
                    return;
                }
                String itemName = "";

                Bukkit.getLogger().log(Level.SEVERE, "TEST 1");

                item.setCustomName(null);
                item.setCustomNameVisible(false);

                if(item.getItemStack().getItemMeta() == null) {
                    item.setCustomName(item.getName());
                    Bukkit.getLogger().log(Level.SEVERE, "TEST 2");
                }

                itemName = Ashura.colorTranslation.hexTranslation(
                        "&7[#dc143d" + timerTicks + "&7] " + stackAmount + " &7x " + item.getName());

                if(item.getItemStack().getType().equals(Material.AIR)) {
                    cancel();
                    itemTimers.remove(itemUUID);
                    Bukkit.getLogger().log(Level.SEVERE, "TEST 2");
                    return;
                }
                if(item.getItemStack().getItemMeta().hasDisplayName()) {
                    itemName = Ashura.colorTranslation.hexTranslation(
                            "&7[#dc143d" + timerTicks + "&7] " + stackAmount + " &7x " + item.getItemStack().getItemMeta().getDisplayName());
                    Bukkit.getLogger().log(Level.SEVERE, "TEST 3");

                }

                item.setCustomName(itemName);
                item.setCustomNameVisible(true);

            }
        };

        itemTimers.put(itemUUID, timerTask);
        timerTask.runTaskTimer(Ashura.getInstance, 0, 20);
    }


    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {

        Item item = e.getItemDrop();
        UUID itemUUID = item.getUniqueId();
        ItemStack itemStack = item.getItemStack();

        if (itemStack.getItemMeta() == null) {
            return;
        }

        if(!itemTimers.containsKey(itemUUID)) {

            startTimer(item);

        }
    }



    @EventHandler
    public void onItemPickUp(EntityPickupItemEvent e) {

        Item item = e.getItem();
        UUID itemUUID = item.getUniqueId();

        if(itemTimers.containsKey(itemUUID)) {

            BukkitRunnable timerTask = itemTimers.get(itemUUID);
            timerTask.cancel();
            itemTimers.remove(itemUUID);
        }

        Bukkit.getLogger().log(Level.WARNING, "TEST size " + itemTimers.size());
    }
}
