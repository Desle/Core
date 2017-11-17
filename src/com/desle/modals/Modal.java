package com.desle.modals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

import com.desle.bookcomposer.BookComposer;

import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;

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
		BookMeta bookMeta = (BookMeta) Bukkit.getItemFactory().getItemMeta(Material.WRITTEN_BOOK);
		
		List<TextComponent> textComponents = new ArrayList<TextComponent>();
		textComponents.add(this.modalHandler.constructModal());
		
		List<IChatBaseComponent> pages = new ArrayList<IChatBaseComponent>();
		pages.add(BookComposer.createPage(textComponents));
		
		bookMeta = BookComposer.addPages(pages, bookMeta);
		
		Player player = Bukkit.getPlayer(this.playerUuid);
		if (player == null || !player.isOnline())
			return false;

		BookComposer.openBook(player, bookMeta);
		
		this.modalHandler.onOpen(player);
		return true;
	}
	
}

