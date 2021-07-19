package com.inivican.essentials;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.plugin.java.JavaPlugin;

import com.inivican.essentials.constants.MsgPrefix;
import com.inivican.essentials.objects.PlayerHome;
import com.inivican.essentials.util.TeleportAssistant;
import com.inivican.essentials.util.commands.Home;


/**
 * 
 * @author Seth G. R. Herendeen (inivican)
 * 
 */
public class Essentials extends JavaPlugin {
	
	public static ArrayList<PlayerHome> playerHomes = new ArrayList<PlayerHome>();
	
	public static boolean doesPlayerHomeExist(final UUID uuid, final String homeName) {
		
		if (playerHomes.size() > 0) {
			
			for (PlayerHome ph : playerHomes) {
				if (ph.getPlayerUUID().equals(uuid.toString()) 
						&& ph.getHomeName().equalsIgnoreCase(homeName)) {
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	@Nullable
	public static PlayerHome getPlayerHome(final UUID uuid, final String homeName) {
		
		for (PlayerHome ph : playerHomes) {
			if (ph.getPlayerUUID().equals(uuid.toString()) 
					&& ph.getHomeName().equalsIgnoreCase(homeName)) {
				return ph;
			}
		}
		return null;
	}
	
	public static void loadPlayerHomes() {
		
		ArrayList<String> paths = new ArrayList<String>();
		
		try {
			Files.list(new File(System.getProperty("user.dir")+"/playerHomes/").toPath()).forEach(path ->{
				
				if (path.toAbsolutePath().toString().endsWith(".ph")) {
					System.out.println(MsgPrefix.LIST + path);
					paths.add(path.toAbsolutePath().toString());
				}
				
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Attempting to deserialize");
		
		for (int i = 0; i < paths.size(); i++ ) {
			playerHomes.add(PlayerHome.deserialize(paths.get(i)));
		}
		
	}
	
	@Override
	public void onEnable() {
		// enable /home commands
		this.getCommand("home").setExecutor(new Home(this));
		
		// enable /tpa commands
		this.getCommand("tpa").setExecutor(new TeleportAssistant(this));
		
		loadPlayerHomes();
		System.out.println("Number of player homes loaded: " + playerHomes.size());
		
		System.out.println("Essentials by Inivican enabled!");
	}
	
	@Override
	public void onDisable() {
		
		System.out.println("Serialization in process....");
		
		Path path = Paths.get("playerHomes");
		if (!Files.exists(path)) {
			boolean status = new File("playerHomes").mkdir();
			System.out.println("`playerHomes` did not exist. Creating one for you." + status);
		}
		
		
		
		
		for(PlayerHome ph : playerHomes) {
			PlayerHome.serialize(ph, PlayerHome.getAutoName(ph));
		}
		
		System.out.println("Essentials by Inivican disabled!");
	}
	
	public static void deletePlayerHome(final String fileName) {
		
		File file = new File(fileName);
		if(file.exists()) {
			boolean b = file.delete();
			System.out.println(fileName + " deletion: " + b);
		}
	}
}
