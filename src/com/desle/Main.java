package com.desle;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.components.gui.modals.ModalCommandHandler;
import com.desle.components.worlds.WorldInstance;

import net.md_5.bungee.api.ChatColor;


public class Main extends JavaPlugin implements Listener {
	
	
	public static Main getPlugin() {
		return (Main) Bukkit.getPluginManager().getPlugin("MCore");
	}
	
	@Override
	public void onEnable() {
		initializeCommands();
		Bukkit.getPluginManager().registerEvents(Main.getPlugin(), this);
		
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage("why tf not working nib");
		}
	}
	
	
	@Override
	public void onDisable() {
		for (WorldInstance worldInstance : WorldInstance.list) {
			worldInstance.delete();
		}
	}
	
	
	
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		Player player = e.getEntity().getKiller();
		
		player.sendMessage("test");
		
		World world = Bukkit.getWorld("world_template");
		
		new WorldInstance(world) {
			
			@Override
			public void onReady(String worldName) {
				World world = Bukkit.getWorld(worldName);
				
				if (world == null)
					return;

				Bukkit.broadcastMessage(worldName + ChatColor.GRAY + " created");
				
				player.teleport(world.getSpawnLocation());
			}
		};
	}
	
	
	public void initializeCommands() {
		getCommand("finishmodal").setExecutor(new ModalCommandHandler());
	}	
}