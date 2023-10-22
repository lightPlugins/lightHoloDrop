package de.lightplugins.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemGlow {

    public static List<Team> teams = new ArrayList<>();
    public static Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();

    public static void setGlowColor(ChatColor color, Entity entity) {
        String name = "HD" + color.getChar();
        Team team = scoreboard.getTeam(name);
        if (team == null) {
            team = scoreboard.registerNewTeam(name);
            teams.add(team);
        }
        team.setColor(color);
        team.addEntry(entity.getUniqueId().toString());
    }

    public static void unregister() {
        for (Team team : teams) {
            team.unregister();
        }
    }

}
