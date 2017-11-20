package com.desle.components.worlds;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.fusesource.jansi.Ansi.Color;

import com.desle.Main;

public abstract class WorldInstance {
	
	public static List<WorldInstance> list = new ArrayList<WorldInstance>();
	
	private UUID uuid;
	private String worldName;
	private String originalWorldName;
	private World world;
	private boolean available;
	
	public WorldInstance(World world) {
		this.uuid = UUID.randomUUID();
		this.originalWorldName = world.getName();
		this.worldName = this.originalWorldName + "_" + this.uuid.toString();
		
		copyWorldFile(world);
		
		list.add(this);
	}
	
	
	
	
	private void copyWorldFile(World world) {
		setAvailable(false);
		
		File from = world.getWorldFolder();
		File to = new File(Bukkit.getWorldContainer(), this.worldName);
		String worldName = this.worldName;
		
		System.out.println(worldName);

		Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				try {
					FileUtils.copyDirectory(from, to, new FileFilter() {
					    public boolean accept(File pathname) {
					        String name = pathname.getName();
					        
					        return (!name.equalsIgnoreCase("session.lock") && !name.equalsIgnoreCase("uid.dat"));
					    }
					}, true);
				} catch (IOException e) {
					System.out.println(Color.RED + from.getName() + " could not be copied and caused an IOException.");
				}
				
				setAvailable(true);
			}
		});
		
	}
	
	
	
	
	
	public boolean isnItializedWorld() {
		if (this.world == null)
			return false;
		
		return true;
	}
	
	public boolean initializeWorld() {
		if (!this.available)
			return false;
		
		this.world = Bukkit.createWorld(new WorldCreator(this.worldName));
		return true;
	}
	
	private void setAvailable(boolean state) {
		this.available = state;
	}
	
	public boolean isAvailable() {
		if (this.available)
			return true;
		
		return false;
	}
	
	
	
	
	
	
	public void unloadPlayerToWorld(Location location) {
		if (location == null)
			location = Bukkit.getWorlds().get(0).getSpawnLocation();
		
		for (Player player : this.world.getPlayers()) {
			player.teleport(location);
		}
	}
	
	public boolean delete() {
		this.setAvailable(false);
		
		if (this.world == null)
			return false;
		
		if (this.world.getPlayers().size() > 0)
			this.unloadPlayerToWorld(null);
		
		File file = Bukkit.getWorld(this.worldName).getWorldFolder();
		Bukkit.unloadWorld(this.worldName, false);
		
		if (file.delete())
			return true;
		
		return false;
	}
	
	
	
	
	
	public String getWorldName() {
		return this.worldName;
	}
	
	public String getOriginalWorldName() {
		return this.originalWorldName;
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public World getWorld() {
		return this.world;
	}
	
}
