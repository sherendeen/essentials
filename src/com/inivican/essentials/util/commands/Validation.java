package com.inivican.essentials.util.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.inivican.essentials.constants.Msg;

public class Validation {


	/**
	 * checks validity of the command being issued
	 * @param commandSender the sender of the command
	 * @param args arguments following the base command
	 * @param permission e.g. essentials.home
	 * @return boolean value true if command is 
	 * issued by someone with permission,
	 * is a player, and the arguments exceed zero.
	 */
	public static boolean satisfiesBasicCommandReqs(final CommandSender commandSender,
			final String[] args, final String permission) {
		
		// can the command be executed
		if (!commandSender.hasPermission(permission) && !commandSender.isOp()) {
			return false;
		}

		if (!(commandSender instanceof Player)) {
			System.out.println(Msg.DEBUG + "Console cannot issue /home* commands." + commandSender.getName());
			return false;
		}

		if (args.length < 1) {
			commandSender.sendMessage(Msg.ERR + "Insufficient arguments for command.");
			return false;
		}
		
		
		return true;
	}
	
}
