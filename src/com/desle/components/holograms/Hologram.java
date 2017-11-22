package com.desle.components.holograms;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class Hologram {
	
	public static List<Hologram> list = new ArrayList<Hologram>();
	
	public static void unloadAll() {
		for (Hologram hologram : list) {
			hologram.destroy();
		}
	}
	
	private List<ArmorStand> armorStands;
	private List<String> lines;
	private double lineHeight = 0.3;
	
	public Hologram(Location location, List<String> lines) {
		this.lines = lines;
		spawn(location);
		list.add(this);
	}
	
	public void spawn(Location location) {
		location = location.getBlock().getLocation().add(0.5, (this.lines.size() - 0.5) * this.lineHeight, 0.5);
		
		World world = location.getWorld();
		List<ArmorStand> armorStands = new ArrayList<ArmorStand>();
		
		for (int x = 0; x < this.lines.size(); x++) {
			location.subtract(0, this.lineHeight, 0);
			ArmorStand armorStand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
			armorStands.add(armorStand);
			armorStand.setInvulnerable(true);
			armorStand.setGravity(false);
			armorStand.setVisible(false);
			armorStand.setCollidable(false);
			armorStand.setCustomNameVisible(true);
			armorStand.setCustomName(this.lines.get(x));
		}
		
		this.armorStands = armorStands;
	}
	
	public void destroy() {
		for (ArmorStand armorStand : this.armorStands) {
			armorStand.remove();
		}
	}
	
	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	
	public List<String> getLines() {
		return this.lines;
	}
	
	public void setLineHeight(double lineHeight) {
		this.lineHeight = lineHeight;
	}
}