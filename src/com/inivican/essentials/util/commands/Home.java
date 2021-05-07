package com.inivican.essentials.util.commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.inivican.essentials.Essentials;
import com.inivican.essentials.constants.MsgPrefix;
import com.inivican.essentials.objects.PlayerHome;
import com.inivican.essentials.util.Homes;

public class Home implements CommandExecutor {

	private Essentials essentials;
	private HashMap<String, Long> coolDowns = new HashMap<String, Long>();
	private final int COOLDOWN_TIME = 90;
	
	public Home(final Essentials essentials) {
		this.essentials = essentials;
	}

	@Override
	public boolean onCommand(final CommandSender commandSender, final Command cmd, 
			final String arg, final String[] args) {
		
		// can the command be executed
		if (commandSender.hasPermission("essentials.home") 
				|| commandSender.isOp() && commandSender instanceof Player ) {
			
			if (args.length < 1) {
				return false;
			}
			
			Player player = (Player) commandSender;
			
			switch(args[0].toLowerCase()) {
			case "set":
				
				if (!args[1].equalsIgnoreCase("") && args[1] != null &&
						!Homes.playerHomeExists(player.getUniqueId(), args[1], 
								Homes.getPlayerHomesByPlayer(
										player.getUniqueId(),
										Essentials.playerHomes))
						) {
					
					String worldName = player.getLocation().getWorld().getName();
					
					System.out.println(MsgPrefix.DEBUG + "serializing home on [world:" +worldName+"]");
					
					//String homeName, int x, int y, int z, UUID playerUUID, String worldName
					PlayerHome ph = new PlayerHome(args[1], 
							player.getLocation().getBlockX(), 
							player.getLocation().getBlockY(),
							player.getLocation().getBlockZ(), 
							player.getUniqueId().toString(), 
							worldName);
					
					Essentials.playerHomes.add(ph);
					PlayerHome.serialize(ph, PlayerHome.getAutoName(ph));
					//TODO: ACCOUNT FOR NAME COLLISIONS?
					
					commandSender.sendMessage( MsgPrefix.OK + "Home '"+args[1]+"' set." );
				}
				
				break;
			case "goto":
			case "go":
			case "tp":
				System.out.println(MsgPrefix.DEBUG + "entered goto/go/tp");
				
				if (!args[1].equalsIgnoreCase("") && args[1] != null ) {
					
					System.out.println(MsgPrefix.DEBUG +"arg was not invalid");
					
					if (Essentials.doesPlayerHomeExist(player.getUniqueId(),
							args[1])) {
						
						System.out.println(MsgPrefix.DEBUG +"player home DOES exist"); 
						
						if (coolDowns.containsKey(player.getName())) {
							
							System.out.println(MsgPrefix.DEBUG +"cooldowns contains key");
							
							long secondsLeft = getSecondsLeft(player);
							
							if (secondsLeft > 0) {
								commandSender.sendMessage(MsgPrefix.ERR +
										"You cannot teleport right now. " + 
								secondsLeft + " seconds left.");
								return true;
							}
							
							coolDowns.put(player.getName(), 
									System.currentTimeMillis());
						} else {
							coolDowns.put(player.getName(), System.currentTimeMillis());
						}
						
						//World currentWorld = player.getWorld();
						PlayerHome ph = Essentials.getPlayerHome(
								player.getUniqueId(), args[1]);
						
						player.teleport(new Location(
								essentials.getServer().getWorld(
										ph.getWorldName()), ph.getX(), 
								ph.getY(), ph.getZ()));
					}
					
				}
				
				break;
			case "del":
				
				if (!args[1].equalsIgnoreCase("") && args[1] != null) {
					if (Essentials.doesPlayerHomeExist(player.getUniqueId(), args[1])) {
						
						PlayerHome ph = Essentials.getPlayerHome(
								player.getUniqueId(), args[1]);
						
						Essentials.deletePlayerHome(PlayerHome.getAutoName(ph));
						Essentials.playerHomes.remove(ph);
					}
				}
				
				break;
			case "list":
			case "l":
				
				ArrayList<PlayerHome> homes = Homes.getPlayerHomesByPlayer(
						player.getUniqueId(), Essentials.playerHomes);
				if (homes.size() > 0 ||homes != null ) {
					commandSender.sendMessage(MsgPrefix.INFO + " *** Your Homes ***");
					for (int i = 0; i < homes.size(); i++) {
						commandSender.sendMessage(MsgPrefix.LIST + i + ". "+homes.get(i).getFewerDetails());
					}
					
				}
				
				break;
				default:
					commandSender.sendMessage(MsgPrefix.ERR + "Insufficient correct arguments given.");
			}
			
			return true;
		} else {
		
			return false;
		}
	}

	private long getSecondsLeft(Player player) {
		return ((coolDowns.get(player.getName())/1000) + COOLDOWN_TIME) - (System.currentTimeMillis() / 1000);
	}

}
