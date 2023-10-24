package de.lightplugins.enums;

import de.lightplugins.master.ItemHolo;
import org.bukkit.configuration.file.FileConfiguration;

public enum MessagePath {

    Prefix("prefix"),

    ;

    private final String path;

    MessagePath(String path) { this.path = path; }
    public String getPath() {
        FileConfiguration paths = ItemHolo.messages.getConfig();
        try {
            return paths.getString(this.path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

}
