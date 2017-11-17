package com.desle.bookcomposer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.desle.colors.ChatColorUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;

public class BookComposer {
	
	
	public static void openBook(Player player, BookMeta bookMeta) {
		ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
		itemStack.setItemMeta(Bukkit.getItemFactory().getItemMeta(Material.WRITTEN_BOOK));
		
		if (bookMeta != null)
			itemStack.setItemMeta(bookMeta);
		
		int slot = player.getInventory().getHeldItemSlot();
		ItemStack item = player.getInventory().getItem(slot);
		
		player.getInventory().setItem(slot, itemStack);
	
	   ByteBuf buf = Unpooled.buffer(256);
	   buf.setByte(0, (byte)0);
	   buf.writerIndex(1);
	
	    PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buf));
	    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	    
	    player.getInventory().setItem(slot, item);
	}
	
	
	@SuppressWarnings("unchecked")
	public static BookMeta addPages(List<IChatBaseComponent> newPages, BookMeta bookMeta) {
		List<IChatBaseComponent> pages;
		
		//get the pages
		try {
		    pages = (List<IChatBaseComponent>) CraftMetaBook.class.getDeclaredField("pages").get(bookMeta);
		} catch (ReflectiveOperationException ex) {
		    ex.printStackTrace();
		    return bookMeta;
		}
		
		pages.addAll(newPages);
		
		return bookMeta;
	}
	
	
	public static TextComponent createTextComponent(String text, ChatColor chatColor, boolean italic, boolean underlined, boolean strikethrough) {
		TextComponent textComponent = new TextComponent(text);
		textComponent.setColor(chatColor);
		textComponent.setItalic(italic);
		textComponent.setUnderlined(underlined);
		textComponent.setStrikethrough(strikethrough);
		
		return textComponent;
	}
	
	
	public static IChatBaseComponent createPage(List<TextComponent> textComponents) {
		String string = "[";
		
		for (TextComponent textComponent : textComponents) {
			if (string != "[")
				string += ",";
			
			string += ComponentSerializer.toString(textComponent);
		}
		
		return ChatSerializer.a(string + "]");
	}
	
	
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
	
	
	public static String centerAndReplace(String string, String replacement) {
		string = center(string);
		String result = string.replace(string.trim(), replacement);
		
		if (string.trim().length() > replacement.length() && !result.contains("\n"))
			result += "\n";
		
		return result;
		
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



