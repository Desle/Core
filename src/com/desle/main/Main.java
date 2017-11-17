package com.desle.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.modals.Modal;

public class Main extends JavaPlugin implements Listener {
	
	
	@Override
	public void onEnable() {
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