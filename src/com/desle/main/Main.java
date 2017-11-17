package com.desle.main;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.bookcomposer.BookComposer;
import com.desle.modals.Modal;
import com.desle.modals.modalhandlers.QuestModalHandler;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class Main extends JavaPlugin implements Listener {
	
	
	@Override
	public void onEnable() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			new Modal(player, new QuestModalHandler() {
				
				@Override
				public TextComponent getText(String key) {
					TextComponent textComponent = new TextComponent();
					
					switch (key) {
					case "TITLE":
						textComponent.setText("\n" + BookComposer.center("The Man Who Would Be Jack") + "\n\n");
						return textComponent;
					case "DESCRIPTION":
						textComponent.setText("With the pocketwatch in hand and Brick's buzzards on your side, you are now ready to get the Vault Key.");
						textComponent.setColor(ChatColor.ITALIC);
						return textComponent;
					}
					
					return super.getText(key);
				}
				
				public void callback(String result) {
					player.playSound(player.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 1, 1);
				}
			});
		}
	}
	
	
	@Override
	public void onDisable() {
	}
	

	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {		
		if (!label.equalsIgnoreCase("finishmodal"))
			return false;
		
		if (!(sender instanceof Player))
			return false;
		
		if (args == null || args.length == 0)
			return false;
		
		String result = args[0];
		Player player = (Player) sender;
		
		Modal modal = Modal.openModals.get(player.getUniqueId());
		
		if (modal == null)
			return false;
		
		modal.getModalHandler().callback(result);
		
		return true;
		
	}
	
	
}