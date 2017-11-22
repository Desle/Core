package com.desle;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.components.gui.modals.ModalCommandHandler;
import com.desle.components.holograms.Hologram;
import com.desle.plugins.dungeons.Dungeon;
import com.desle.plugins.dungeons.DungeonEvents;


public class Main extends JavaPlugin implements Listener {
	
	public static Main getPlugin() {
		return (Main) Bukkit.getPluginManager().getPlugin("MCore");
	}	
	
	@Override
	public void onEnable() {
		initializeCommands();
		initializeEvents();
	}
	
	@Override
	public void onDisable() {
		Hologram.unloadAll();
		Dungeon.unloadAll();
	}
	
	public void initializeCommands() {
		getCommand("finishmodal").setExecutor(new ModalCommandHandler());
	}
	
	public void initializeEvents() {
		Main main = getPlugin();
		Bukkit.getPluginManager().registerEvents(new DungeonEvents(), main);
	}
}