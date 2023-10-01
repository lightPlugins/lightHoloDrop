package de.lightplugins.boxes;

import de.lightplugins.enums.PersistentDataPaths;
import de.lightplugins.master.Ashura;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoxesManager {


    public ItemStack getBox(String boxID) {

        FileConfiguration boxes = Ashura.boxes.getConfig();

        for(String path : boxes.getConfigurationSection("boxes").getKeys(false)) {

            if(path.equalsIgnoreCase(boxID)) {


                Material material = Material.valueOf(
                        Objects.requireNonNull(boxes.getString("boxes." + boxID + ".material")).toUpperCase());

                ItemStack is = new ItemStack(material, 1);
                ItemMeta im = is.getItemMeta();

                if(im == null) {
                    return null;
                }


                String displayName = boxes.getString("boxes." + path + ".displayName");
                List<String> lore = new ArrayList<>();

                for(String loreLine : boxes.getStringList("boxes." + path + ".lore"))  {
                    lore.add(Ashura.colorTranslation.hexTranslation(loreLine));
                }

                int customModeData = boxes.getInt("boxes." + path + ".customModelData");

                if(customModeData != 0) {
                    im.setCustomModelData(customModeData);
                }

                im.setLore(lore);
                im.setDisplayName(Ashura.colorTranslation.hexTranslation(displayName));

            /*
                Item glow enable ?

             */

                boolean glow = boxes.getBoolean("boxes." + path + ".glow");

                if(glow) {
                    im.addEnchant(Enchantment.DURABILITY, 1, true);
                    im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }

                PersistentDataContainer container = im.getPersistentDataContainer();
                NamespacedKey namespacedKeyValue =
                        new NamespacedKey(Ashura.getInstance, PersistentDataPaths.ashuraBoxID.getType());

                if(namespacedKeyValue.getKey().equalsIgnoreCase(PersistentDataPaths.ashuraBoxID.getType())) {
                    container.set(namespacedKeyValue, PersistentDataType.STRING, path);
                }

                is.setItemMeta(im);
                return is;
                
            }
        }
        return null;
    }
}
