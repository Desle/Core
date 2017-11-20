package com.desle;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.fusesource.jansi.Ansi.Color;

import com.desle.components.gui.modals.ModalCommandHandler;
import com.desle.components.worlds.WorldInstance;
import com.desle.plugins.dungeons.Dungeon;


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
			System.out.println(worldInstance.getWorldName() + Color.RED + " removal success: " + worldInstance.delete());
		}
	}
	
	
	
	@EventHandler
	public void enterPortal(PlayerPortalEvent e) {
		e.setCancelled(true);
		
		
		e.getPlayer().sendMessage("entering");
		
		if (!Dungeon.active.containsKey(e.getFrom().getBlock().getLocation()))
			return;
		
		e.getPlayer().sendMessage("dungeon found");
		
		Dungeon dungeon = Dungeon.active.get(e.getFrom().getBlock().getLocation());
		
		if (!dungeon.getWorldInstance().initializeWorld()) {
			e.getPlayer().sendMessage("The dungeon is still loading. Please try again.");
			return;
		}
		
		e.getPlayer().teleport(dungeon.getWorldInstance().getWorld().getSpawnLocation());
	}
	
	
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		
		if (e.getEntity() instanceof Player)
			return;
		
		if (e.getEntity().getKiller() == null)
			return;
		
		Player player = e.getEntity().getKiller();
		
		World world = Bukkit.getWorld("world_template");
		
		
		player.sendMessage("test");
		
		if (world == null)
			world = Bukkit.createWorld(new WorldCreator("world_template"));
		
		e.getEntity().getLocation().getBlock().setType(Material.PORTAL);
		
		new Dungeon(new WorldInstance(world) {
		}, e.getEntity().getLocation().getBlock().getLocation());
	}
	
	
	public void initializeCommands() {
		getCommand("finishmodal").setExecutor(new ModalCommandHandler());
	}	
}