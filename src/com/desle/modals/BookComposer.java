package com.desle.modals;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class BookComposer {
	
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
	
	
//	public static ChatColor tempt(String string, int index) {
//    	ChatColor chatColor = null;
//    	
//        for(ChatColor color : ChatColor.values()) {
//        	int colorIndex = string.lastIndexOf(color.toString());
//        	
//        	if (colorIndex == index);
//        }
//       
//        return chatColor;
//	}
	
	
	public static ChatColor getColor(String string, int i) {		
		if (string.length() < i || string.length() < i + 1)
			return null;
		
		ChatColor chatColor = ChatColor.getByChar(string.charAt(i + 1));
		
		if (string.length() >= i + 2) {
			ChatColor doubleCharChatColor = ChatColor.getByChar(string.charAt(i + 1) + "" + string.charAt(i + 2));
			if (doubleCharChatColor != null)
				chatColor = doubleCharChatColor;
		}
		
		return chatColor;
	}
	
}



