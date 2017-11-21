package com.desle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.components.gui.modals.Modal;
import com.desle.components.gui.modals.ModalCommandHandler;
import com.desle.components.gui.modals.ModalHandler;
import com.desle.components.sound.SoundType;
import com.desle.components.worlds.Portal;
import com.desle.components.worlds.WorldInstance;
import com.desle.plugins.dungeons.Dungeon;
import com.desle.utilities.worlds.WorldComposer;

import net.md_5.bungee.api.ChatColor;


public class Main extends JavaPlugin implements Listener {
	
	
	public static Main getPlugin() {
		return (Main) Bukkit.getPluginManager().getPlugin("MCore");
	}	
	
	@Override
	public void onEnable() {
		initializeCommands();
		Bukkit.getPluginManager().registerEvents(Main.getPlugin(), this);
		
		new WorldInstance(WorldComposer.loadWorld("empty_world_template")) {
			
			@Override
			public void onFinish() {				
				Dungeon dungeon = new Dungeon(ChatColor.RED + "The Abyss",  this);
				dungeon.setPortal(new Portal(Bukkit.getPlayer("Desle").getLocation()));
			}
		};
		
	}
	
	
	@EventHandler
	public void portal(PlayerPortalEvent e) {
		e.setCancelled(true);
		
		Player player = e.getPlayer();
		Location location = e.getFrom().getBlock().getLocation();
		
		if (!Portal.mapped.containsKey(location))
			return;
		
		Portal portal = Portal.mapped.get(location);
		Dungeon dungeon = Dungeon.linkedPortals.get(portal);
		
		if (dungeon == null)
			return;
		
		new Modal(player, new ModalHandler() {
			
			@Override
			public String getHeader() {
				return dungeon.getName();
			}
			
			@Override
			public String getQuestion() {
				return "Are you sure you want to enter this dungeon?";
			}
			
			@Override
			public void callback(String result) {
				SoundType.CLICK.playFor(player);
				
				switch(result) {
				case "0":
					player.teleport(dungeon.getWorldInstance().getWorld().getSpawnLocation());
					break;
				}
			}
		});
	}
	
	@Override
	public void onDisable() {		
		for (WorldInstance worldInstance : WorldInstance.list) {
			worldInstance.delete();
		}
	}
	
	public void initializeCommands() {
		getCommand("finishmodal").setExecutor(new ModalCommandHandler());
	}
}