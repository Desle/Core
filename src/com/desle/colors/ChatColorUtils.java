package com.desle.colors;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

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
			int colorIndex = string.lastIndexOf("ยง");
			ChatColor chatColor = getColor(string, colorIndex);
			
			chatColors.add(chatColor);
			

			if (chatColor != ChatColor.BOLD && chatColor == ChatColor.UNDERLINE && chatColor != ChatColor.STRIKETHROUGH && chatColor != ChatColor.ITALIC)
				break;
		}
		
		return chatColors.toArray(new ChatColor[chatColors.size()]);
	}

	
	public static ChatColor getColor(String string, int i) {
		if (string.length() < i || string.length() < i + 1 || string.charAt(i) != 'ง')
			return null;
		
		ChatColor chatColor = ChatColor.getByChar(string.charAt(i + 1));
		
		return chatColor;
	}
	
	
}
