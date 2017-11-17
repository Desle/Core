package com.desle.modals;

import com.desle.bookcomposer.BookComposer;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public interface ModalHandler {

	default TextComponent constructOptions() {
		String[] options = {"Yes", "No"};
		
		TextComponent textComponent = new TextComponent();
		
		for (String option : options) {
			TextComponent optionTextComponent = new TextComponent(BookComposer.center(ChatColor.BLACK + option));
			optionTextComponent.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/finishmodal " + option.toUpperCase()));
			
			if (option != options[options.length])
				optionTextComponent.addExtra(new TextComponent(BookComposer.center(ChatColor.DARK_GRAY + "⤛ OR ⤜")));
			
			textComponent.addExtra(optionTextComponent);
		}
		
		return textComponent;
	}
	
	abstract void callback(String result);
}
