package com.desle.components.gui.modals;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

import com.desle.utilities.items.books.BookComposer;

public class Modal {
	
	public static Map<UUID, Modal> openModals = new HashMap<UUID, Modal>();
	private UUID uuid;
	private UUID playerUuid;
	private ModalHandler modalHandler;
	
	public Modal(Player player, ModalHandler modalHandler) {
		this.uuid = UUID.randomUUID();
		this.playerUuid = player.getUniqueId();
		this.modalHandler = modalHandler;
		
		if (this.open())
			openModals.put(this.playerUuid, this);
	}
	
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public ModalHandler getModalHandler() {
		return this.modalHandler;
	}
	
	
	public boolean open() {
		BookMeta bookMeta = modalHandler.constructModal();
		
		Player player = Bukkit.getPlayer(this.playerUuid);
		if (player == null || !player.isOnline())
			return false;

		BookComposer.openBook(player, bookMeta);
		
		this.modalHandler.onOpen(player);
		return true;
	}
	
}

