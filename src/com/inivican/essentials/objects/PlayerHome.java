package com.inivican.essentials.objects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

import com.inivican.essentials.constants.Msg;
import com.inivican.essentials.util.Players;

public class PlayerHome implements Serializable {
	
	/**
	 * current serial version
	 */
	private static final long serialVersionUID = -5932830294137686811L;
	private String homeName;
	private int x;
	private int y;
	private int z;
	private String playerUUID;
	private String worldName;
	
	public PlayerHome() {
		System.out.println(Msg.DEBUG + "Used no arg playerHome Constructor" );
		this.homeName = "undefined";
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.playerUUID = null;
		this.worldName = "world";
	}
	
	public PlayerHome(String homeName, int x, int y, int z, 
			String playerUUID, String worldName) {
		
		this.homeName = homeName;
		this.x = x;
		this.y = y;
		this.z = z;
		this.playerUUID = playerUUID;
		this.worldName = worldName;
	}
	
	public String getHomeName() {
		return homeName;
	}

	public void setHomeName(String homeName) {
		this.homeName = homeName;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public String getPlayerUUID() {
		return playerUUID;
	}

	public void setPlayerUUID(String playerUUID) {
		this.playerUUID = playerUUID;
	}
	
	@Override
	public String toString() {
		return this.homeName + 
				" located at " + this.x 
				+ ", " +this.y+ ", " +this.z+ ". World name: " + this.worldName;
	}
	
	public String getFewerDetails() {
		return "["+this.homeName+"@"+this.worldName+"] XYZ: " + this.x 
				+ ", " +this.y+ ", " +this.z;
	}
	
	public static void serialize(PlayerHome playerHome, String fileName) {
		try {
			
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(playerHome);
			
			fileOutputStream.close();
			objectOutputStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Serialized as " + fileName + ".");
		}
	}
	
	public static PlayerHome deserialize(String fileName) {
		PlayerHome playerHome = null;

		try {
			FileInputStream fileInputStream = new FileInputStream(fileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			// attempt to read and deserialize
			playerHome = (PlayerHome) objectInputStream.readObject();

			objectInputStream.close();
			fileInputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println(playerHome.getFewerDetails());
		System.out.println();
		
		return playerHome;
	}

	public String getWorldName() {
		return worldName;
	}

	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}
	
	public static String getAutoName(PlayerHome ph) {
		return ph.getHomeName()+"_"+ph.getWorldName()+".ph";
	}
	
}
