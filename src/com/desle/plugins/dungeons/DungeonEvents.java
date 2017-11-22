package com.desle.plugins.dungeons;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class DungeonEvents implements Listener {
	
	
	@EventHandler
	public void onPortalEnter(PlayerPortalEvent e) {
		e.setCancelled(true);
		
		Dungeon dungeon = Dungeon.getDungeon(e.getFrom());
		if (dungeon == null)
			return;
		
		dungeon.promptJoinModal(e.getPlayer());
	}
	
	
	@EventHandler
	public void onSandLand(EntityChangeBlockEvent e) {
		if (e.getEntityType() != EntityType.FALLING_BLOCK)
			return;
		
		if (!Dungeon.fallingBlockIds.contains(e.getEntity().getEntityId() + "_"))
			return;

		Dungeon.fallingBlockIds.remove(e.getEntity().getEntityId() + "_");
		e.setCancelled(true);
		
		Dungeon dungeon = Dungeon.getDungeon(e.getBlock().getLocation());
		if (dungeon == null)
			dungeon = Dungeon.getDungeon(e.getBlock().getLocation().clone().add(0, 1, 0));
		
		if (dungeon == null)
			return;
		
		e.setCancelled(true);
		
		dungeon.createPortal();
	}
	
	
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if (e.getEntity().getKiller() == null)
			return;
		
		Location location = e.getEntity().getLocation();
		
		Random random = new Random();
		if (!random.nextBoolean())
			return;
		
		new Dungeon("The Abyss", 24, location, location.clone().add(0, 64, 0));
	}
}
