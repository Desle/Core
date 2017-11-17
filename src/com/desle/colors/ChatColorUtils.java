package com.desle.colors;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class ChatColorUtils {
	
	
	public static boolean containsColor(String string) {
		if (ChatColor.stripColor(string) == string)
			return false;
		
		return true;
	}
	
	
	public static ChatColor[] getLastAppliedChatColors(String string) {
		List<ChatColor> chatColors = new ArrayList<ChatColor>();
		
		int x = 0;
		while (x == 0) {
			int colorIndex = string.lastIndexOf("§");
			ChatColor chatColor = getColor(string, colorIndex);
			
			chatColors.add(chatColor);
			

			if (chatColor.isColor())
				break;
		}
		
		return chatColors.toArray(new ChatColor[chatColors.size()]);
	}

	
	public static ChatColor getColor(String string, int i) {
		if (string.length() < i || string.length() < i + 1 || string.charAt(i) != '§')
			return null;
		
		ChatColor chatColor = ChatColor.getByChar(string.charAt(i + 1));
		
		return chatColor;
	}
	
	
}
