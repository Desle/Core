package com.desle.components.worlds;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Portal {
	
	public static Map<Location, Portal> mapped = new HashMap<Location, Portal>();
	
	private Location location;
	private Block[] blocks;
	
	public Portal(Location location) {
		this.location = location.getBlock().getLocation();
	}
	
	
	public Location getNearestGround(Location startLocation, int maxDistance) {
		Location calcLocation = startLocation.clone();
		for (int x = 0; x < 5; x++) {
			calcLocation.subtract(0, 1, 0);
			if (calcLocation.getBlock().getType() != Material.AIR)
				return calcLocation;
		}
		
		return null;
	}
	
	
	public boolean construct() {
		this.location = getNearestGround(this.location, 5).add(0, 1, 0);
		
		if (this.location == null)
			return false;
		
		this.blocks = new Block[]{this.location.getBlock(), this.location.add(0, 1, 0).getBlock()};
			
		for (Block block : this.blocks) {
			if (block.getType() != Material.AIR) {
				this.blocks = new Block[]{};
				return false;
			}
		}

		for (Block block : this.blocks) {
			block.setType(Material.PORTAL, false);
			mapped.put(block.getLocation(), this);
		}
		
		return true;
	}
	
	public void delete() {
		for (Block block : this.blocks) {
			block.setType(Material.AIR);
			
			if (mapped.containsKey(block.getLocation()))
				mapped.remove(block.getLocation());
		}
	}
}
