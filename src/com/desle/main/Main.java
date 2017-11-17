package com.desle.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.bookcomposer.BookComposer;
import com.desle.modals.Modal;
import com.desle.modals.ModalHandler;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	
	@Override
	public void onEnable() {		
		for (Player player : Bukkit.getOnlinePlayers()) {
//			BookMeta bookMeta = (BookMeta) Bukkit.getItemFactory().getItemMeta(Material.WRITTEN_BOOK);
//			bookMeta.addPage(
//					BookComposer.centerLine(ChatColor.ITALIC + "" + ChatColor.DARK_AQUA + "*- Quest Complete -*", ' ')
//					+ "\n" + BookComposer.center(ChatColor.BLACK + "On Our Way to Sanctuary")
//					+ "\n" + BookComposer.center(ChatColor.DARK_GRAY + "With the pocketwatch in hand, Brick's buzzards on your side, you are now ready to storm Control Core Angel.")
//					);
//			BookComposer.openBook(player, bookMeta);
//			
			new Modal(player, new ModalHandler() {
				
				@Override
				public void callback(String result) {
					player.sendMessage(result);
				}
			});
		}
	}
	
	
	@Override
	public void onDisable() {
	}
	
	
}