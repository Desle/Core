package com.desle.utilities.text.formatter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.desle.utilities.text.ChatColorUtils;

import net.md_5.bungee.api.ChatColor;

public class TextFormatter {
	
	private LineSize lineSize;
	
	public TextFormatter(LineSize lineSize) {
		this.lineSize = lineSize;
	}
	
	
	public double getLineSize() {
		return this.lineSize.getCharacterLength();
	}
	
	
	public String center(String string) {
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
	
	
	public String centerAndReplace(String string, String replacement) {
		string = center(string);
		String result = string.replace(string.trim(), replacement);
		
		if (string.trim().length() > replacement.length() && !result.contains("\n"))
			result += "\n";
		
		return result;
		
	}
	
	
	public String divider(ChatColor chatColor, char character) {
		String divider = "";
		CharacterSize characterSize = CharacterSize.getCharacterSize(character);
    	double remainingCharacterSpace = this.getLineSize();
    	
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
	
    
    public String centerLine(String string, char character) {
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
    	
    	return " " + string + "\n";
    }
    
    
	public boolean canInline(String string, String addition, int extraSpace) {
		double maxSize = this.getLineSize();
		double size = getStringSize(string + addition);
		
		if (size + extraSpace >= maxSize)
			return false;
			
		return true;
	}
	
	
	public double getRemainingCharacterSpace(String string) {
		return (this.getLineSize() - getStringSize(string));
	}
	
	
	public double getStringSize(String string) {
		int size = 0;
		boolean bold = false;
		
		for (int x = 0; x < string.length(); x++) {
			ChatColor chatColor = ChatColorUtils.getColor(string, x);
			
			if (chatColor != null) {
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
