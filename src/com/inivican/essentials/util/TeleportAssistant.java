package com.inivican.essentials.util;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.inivican.essentials.Essentials;
import com.inivican.essentials.constants.MsgPrefix;
import com.inivican.essentials.util.commands.Validation;

import net.md_5.bungee.api.ChatColor;

public class TeleportAssistant implements CommandExecutor {

	private HashMap<String, Long> coolDowns = new HashMap<String, Long>();
	private final int COOLDOWN_TIME = 90;
	private Essentials essentials;
	
	public TeleportAssistant(final Essentials essentials) {
		this.essentials = essentials;
	}
	
	@Override
	public boolean onCommand(final CommandSender commandSender, 
			final Command cmd, 
			final String arg, final String[] args) {
		
		if(!Validation.satisfiesBasicCommandReqs(commandSender, args, 
				"essentials.tpa")) {
			return false;
		}
		Player player = (Player)commandSender;
		Player player2 = this.essentials.getServer().getPlayer(args[1]);
		
		// is the a
		if (args[0] == null || args[0].equalsIgnoreCase("")) {
			
			commandSender.sendMessage(MsgPrefix.ERR + ChatColor.GRAY 
					+ "Invalid player-name! correct usage: /tpa " + ChatColor.WHITE+ " PlayerName");
			return false;
		}
		
		// make sure the target player is online
		if (player2 == null || !player2.isOnline()) {
			commandSender.sendMessage(MsgPrefix.ERR 
					+ "You cannot teleport to a player that is not online.");
			return false;
		}
		
		/**check the cool downs
		* 
		*/
		if (!coolDowns.containsKey(player.getName())) {
			coolDowns.put(player.getName(), System.currentTimeMillis());
		} else {
			
			System.out.println(MsgPrefix.DEBUG + "Direct Teleport cooldowns contains key");

			long secondsLeft = getSecondsLeft(player);

			if (secondsLeft > 0) {
				commandSender.sendMessage(//color
						MsgPrefix.ERR + "You cannot teleport right now. " + ChatColor.YELLOW + secondsLeft + ChatColor.GRAY + " seconds left");
				return false;
			}
		 
			coolDowns.put(player.getName(), System.currentTimeMillis());
		}
		

		player.teleport(
				new Location(essentials.getServer().getWorld(player.getWorld().getName()), 
						player2.getLocation().getX(), player2.getLocation().getY(), player2.getLocation().getZ()));
		
		
		System.out.println(MsgPrefix.DEBUG + "arg was not invalid");
		
		return true;
	}

	private long getSecondsLeft(Player player) {
		return ((coolDowns.get(player.getName()) / 1000) + COOLDOWN_TIME) - (System.currentTimeMillis() / 1000);
	}
	
}
