package com.desle.components.worlds;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitScheduler;

import com.desle.Main;
import com.desle.utilities.worlds.WorldComposer;

public abstract class WorldInstance {
	public abstract void onFinish();
	
	public static List<WorldInstance> list = new ArrayList<WorldInstance>();
		
	private String templateName;
	private String instanceName;
	
	public WorldInstance(World world) {
		if (world == null)
			return;
		
		this.templateName = world.getName();
		this.instanceName = this.templateName + "_instance_" + UUID.randomUUID().toString();
		
		WorldComposer.unloadWorld(this.templateName, false);
		
		File instanceFile = WorldComposer.getWorldFile(this.instanceName);
		
		if (instanceFile != null && instanceFile.exists()) {
			WorldComposer.deleteWorld(instanceFile);
		}

		createInstance();
		
		list.add(this);
	}
	
	
	public void createInstance() {
		String name = this.templateName;
		File from = WorldComposer.getWorldFile(this.templateName);
		File to = new File(Bukkit.getWorldContainer(), this.instanceName);
		
		if (from == null || !from.exists())
			return;
		
		WorldComposer.unloadWorld(templateName, false);


		Main main = Main.getPlugin();
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.runTaskAsynchronously(main, new Runnable() {
			
			@Override
			public void run() {
				WorldComposer.copyWorld(from, to);

				scheduler.runTask(main, new Runnable() {
					@Override
					public void run() {
						WorldComposer.loadWorld(templateName);
						onFinish();
					}
				});
			}
			
		});
	}
	
	
	public World getWorld() {
		return WorldComposer.loadWorld(this.instanceName);
	}
	
	
	public void delete() {
		File file = WorldComposer.getWorldFile(this.instanceName);
		WorldComposer.deleteWorld(file);
	}
}
