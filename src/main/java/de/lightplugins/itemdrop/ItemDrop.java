package de.lightplugins.itemdrop;

import com.willfp.eco.core.items.Items;
import de.lightplugins.master.ItemHolo;
import de.lightplugins.util.ItemGlow;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ItemDrop implements Listener {

    private Map<UUID, BukkitRunnable> itemTimers = new HashMap<>();

    @EventHandler
    public void onBlockBreakEvent(ItemSpawnEvent e) {

        FileConfiguration settings = ItemHolo.settings.getConfig();

        settings.getStringList("settings.itemHolo.whitelist.worlds").forEach(singleWorld -> {
            if(Objects.requireNonNull(settings.getString(
                    "settings.itemHolo.whitelist.mode")).equalsIgnoreCase("whitelist")) {

                if(singleWorld.equalsIgnoreCase(Objects.requireNonNull(e.getLocation().getWorld()).getName())) {


                    Item item = e.getEntity();
                    item.setPickupDelay(settings.getInt("settings.itemHolo.pickUpDelay"));

                    UUID itemUUID = item.getUniqueId();
                    ItemStack itemStack = item.getItemStack();

                    if(itemStack.getItemMeta() == null) {
                        return;
                    }

                    if(!itemTimers.containsKey(itemUUID)) {
                        startTimer(item);
                        return;
                    }
                    return;
                }
            }

            if(Objects.requireNonNull(settings.getString(
                    "settings.itemHolo.whitelist.mode")).equalsIgnoreCase("blacklist")) {

                if(singleWorld.equalsIgnoreCase(Objects.requireNonNull(e.getLocation().getWorld()).getName())) {
                    return;

                }

                Item item = e.getEntity();

                item.setPickupDelay(settings.getInt("settings.itemHolo.pickUpDelay"));

                UUID itemUUID = item.getUniqueId();
                ItemStack itemStack = item.getItemStack();

                if(itemStack.getItemMeta() == null) {
                    return;
                }

                if(!itemTimers.containsKey(itemUUID)) {
                    startTimer(item);
                }
            }
        });
    }

    @EventHandler
    public void onItemMerge(ItemMergeEvent e) {

        FileConfiguration settings = ItemHolo.settings.getConfig();

        settings.getStringList("settings.itemHolo.whitelist.worlds").forEach(singleWorld -> {

            if(Objects.requireNonNull(settings.getString(
                    "settings.itemHolo.whitelist.mode")).equalsIgnoreCase("whitelist")) {

                Item item = e.getEntity();
                UUID itemUUID = item.getUniqueId();

                itemTimers.remove(itemUUID);
                startTimer(item);
                return;

            }

            if(Objects.requireNonNull(settings.getString(
                    "settings.itemHolo.whitelist.mode")).equalsIgnoreCase("blacklist")) {

                return;
            }

            Item item = e.getEntity();
            UUID itemUUID = item.getUniqueId();

            item.setPickupDelay(settings.getInt("settings.itemHolo.pickUpDelay"));

            itemTimers.remove(itemUUID);
            startTimer(item);

        });
    }

    private void startTimer(Item item) {

        FileConfiguration settings = ItemHolo.settings.getConfig();
        int timer = settings.getInt("settings.itemHolo.timer");
        String displayName = settings.getString("settings.itemHolo.displayName");
        boolean enableDefaultColor = settings.getBoolean("settings.itemHolo.glow.enableByDefault");
        ChatColor defaultColor = ChatColor.valueOf(settings.getString("settings.itemHolo.glow.defaultColor"));
        Particle particle = Particle.valueOf(settings.getString("settings.itemHolo.particle.type"));
        boolean enableParticle = settings.getBoolean("settings.itemHolo.particle.enable");
        int particleAmount = settings.getInt("settings.itemHolo.particle.amount");

        if(displayName == null) {
            displayName = "Error in Settings.yml";
        }

        UUID itemUUID = item.getUniqueId();
        String finalDisplayName = displayName;

        BukkitRunnable timerTask = new BukkitRunnable() {

            int timerTicks = timer + 1;

            @Override
            public void run() {

                timerTicks--;

                int stackAmount = item.getItemStack().getAmount();

                if(timer >= (timerTicks)) {
                    Location itemLocation = item.getLocation();

                    Objects.requireNonNull(itemLocation.getWorld()).spawnParticle(Particle.SPELL_INSTANT, itemLocation, 10);
                }

                if(timerTicks <= 0) {

                    Location location = item.getLocation();

                    if(location.getWorld() == null) {
                        item.remove();
                        cancel();
                        itemTimers.remove(itemUUID);
                        return;
                    }

                    if(enableParticle) {
                        location.getWorld().spawnParticle(particle, location, particleAmount);
                    }

                    item.remove();
                    cancel();
                    itemTimers.remove(itemUUID);
                    return;
                }
                String itemName = "";

                item.setCustomName(null);
                item.setCustomNameVisible(false);

                if(item.getItemStack().getItemMeta() == null) {
                    item.setCustomName(item.getName());
                }

                itemName = ItemHolo.colorTranslation.hexTranslation(finalDisplayName
                        .replace("#timer#", String.valueOf(timerTicks))
                        .replace("#amount#", String.valueOf(stackAmount))
                        .replace("#item#", item.getName()));

                if(item.getItemStack().getType().equals(Material.AIR)) {
                    cancel();
                    itemTimers.remove(itemUUID);
                    return;
                }
                if(item.getItemStack().getItemMeta().hasDisplayName()) {
                    itemName = ItemHolo.colorTranslation.hexTranslation(finalDisplayName
                            .replace("#timer#", String.valueOf(timerTicks))
                            .replace("#amount#", String.valueOf(stackAmount))
                            .replace("#item#", item.getItemStack().getItemMeta().getDisplayName()));

                }

                item.setCustomName(itemName);
                item.setCustomNameVisible(true);

                if(Objects.requireNonNull(settings.getConfigurationSection(
                        "settings.itemHolo.glow.customItems")).getKeys(false).isEmpty()) {
                    if(enableDefaultColor) {
                        item.setGlowing(true);
                        ItemGlow.setGlowColor(defaultColor, item);
                        return;
                    }
                }

                for(String path : Objects.requireNonNull(settings.getConfigurationSection(
                        "settings.itemHolo.glow.customItems")).getKeys(false)) {

                    String defaultPath = "settings.itemHolo.glow.customItems." + path + ".";
                    String mode = settings.getString(defaultPath + "mode");
                    String data = settings.getString(defaultPath + "data");
                    ChatColor color = ChatColor.valueOf(settings.getString(defaultPath + "color"));

                    if(mode == null) {
                        return;
                    }

                    if(data == null) {
                        return;
                    }

                    /*
                     *
                     *  VANILLA MODE
                     *
                     */

                    if(mode.equalsIgnoreCase("vanilla")) {
                        ItemStack is = new ItemStack(Material.valueOf(data), 1);

                        if(is.getItemMeta() == null) {
                            return; // Maybe more debugging here -> wrong config format
                        }

                        if(item.getItemStack().getType().equals(is.getType())) {
                            item.setGlowing(true);
                            ItemGlow.setGlowColor(color, item);
                            return;
                        }
                    }

                    /*
                     *
                     *  CUSTOMMODELDATA MODE
                     *
                     */

                    if(mode.equalsIgnoreCase("modeldata")) {

                        ItemStack is = new ItemStack(Material.valueOf(data), 1);

                        if(is.getItemMeta() == null) {
                            return; // Maybe more debugging here -> wrong config format
                        }

                        if(!is.getItemMeta().hasCustomModelData()) {
                            return; // Maybe more debugging here -> wrong config format
                        }

                        int customModelData = is.getItemMeta().getCustomModelData();
                        int modelDataConfig = Integer.parseInt(data);

                        if(customModelData == modelDataConfig) {
                            item.setGlowing(true);
                            ItemGlow.setGlowColor(color, item);

                            return;

                        }
                    }

                    /*
                     *
                     *  ECOITEMS MODE
                     *
                     */

                    if(mode.equalsIgnoreCase("ecoitems") && ItemHolo.getInstance.isEco) {

                        String finalData = "ecoitems:" + data;

                        ItemStack is = Items.lookup(finalData).getItem();

                        if(is.getItemMeta() == null) {
                            return; // Maybe more debugging here -> wrong config format
                        }

                        if(!is.getItemMeta().hasCustomModelData()) {
                            return; // Maybe more debugging here -> wrong config format
                        }

                        int customModelData = is.getItemMeta().getCustomModelData();
                        int modelDataConfig = Integer.parseInt(data);

                        if(customModelData == modelDataConfig) {
                            item.setGlowing(true);
                            ItemGlow.setGlowColor(color, item);

                            return;

                        }
                    }

                    /*
                     *
                     *  ECOARMOR MODE
                     *
                     */

                    if(mode.equalsIgnoreCase("ecoarmor") && ItemHolo.getInstance.isEco) {

                        String finalData = "ecoarmor:" + data;

                        ItemStack is = Items.lookup(finalData).getItem();

                        if(is.getItemMeta() == null) {
                            return; // Maybe more debugging here -> wrong config format
                        }

                        if(!is.getItemMeta().hasCustomModelData()) {
                            return; // Maybe more debugging here -> wrong config format
                        }

                        int customModelData = is.getItemMeta().getCustomModelData();
                        int modelDataConfig = Integer.parseInt(data);

                        if(customModelData == modelDataConfig) {
                            item.setGlowing(true);
                            ItemGlow.setGlowColor(color, item);

                            return;

                        }
                    }

                    /*
                     *
                     *  ITEMSADDER MODE
                     *
                     */

                    if(mode.equalsIgnoreCase("itemsadder") && ItemHolo.getInstance.isItemsAdder) {

                        CustomStack customStack = CustomStack.getInstance(data);

                        if(customStack != null) {
                            ItemStack is = customStack.getItemStack();

                            if(is.getItemMeta() == null) {
                                return; // Maybe more debugging here -> wrong config format
                            }

                            if(!is.getItemMeta().hasCustomModelData()) {
                                return; // Maybe more debugging here -> wrong config format
                            }

                            int customModelData = is.getItemMeta().getCustomModelData();
                            int modelDataConfig = Integer.parseInt(data);

                            if(customModelData == modelDataConfig) {
                                item.setGlowing(true);
                                ItemGlow.setGlowColor(color, item);

                                return;

                            }
                        }
                    }
                }

                if(enableDefaultColor) {
                    item.setGlowing(true);
                    ItemGlow.setGlowColor(defaultColor, item);
                }
            }
        };

        itemTimers.put(itemUUID, timerTask);
        timerTask.runTaskTimer(ItemHolo.getInstance, 0, 20);
    }


    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {

        FileConfiguration settings = ItemHolo.settings.getConfig();

        settings.getStringList("settings.itemHolo.whitelist.worlds").forEach(singleWorld -> {

            if(Objects.requireNonNull(settings.getString(
                    "settings.itemHolo.whitelist.mode")).equalsIgnoreCase("whitelist")) {

                Item item = e.getItemDrop();
                item.setPickupDelay(settings.getInt("settings.itemHolo.pickUpDelay"));
                UUID itemUUID = item.getUniqueId();
                ItemStack itemStack = item.getItemStack();

                if (itemStack.getItemMeta() == null) {
                    return;
                }

                if(!itemTimers.containsKey(itemUUID)) {
                    startTimer(item);
                    return;
                }
                return;
            }

            if(Objects.requireNonNull(settings.getString(
                    "settings.itemHolo.whitelist.mode")).equalsIgnoreCase("blacklist")) {
                return;
            }

            Item item = e.getItemDrop();
            UUID itemUUID = item.getUniqueId();
            ItemStack itemStack = item.getItemStack();

            if (itemStack.getItemMeta() == null) {
                return;
            }

            if(!itemTimers.containsKey(itemUUID)) {
                startTimer(item);
            }
        });
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
    }
}
