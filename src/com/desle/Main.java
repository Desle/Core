package com.desle;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.components.gui.modals.ModalCommandHandler;
import com.desle.utilities.worlds.WorldManager;


public class Main extends JavaPlugin implements Listener {
	
	public static Main getPlugin() {
		return (Main) Bukkit.getPluginManager().getPlugin("MCore");
	}	
	
	@Override
	public void onEnable() {
		initializeCommands();
		Bukkit.getPluginManager().registerEvents(Main.getPlugin(), this);
		
		WorldManager.get("empty_world_template").copy("empty_world_instance", new Runnable() {
			
			@Override
			public void run() {
				WorldManager worldManager = WorldManager.get("empty_world_instance");
				
				for (Player player : Bukkit.getOnlinePlayers()) {
					player.teleport(worldManager.getWorld().getSpawnLocation());
				}
			}
		});
	}
	
	@Override
	public void onDisable() {
		for (WorldManager wm : WorldManager.list.values()) {
			wm.unload();
		}
	}
	
	public void initializeCommands() {
		getCommand("finishmodal").setExecutor(new ModalCommandHandler());
	}
}