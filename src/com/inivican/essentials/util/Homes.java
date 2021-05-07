package com.inivican.essentials.util;

import java.util.ArrayList;
import java.util.UUID;

import com.inivican.essentials.objects.PlayerHome;

public class Homes {
	
	public static ArrayList<PlayerHome> getPlayerHomesByPlayer(final UUID playerUUID, 
			final ArrayList<PlayerHome> homes) {
		
		ArrayList<PlayerHome> resultSet = new ArrayList<PlayerHome>();
		for (PlayerHome home : homes) {
			if (home.getPlayerUUID().equals(playerUUID.toString())) {
				resultSet.add(home);
			}
		}
		
		return resultSet;
	}
	
	/**
	 * if a given player has at least one home in a given arraylist, return true
	 * otherwise
	 * @param playerUUID
	 * @param homes
	 * @return 
	 */
	public static boolean playerHasAtLeastOneHome(final UUID playerUUID, 
			final ArrayList<PlayerHome> homes) {
		
		for ( int i = 0 ; i < homes.size(); i++ ) {
			if (homes.get(i).getPlayerUUID().equals(playerUUID.toString())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * if a given player has at least one home in a given array, return true
	 * otherwise
	 * @param playerUUID player UUID
	 * @param homes an array of homes
	 * @return whether the name was found in the array or not
	 */
	public static boolean playerHasAtLeastOneHome(final UUID playerUUID, 
			final PlayerHome... homes) {
		for ( int i = 0 ; i < homes.length; i++ ) {
			if (homes[i].getPlayerUUID().equals(playerUUID.toString())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines if the supplied string is already
	 * @param playerUUID
	 * @param homeName
	 * @param homes
	 * @return
	 */
	public static boolean playerHomeExists(final UUID playerUUID, final String homeName, 
			final ArrayList<PlayerHome> homes) {
		
		
		if (playerHasAtLeastOneHome(playerUUID, homes)) {
			for (int i = 0 ; i < homes.size(); i++) {
				if (homes.get(i).getHomeName().equals(homeName) 
						&& homes.get(i).getPlayerUUID().equals(playerUUID.toString())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
}
