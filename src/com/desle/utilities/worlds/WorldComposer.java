package com.desle.utilities.worlds;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.fusesource.jansi.Ansi.Color;

import com.google.common.io.Files;

public class WorldComposer {
	
	
	public static File getWorldFile(String name) {
		File file = new File(Bukkit.getWorldContainer(), name);
		if (file.exists())
			return file;
		
		return null;
	}
	
	
	public static boolean isLoaded(String name) {
		if (Bukkit.getWorld(name) != null)
			return true;
		
		return false;
	}
	
	
	public static World loadWorld(String name) {
		
		if (getWorldFile(name) == null)
			return null;
		
		if (isLoaded(name))
			return Bukkit.getWorld(name);
		
		return Bukkit.createWorld(new WorldCreator(name));
	}
	
	
	public static void unloadPlayers(String name, Location location) {
		if (location == null)
			location = Bukkit.getWorlds().get(0).getSpawnLocation();
		
		World world = loadWorld(name);
		
		for (Player player : world.getPlayers()) {
			player.teleport(location);
		}
	}
	
	
	public static void unloadWorld(String name, boolean save) {
		World world = loadWorld(name);
		if (world == null)
			return;

		unloadPlayers(name, null);
		
		Bukkit.unloadWorld(world, save);
	}
	
	
	
	public static boolean deleteWorld(File path) {
		if (path == null)
			return false;
		
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}
	
	

	
	
	
	public static void copyWorld(File from, File to) {
		File wip = new File(to.getParentFile(), to.getName() + "_WIP");
		
		try {
			FileUtils.copyDirectory(from, wip, new FileFilter() {
			    public boolean accept(File pathname) {
			        String name = pathname.getName();
			        
			        return (!name.equalsIgnoreCase("session.lock") && !name.equalsIgnoreCase("uid.dat"));
			    }
			}, true);

			Files.move(wip, to);
		} catch (IOException e) {
			System.out.println(Color.RED + from.getName() + " could not be copied and caused an IOException.");
		}
	}
}
