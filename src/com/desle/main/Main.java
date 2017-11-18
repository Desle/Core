package com.desle.main;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.modals.modalhandlers.ModalCommandHandler;


public class Main extends JavaPlugin implements Listener {
	
	
	public static Main getPlugin() {
		return (Main) Bukkit.getPluginManager().getPlugin("MCore");
	}
	
	
	@Override
	public void onEnable() {
		initializeCommands();
	}
	
	
	@Override
	public void onDisable() {
	}
	
	
	
	
	
	public void initializeCommands() {
		getCommand("finishmodal").setExecutor(new ModalCommandHandler());
	}
	
	
}