package com.desle.plugins.dungeons;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
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
		
		Dungeon dungeon = Dungeon.getDungeon(e.getBlock().getLocation());
		if (dungeon == null)
			return;
		
		e.setCancelled(true);
		
		dungeon.createPortal();
	}
}
