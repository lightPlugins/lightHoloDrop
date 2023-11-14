package de.lightplugins.enums;

import de.lightplugins.master.ItemHolo;
import org.bukkit.configuration.file.FileConfiguration;

/*
 * ----------------------------------------------------------------------------
 *  This software and its source code, including text, graphics, and images,
 *  are the sole property of lightPlugins ("Author").
 *
 *  Unauthorized reproduction or distribution of this software, or any portion
 *  of it, may result in severe civil and criminal penalties, and will be
 *  prosecuted to the maximum extent possible under the law.
 * ----------------------------------------------------------------------------
 */

/**
 * This software is developed and maintained by lightPlugins.
 * For inquiries, please contact @discord: .light4coding.
 *
 * @version 1.0
 * @since 2023-11-14
 */

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
