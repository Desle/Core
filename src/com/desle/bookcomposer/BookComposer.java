package com.desle.bookcomposer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import com.desle.colors.ChatColorUtils;

public class BookComposer {
	
	
	public static String center(String string) {
		String[] words = string.split(" ");
		String line = "";
		
		List<String> lines = new ArrayList<String>();
		
		for (int x = 0; x < words.length; x++) {
			String word = words[x];
			
			if (line != "" && !line.endsWith(" "))
				word = " " + word;
			
			if (canInline(line.trim(), word, 8)) {
				line += word;
			} else {
				lines.add(centerLine(line, ' '));
				line = word;
			}
			
			line = line.trim();
		}
		
		lines.add(centerLine(line, ' '));
		
		return StringUtils.join(lines, "");
	}
	
	
	public static String divider(ChatColor chatColor, char character) {
		String divider = "";
    	CharacterSize characterSize = CharacterSize.getCharacterSize(character);
    	double remainingCharacterSpace = CharacterSize.getLineSize();
    	
    	while (characterSize.getSize() < remainingCharacterSpace - characterSize.getSize()) {
    		divider += character;
    		remainingCharacterSpace -= characterSize.getSize();
    	}

    	divider = chatColor + divider;
    	
    	while (CharacterSize.SPACE.getSize() <= remainingCharacterSpace - CharacterSize.SPACE.getSize()) {
			divider = CharacterSize.SPACE.getCharacter() + divider;
			remainingCharacterSpace -= CharacterSize.SPACE.getSize();
    	}
    	
    	if (remainingCharacterSpace >= 1)
    		divider += "\n";
		
		return divider + ChatColor.RESET;
	}
	
    
    public static String centerLine(String string, char character) {
    	string = string.replaceAll("\n", "");
    	
    	CharacterSize characterSize = CharacterSize.getCharacterSize(character);
    	
    	double remainingCharacterSpace = getRemainingCharacterSpace(string) / 2;
    	
    	while (characterSize.getSize() <= remainingCharacterSpace - characterSize.getSize()) {
			string = characterSize.getCharacter() + string;
			remainingCharacterSpace -= characterSize.getSize();
    	}

    	while (CharacterSize.SPACE.getSize() <= remainingCharacterSpace - CharacterSize.SPACE.getSize()) {
			string = CharacterSize.SPACE.getCharacter() + string;
			remainingCharacterSpace -= CharacterSize.SPACE.getSize();
    	}
    	
    	return string + "\n";
    }
    
    
	public static boolean canInline(String string, String addition, int extraSpace) {
		double maxSize = CharacterSize.getLineSize();
		double size = getStringSize(string + addition);
		
		if (size + extraSpace >= maxSize)
			return false;
			
		return true;
	}
	
	
	public static double getRemainingCharacterSpace(String string) {
		return (CharacterSize.getLineSize() - getStringSize(string));
	}
	
	
	public static double getStringSize(String string) {
		int size = 0;
		boolean bold = false;
		
		for (int x = 0; x < string.length(); x++) {
			ChatColor chatColor = ChatColorUtils.getColor(string, x);
			
			if (chatColor != null) {
				if (chatColor == ChatColor.BOLD)
					bold = true;
				
				if (chatColor.isColor())
					bold = false;

				x += 1;
			} else {
				if (bold) {
					size += CharacterSize.getCharacterSize(string.charAt(x)).getBoldSize();
				} else {
					size += CharacterSize.getCharacterSize(string.charAt(x)).getSize();
				}
			}
		}
		
		return size;
	}
	
}



