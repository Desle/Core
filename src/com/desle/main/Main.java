package com.desle.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.modals.Modal;
import com.desle.modals.modalhandlers.ModalCommandHandler;
import com.desle.modals.modalhandlers.ModalHandler;
import com.desle.sound.SoundType;

public class Main extends JavaPlugin implements Listener {
	
	
	public static Main getPlugin() {
		return (Main) Bukkit.getPluginManager().getPlugin("MCore");
	}
	
	
	@Override
	public void onEnable() {
		initializeCommands();
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	
	@Override
	public void onDisable() {
	}
	
	
	public void initializeCommands() {
		getCommand("finishmodal").setExecutor(new ModalCommandHandler());
	}
	
	
	
	@EventHandler
	public void onRepair(PlayerBedEnterEvent e) {
		e.setCancelled(true);
		
		Player player = e .getPlayer();
		
		new Modal(player, new ModalHandler() {
			
			@Override
			public void callback(String result) {
				switch(result.toUpperCase()){ 
				case "TRUE":
					player.getWorld().setTime(1); 
					SoundType.NOTIFICATION_2.playFor(player);
				break;
				case "FALSE":
					SoundType.CANCEL.playFor(player);
				break;
				}
				
			}
		});
	}
	
	
}