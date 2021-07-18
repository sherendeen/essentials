package com.inivican.essentials.constants;

import org.bukkit.ChatColor;

public class Msg {
	public final static String DEBUG = "[DEBUG] - ";
	public final static String LIST = "[*] - ";
	public final static String PLUGIN_NAME = ChatColor.BLUE + "["+ChatColor.WHITE+"Issentials"+ChatColor.BLUE+"]";
	public final static String ERR = PLUGIN_NAME + ChatColor.RED + "["  + ChatColor.DARK_RED + "ERR" + ChatColor.RED +"] ";
	public final static String OK = PLUGIN_NAME + ChatColor.GREEN + "["  + ChatColor.WHITE + "OK" + ChatColor.GREEN +"] ";
	public final static String INFO = PLUGIN_NAME +ChatColor.AQUA+ "["  + ChatColor.DARK_BLUE + "INFO" + ChatColor.AQUA +"] ";
	
	//color
	public final static String INLINE_VALUE_FIELD_START = ChatColor.YELLOW + "[" + ChatColor.GRAY ;
	public final static String INLINE_VALUE_COMMA = ChatColor.YELLOW + ","+ ChatColor.GRAY;
	public final static String INLINE_VALUE_PERIOD = ChatColor.YELLOW + "."+ ChatColor.GRAY;
	public final static String INLINE_VALUE_HYPHEN = ChatColor.YELLOW + "-"+ ChatColor.GRAY;
	public final static String INLINE_VALUE_FIELD_EXIT = ChatColor.YELLOW + "]"+ ChatColor.GRAY ;
}
