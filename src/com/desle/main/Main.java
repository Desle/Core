package com.desle.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.modals.Modal;
import com.desle.modals.ModalHandler;

public class Main extends JavaPlugin {
	
	
	@Override
	public void onEnable() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			
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