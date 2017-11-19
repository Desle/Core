package com.desle.components.gui.modals;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

import com.desle.components.sound.SoundType;
import com.desle.utilities.items.books.BookComposer;
import com.desle.utilities.text.formatter.LineSize;
import com.desle.utilities.text.formatter.TextFormatter;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public interface ModalHandler {

	/** Predefined for when the modal opens*/
	default void onOpen(Player player) {
		SoundType.MODAL_OPEN.playFor(player);
	}

	
	/*
	 * Predefined textComponents that can be changed for each modal instance
	 */
	default String getQuestion() {
		return "Are you sure you want to continue?";
	}
	
	default String getHelpText() {
		return "Click on one of the options displayed above.";
	}
	
	default String getDivider() {
		return ChatColor.BLACK + "⥼⟝ ᚛" + ChatColor.GOLD + " ⁕" + ChatColor.BLACK + " ᚜ ⟞⥽";
	}
	
	default String getHeader() {
		return "Confirmation";
	}
	
	default String[] getOptions() {
		return new String[]{"Confirm", "Cancel"};
	}

	
	/* Returns clickable textComponents in 1 that executes /finishmodal <answer>*/
	default TextComponent constructOptions(String[] options) {
		TextFormatter bookComposer = new TextFormatter(LineSize.BOOK);		
		TextComponent component = new TextComponent();
		
		for (int x = 0; x < options.length; x++) {
			String option = options[x];
			TextComponent optionComponent = new TextComponent(bookComposer.centerAndReplace(options[0], option));
			optionComponent.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/finishmodal " + x));
			optionComponent.setItalic(true);
			component.addExtra(optionComponent);
			
			if (x != options.length - 1) 
				component.addExtra(new TextComponent(bookComposer.center(this.getDivider())));
		}
		
		return component;
	}

	
	/* Returns BookMeta used for the modal*/
	default BookMeta constructModal() {
		TextFormatter bookComposer = new TextFormatter(LineSize.BOOK);
		BookMeta bookMeta = (BookMeta) Bukkit.getItemFactory().getItemMeta(Material.WRITTEN_BOOK);
		
		TextComponent component = new TextComponent();
		TextComponent currentComponent = new TextComponent();
		
		currentComponent = new TextComponent(bookComposer.center(this.getHeader()));
		currentComponent.setColor(ChatColor.DARK_GRAY);
		component.addExtra(currentComponent);
		component.addExtra(new TextComponent(bookComposer.center(this.getDivider()) + "\n"));
		currentComponent = new TextComponent(bookComposer.center(this.getQuestion()) + "\n");
		currentComponent.setColor(ChatColor.BLACK);
		component.addExtra(currentComponent);
		component.addExtra(this.constructOptions(this.getOptions()));
		currentComponent = new TextComponent("\n" + bookComposer.center(this.getHelpText()));
		currentComponent.setColor(ChatColor.GRAY);
		component.addExtra(currentComponent);
		
		bookMeta = BookComposer.addPage(bookMeta, component, 0);
		return bookMeta;
	}
	
	abstract void callback(String result);
}

