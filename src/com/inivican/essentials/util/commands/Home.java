package com.inivican.essentials.util.commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.inivican.essentials.Essentials;
import com.inivican.essentials.constants.Msg;
import com.inivican.essentials.objects.PlayerHome;
import com.inivican.essentials.util.Homes;

import net.md_5.bungee.api.ChatColor;

public class Home implements CommandExecutor {

	private Essentials essentials;
	private HashMap<String, Long> coolDowns = new HashMap<String, Long>();
	private final int COOLDOWN_TIME = 90;

	public Home(final Essentials essentials) {
		this.essentials = essentials;
	}

	@Override
	public boolean onCommand(final CommandSender commandSender, final Command cmd, 
			final String arg,
			final String[] args) {

		if(!Validation.satisfiesBasicCommandReqs(commandSender, args, 
				"essentials.home")) {
			return false;
		}

		Player player = (Player) commandSender;

		switch (args[0].toLowerCase()) {
		case "set":

			if (!args[1].equalsIgnoreCase("") && args[1] != null && !Homes.playerHomeExists(player.getUniqueId(),
					args[1], Homes.getPlayerHomesByPlayer(player.getUniqueId(), Essentials.playerHomes))) {

				String worldName = player.getLocation().getWorld().getName();

				System.out.println(Msg.DEBUG + "serializing home on [world:" + worldName + "]");

				// String homeName, int x, int y, int z, UUID playerUUID, String worldName
				PlayerHome ph = new PlayerHome(args[1], player.getLocation().getBlockX(),
						player.getLocation().getBlockY(), player.getLocation().getBlockZ(),
						player.getUniqueId().toString(), worldName);

				Essentials.playerHomes.add(ph);
				PlayerHome.serialize(ph, PlayerHome.getAutoName(ph));
				// TODO: ACCOUNT FOR NAME COLLISIONS?

				commandSender.sendMessage(Msg.OK + "Home '" + args[1] + "' set.");
			}

			break;
		case "goto":
		case "go":
		case "tp":
			homeTeleporation(commandSender, args, player);

			break;
		case "del":

			if (args[1].equalsIgnoreCase("") && args[1] == null) {
				break;
			}

			if (Essentials.doesPlayerHomeExist(player.getUniqueId(), args[1])) {

				PlayerHome ph = Essentials.getPlayerHome(player.getUniqueId(), args[1]);

				Essentials.deletePlayerHome(PlayerHome.getAutoName(ph));
				Essentials.playerHomes.remove(ph);
			}

			break;
		case "list":
		case "l":

			ArrayList<PlayerHome> homes = Homes.getPlayerHomesByPlayer(player.getUniqueId(), Essentials.playerHomes);

			if (homes.size() < 1 || homes == null) {
				break;
			}

			commandSender.sendMessage(Msg.INFO + " *** Your Homes ***");
			for (int i = 0; i < homes.size(); i++) {
				commandSender.sendMessage(i + ". " + homes.get(i).toString());
			}

			break;
		default:
			commandSender.sendMessage(Msg.ERR + "Insufficient correct arguments given.");
		}

		return true;

	}

	private void homeTeleporation(final CommandSender commandSender, final String[] args, Player player) {
		System.out.println(Msg.DEBUG + "entered goto/go/tp");

		if (args[1] == null || args[1].equalsIgnoreCase("") ) {
			commandSender.sendMessage(Msg.ERR + "Invalid home name! correct usage: /home tp NameOfYourHome");
			return;
		}

		System.out.println(Msg.DEBUG + "arg was not invalid");

		if (!Essentials.doesPlayerHomeExist(player.getUniqueId(), args[1])) {
			commandSender.sendMessage(Msg.ERR + "You don't have a home by that name");
			return;
		}

		System.out.println(Msg.DEBUG + "player home DOES exist");

		if (coolDowns.containsKey(player.getName())) {

			System.out.println(Msg.DEBUG + "Home cooldowns contains key");

			long secondsLeft = getSecondsLeft(player);

			if (secondsLeft > 0) {
				commandSender.sendMessage(
						Msg.ERR + "You cannot teleport right now. " + ChatColor.RED + secondsLeft + ChatColor.GRAY + " seconds left");
				return;
			}

			coolDowns.put(player.getName(), System.currentTimeMillis());
		} else {
			coolDowns.put(player.getName(), System.currentTimeMillis());
		}

		// World currentWorld = player.getWorld();
		PlayerHome ph = Essentials.getPlayerHome(player.getUniqueId(), args[1]);

		player.teleport(
				new Location(essentials.getServer().getWorld(ph.getWorldName()), 
						ph.getX(), ph.getY(), ph.getZ()));
	}

	private long getSecondsLeft(Player player) {
		return ((coolDowns.get(player.getName()) / 1000) + COOLDOWN_TIME) - (System.currentTimeMillis() / 1000);
	}

}
