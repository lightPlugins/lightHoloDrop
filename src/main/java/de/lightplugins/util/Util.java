package de.lightplugins.util;

import de.lightplugins.enums.MessagePath;
import de.lightplugins.master.Main;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Util {

    public void sendMessage(Player player, String message) {
        String prefix = MessagePath.Prefix.getPath();
        player.sendMessage(Main.colorTranslation.hexTranslation(prefix + message));
    }
    /*  Send a message List to player without Prefix  */

    public void sendMessageList(Player player, List<String> list) {
        for(String s : list) {
            player.sendMessage(Main.colorTranslation.hexTranslation(s));
        }
    }

    // Return scaled doubled by 2 scales

    public double fixDouble(double numberToFix) {
        BigDecimal bd = new BigDecimal(numberToFix).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public boolean isNumber(String number) {
        try {
            Double dummy = Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    // Return double formated as String
    public String finalFormatDouble(double numberToRound) { return formatDouble(fixDouble(numberToRound));
    }

    private String formatDouble(double numberToFormat) {
        return String.format("%,.2f", numberToFormat);
    }
}
