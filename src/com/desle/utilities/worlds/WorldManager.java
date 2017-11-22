package com.desle.utilities.worlds;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.fusesource.jansi.Ansi.Color;

import com.desle.Main;

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
	private boolean deleteOnUnload;
	
	public WorldManager(String name) {
		this.file = new File(Bukkit.getWorldContainer(), name);
		this.name = name;
		list.put(this.name, this);
	}
	
	
	public World getWorld() {
		if (this.world != null)
			return this.world;
		
		if (getFile().exists())
			return null;
		
		this.world = Bukkit.getWorld(this.name);
		if (this.world != null)
			return this.world;
		
		return null;
	}
	
	
	public String getName() {
		return this.name;
	}
	
	
	public World load() {
		if (!getFile().exists())
			return null;
		
		World world = getWorld();
		
		if (world != null)
			return world;
		
		return Bukkit.createWorld(new WorldCreator(getName()));
	}
	
	
	public boolean unload() {
		if (getWorld() != null) {
			for (Player player : getWorld().getPlayers()) {
				player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
			}
		}

		if (!this.deleteOnUnload)
			return Bukkit.unloadWorld(getWorld(), false);
		
		Bukkit.unloadWorld(getName(), false);
		return delete();
	}
	
	
	
	public boolean delete() {
		boolean delete = deleteFiles(getFile());
		unregister();
		
		return delete;
	}
	
	
	
	private boolean deleteFiles(File path) {		
		if (path == null)
			return false;

		System.out.println(path.canWrite());
		
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
	
	
	public void deleteOnUnload(boolean deleteOnUnload) {
		this.deleteOnUnload = deleteOnUnload;
	}
	
	
	public void copyFrom(String name, Runnable runnable) {
		
		WorldManager worldManager = get(name);
		WorldManager newWorldManager = this;
		
		worldManager.unload();
		
		File from = worldManager.getFile();
		File to = newWorldManager.getFile();
		
		if (to.exists()) {
			newWorldManager.unload();
			newWorldManager.delete();
		}
		
		if (!from.exists())
			return;	
		
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
		if (!to.exists())
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
	
	
	public File getFile() {		
		if (this.file != null && this.file.exists())
			return this.file;
		
		this.file = new File(Bukkit.getWorldContainer(), this.name);
		return this.file;
	}
	
	
	public void unregister() {
		list.remove(this);
	}
}
