package com.desle.utilities.worlds;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.fusesource.jansi.Ansi.Color;

import com.desle.Main;

import net.minecraft.server.v1_12_R1.RegionFileCache;

public class WorldManager {
	
	public static Map<String, WorldManager> list = new HashMap<String, WorldManager>();
	
	public static WorldManager get(String name) {
		if (list.containsKey(name))
			return list.get(name);
		
		return new WorldManager(name);
	}
	
	private String name;
	private World world;
	private File file;
	
	public WorldManager(String name) {
		this.file = new File(Bukkit.getWorldContainer(), name);
		this.name = name;
		list.put(this.name, this);
	}
	
	
	public World getWorld() {
		
		if (this.world != null)
			return this.world;
		
		if (fileExists() == null)
			return null;
		
		this.world = Bukkit.getWorld(this.name);
		if (this.world != null)
			return this.world;
		
		this.world = Bukkit.createWorld(new WorldCreator(this.name));
		if (this.world != null)
			return getWorld();
		
		return null;
	}
	
	
	public String getName() {
		return getWorld().getName();
	}
	
	
	public boolean unload() {
		if (Bukkit.getWorld(this.name) == null)
			return true;
		
		for (Player player : getWorld().getPlayers()) {
			player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
		}
		
		return Bukkit.unloadWorld(getWorld(), true);
	}
	
	
	
	public boolean delete() {
		if (fileExists() == null)
			return true;
		
		RegionFileCache.a();
		System.gc();
		System.gc();
		FileDeleteStrategy.FORCE.deleteQuietly(file);
		
		return deleteFiles(file);
	}
	
	
	
	private boolean deleteFiles(File path) {		
		if (path == null)
			return false;
		
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	            	  deleteFiles(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      
	      return(path.delete());
	}
	
	
	public void copy(String name, Runnable runnable) {
		
		WorldManager worldManager = this;
		WorldManager newWorldManager = get(name);
		
		File from = this.fileExists();
		File to = newWorldManager.fileExists();
		
		newWorldManager.unload();
		newWorldManager.delete();
		
		Main main = Main.getPlugin();
		Bukkit.getScheduler().runTaskAsynchronously(main, new Runnable() {
			
			@Override
			public void run() {
				worldManager.copyFiles(from, to);
				
				Bukkit.getScheduler().runTask(main, runnable);
			}
		});
	}
	
	
	private boolean copyFiles(File from, File to) {
		to.mkdirs();
		
		if (from == null || to == null || !from.exists() || !to.exists())
			return false;
		
		try {
			FileUtils.copyDirectory(from, to, new FileFilter() {
			    public boolean accept(File pathname) {
			        String name = pathname.getName();
			        
			        return (!name.equalsIgnoreCase("session.lock") && !name.equalsIgnoreCase("uid.dat"));
			    }
			}, true);
			
			return true;
		} catch (IOException e) {
			System.out.println(Color.RED + from.getName() + " could not be copied and caused an IOException.");
		}
		
		return false;
	}
	
	
	public File fileExists() {		
		if (this.file != null && this.file.exists())
			return this.file;
		
		this.file = new File(Bukkit.getWorldContainer(), this.name);
		if (this.file.exists())
			return this.file;
		
		return null;
	}
	
}
