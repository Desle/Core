package com.desle.modals.modalhandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

import com.desle.bookcomposer.BookComposer;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public interface ModalHandler {
	
	default void onOpen(Player player) {
		player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1, 1);
	}
	
	default TextComponent getText(String key) {
		TextComponent textComponent = new TextComponent();
		
		switch(key.toUpperCase()) {
			case "HEADER":
				textComponent.setText(BookComposer.center("Confirmation"));
			break;
			case "QUESTION":
				textComponent.setText(BookComposer.center("Are you sure you want to continue?") + "\n\n");
			break;
			case "HELPTEXT":
				textComponent.setText("\n\n" + BookComposer.center(ChatColor.ITALIC + "Click on one of the options displayed above.") + "\n\n");
				textComponent.setColor(ChatColor.GRAY);
			break;
			case "DIVIDER":
				textComponent.setText(BookComposer.center("⥼⟝ ᚛" + ChatColor.DARK_PURPLE + " ⁕" + ChatColor.BLACK + " ᚜ ⟞⥽"));
				textComponent.setColor(ChatColor.BLACK);
			break;
		}
		
		return textComponent;
	}
	
	default BookMeta constructBookMeta() {
		BookMeta bookMeta = (BookMeta) Bukkit.getItemFactory().getItemMeta(Material.WRITTEN_BOOK);		
		
		List<TextComponent> textComponents = new ArrayList<TextComponent>();
		textComponents.addAll(this.constructModal());
		
		List<IChatBaseComponent> pages = new ArrayList<IChatBaseComponent>();
		pages.add(BookComposer.createPage(textComponents));
		
		bookMeta = BookComposer.addPages(bookMeta, pages, 0);
		
		return bookMeta;
	}
	
	default TextComponent constructOption(String display, String value) {
		TextComponent textComponent = new TextComponent(display);
		
		textComponent.setColor(ChatColor.BLACK);
		textComponent.setItalic(true);
		textComponent.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/finishmodal " + value.toUpperCase()));
		
		return textComponent;
	}

	default List<TextComponent> constructModal() {
		TextComponent textComponent = new TextComponent();

		textComponent.addExtra(this.getText("HEADER"));
		textComponent.addExtra(this.getText("DIVIDER"));
		textComponent.addExtra(this.getText("QUESTION"));
		textComponent.addExtra(this.constructOption(BookComposer.centerAndReplace("⋙ Confirm ⋙", "⋙ Confirm"), "CONFIRM"));
		textComponent.addExtra(this.getText("DIVIDER"));
		textComponent.addExtra(this.constructOption(BookComposer.centerAndReplace("⋙ Cancel ⋙", "⋙ Cancel"), "CANCEL"));
		textComponent.addExtra(this.getText("HELPTEXT"));
		
		return Arrays.asList(textComponent);
	}
	
	abstract void callback(String result);
}

