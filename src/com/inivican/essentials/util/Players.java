package com.inivican.essentials.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * 
 * @author Inivican (sherendeen)
 * @since 2021-04-27
 *
 */
public class Players {

    public static String getPlayerNameFromUuid(final UUID uuid) {
        final Player player = Bukkit.getPlayer(uuid);
        return player.getDisplayName();
    }
	
}
