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
	
	default String getDisplay(String key) {
		switch(key.toUpperCase()) {
			case "QUESTION":
				return "Are you sure you want to continue?";
			case "HELPTEXT":
				return "Click on one of the options displayed above.";
			case "DIVIDER":
				return "⥼⟝ ᚛" + ChatColor.DARK_PURPLE + " ⁕" + ChatColor.BLACK + " ᚜ ⟞⥽";
		}
		
		return "";
	}
	
	default TextComponent getTextComponent(String key) {
		TextComponent textComponent = new TextComponent();
		
		switch(key.toUpperCase()) {
			case "QUESTION":
				textComponent.setText(BookComposer.center(this.getDisplay(key)) + "\n\n");
			break;
			case "HELPTEXT":
				textComponent.setText("\n\n" + BookComposer.center(this.getDisplay(key)) + "\n\n");
				textComponent.setItalic(true);
				textComponent.setColor(ChatColor.GRAY);
			break;
			case "DIVIDER":
				textComponent.setText(BookComposer.center(this.getDisplay(key)));
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
		
		System.out.println(display);
		
		textComponent.setColor(ChatColor.BLACK);
		textComponent.setItalic(true);
		textComponent.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/finishmodal " + value.toUpperCase()));
		
		return textComponent;
	}

	default List<TextComponent> constructModal() {
		TextComponent textComponent = new TextComponent();
		
		textComponent.addExtra(this.getTextComponent("QUESTION"));
		textComponent.addExtra(this.constructOption(BookComposer.centerAndReplace("⋙ Confirm ⋙", "⋙ Confirm"), "CONFIRM"));
		textComponent.addExtra(this.getTextComponent("DIVIDER"));
		textComponent.addExtra(this.constructOption(BookComposer.centerAndReplace("⋙ Cancel ⋙", "⋙ Cancel"), "CANCEL"));
		textComponent.addExtra(this.getTextComponent("HELPTEXT"));
		
		return Arrays.asList(textComponent);
	}
	
	abstract void callback(String result);
}

