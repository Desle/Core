package com.desle.modals;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	
	@Override
	public void onEnable() {
		
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		book.setItemMeta(Bukkit.getItemFactory().getItemMeta(Material.WRITTEN_BOOK));
		BookMeta bookMeta = (BookMeta) book.getItemMeta();
		
		String page = "\n" + CharacterSize.center("~ Quest Complete ~", ' ');
		page += CharacterSize.center("Hard difficulty", ' ');
		page += "\n\n" + CharacterSize.center("Congratulations!", ' ');
		
		bookMeta.addPage(page);
		
		book.setItemMeta(bookMeta);
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.getInventory().setItemInMainHand(book);
		}
		
		
	}
}
