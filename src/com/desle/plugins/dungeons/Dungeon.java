package com.desle.plugins.dungeons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import com.desle.components.gui.modals.Modal;
import com.desle.components.gui.modals.ModalHandler;
import com.desle.components.holograms.Hologram;
import com.desle.components.sound.SoundType;

public class Dungeon {

	public static Map<Location, Dungeon> map = new HashMap<Location, Dungeon>();
	
	public static Dungeon getDungeon(Location location) {
		
		for (Dungeon dungeon : map.values()) {
			if (location.distanceSquared(dungeon.getFrom()) <= 0.5)
				return dungeon;
		}
		
		return null;
	}
	
	public static void unloadAll() {
		for (Dungeon dungeon : map.values()) {
			dungeon.deletePortal();
		}
	}
	
	private String name;
	
	private int maxPlayers;
	
	private Location from;
	private Location to;
	
	private Hologram portalHologram;
	private Block[] portalBlocks = {};
	
	public Dungeon(String name, int maxPlayers, Location from, Location to) {
		this.name = name;
		this.maxPlayers = maxPlayers;
		this.from = from.getBlock().getLocation().add(0.5, 0, 0.5);
		this.to = to.getBlock().getLocation().add(0.5, 0, 0.5);
		map.put(this.from, this);
		
		animatePortal();
	}
	
	
	
	public String getName() {
		return this.name;
	}
	
	public int getMaxPlayers() {
		return this.maxPlayers;
	}
	
	public Location getFrom() {
		return this.from;
	}
	
	public Location getTo() {
		return this.to;
	}
	
	
	private void animatePortal() {
		double x = this.from.getBlockX() + 0.5;
		double y = this.from.getBlockY();
		double z = this.from.getBlockZ() + 0.5;
		
		Vector from = new Vector(x, y -0.5, z);
		Vector to  = new Vector(x, y, z);
		 
		Vector vector = to.subtract(from);
		
		FallingBlock block = this.from.getWorld().spawnFallingBlock(new Location(this.from.getWorld(), x, y -1, z), new MaterialData(Material.PORTAL));
		block.setVelocity(vector);

		block = this.from.getWorld().spawnFallingBlock(new Location(this.from.getWorld(), x, y, z), new MaterialData(Material.PORTAL));
		block.setVelocity(vector);
	}
	
	
	public void createPortal() {
		List<String> lines = new ArrayList<String>();
		lines.add(ChatColor.DARK_PURPLE + "Dungeon");
		lines.add(ChatColor.WHITE + getName());
		new Hologram(this.from, lines);
		
		this.portalBlocks = new Block[]{this.from.getBlock(), this.from.clone().add(0, 1, 0).getBlock()};
		for (Block block : this.portalBlocks) {
			block.setType(Material.PORTAL, false);
		}
	}
	
	
	public void deletePortal() {		
		for (Block block : this.portalBlocks) {
			block.setType(Material.AIR, false);
		}
		
		if (this.portalHologram != null)
			this.portalHologram.destroy();
	}
	
	
	public void promptJoinModal(Player player) {
		String name = getName();
		
		new Modal(player, new ModalHandler() {
			
			@Override
			public String getHeader() {
				return "Dungeon";
			}
			
			@Override
			public String getQuestion() {
				return "Are you sure you want to enter " + name + "?";
			}
			
			@Override
			public void callback(String result) {
				SoundType.CLICK.playFor(player);
				switch(result) {
				case "0":
					player.teleport(getTo());
					break;
				case "1":
					break;
				}
			}
			
		});
	}
	
}
