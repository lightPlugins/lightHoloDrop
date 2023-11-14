package de.lightplugins.util;

import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;

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

public abstract class SubCommand {

    public abstract String getName();

    public abstract  String getDescription();

    public abstract String getSyntax();

    public abstract boolean perform(Player player, String args[]) throws ExecutionException, InterruptedException;
}
