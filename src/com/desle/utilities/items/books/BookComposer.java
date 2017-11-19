package com.desle.utilities.items.books;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
	   buf.setByte(0, (byte) 0);
	   buf.writerIndex(1);
	
	    PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buf));
	    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	    
	    player.getInventory().setItem(slot, item);
	}
	
	
	@SuppressWarnings("unchecked")
	public static BookMeta addPage(BookMeta bookMeta, TextComponent textComponent, int index) {
		List<IChatBaseComponent> pages;
		
		//get the pages
		try {
		    pages = (List<IChatBaseComponent>) CraftMetaBook.class.getDeclaredField("pages").get(bookMeta);
		} catch (ReflectiveOperationException ex) {
		    ex.printStackTrace();
		    return bookMeta;
		}
		
		pages.add(index, createPage(textComponent));
			
		return bookMeta;
	}
	
	
	public static IChatBaseComponent createPage(TextComponent textComponent) {
		String string = ComponentSerializer.toString(textComponent);
		return ChatSerializer.a(string);
	}
}



