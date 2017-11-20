package com.desle.plugins.dungeons;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

import com.desle.components.worlds.WorldInstance;

public class Dungeon {
	
	public static Map<Location, Dungeon> active = new HashMap<Location, Dungeon>();
	
	UUID uuid;
	WorldInstance worldInstance;
	Location portalLocation;
	
	public Dungeon(WorldInstance worldInstance, Location portalLocation) {
		this.portalLocation = portalLocation;
		this.worldInstance = worldInstance;
		this.uuid = UUID.randomUUID();
		
		active.put(portalLocation, this);
	}
	
	
	public boolean canEnter() {
		if (this.worldInstance.isAvailable())
			return true;
		
		return false;
	}
	
	
	public Location getPortalLocation() {
		return this.portalLocation;
	}
	
	
	public WorldInstance getWorldInstance() {
		return this.worldInstance;
	}
	
	
	public void delete() {
		this.worldInstance.delete();
		
		active.remove(this.portalLocation);
	}
}
