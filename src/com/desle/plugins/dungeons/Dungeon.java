package com.desle.plugins.dungeons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.desle.components.worlds.Portal;
import com.desle.components.worlds.WorldInstance;

public class Dungeon {
	
	public static Map<Portal, Dungeon> linkedPortals = new HashMap<Portal, Dungeon>();
	
	private WorldInstance worldInstance;
	private UUID uuid;
	private String name;
	
	private int maximumPlayers;
	private List<UUID> players = new ArrayList<UUID>();
	private Portal portal;
	
	
	public Dungeon(String name, WorldInstance worldInstance) {
		this.name = name;
		this.worldInstance = worldInstance;
		this.uuid = UUID.randomUUID();
		
	}
	
	
	public void setPortal(Portal portal) {
		this.portal = portal;
		
		linkedPortals.put(this.portal, this);
		this.portal.construct();
	}
	
	
	public WorldInstance getWorldInstance() {
		return this.worldInstance;
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public int getMaxPlayers() {
		return this.maximumPlayers;
	}
	
	public List<UUID> getPlayerUUIDS() {
		return this.players;
	}
	
	public Portal getPortal() {
		return this.portal;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
