package com.desle.modals;

import org.bukkit.ChatColor;

import com.desle.bookcomposer.BookComposer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public interface ModalHandler {

	default TextComponent constructOption(String display, String value, boolean lastOption) {
		TextComponent textComponent = new TextComponent(BookComposer.center(ChatColor.DARK_GRAY + "" + ChatColor.UNDERLINE + display));
		textComponent.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/finishmodal " + value));
		
		if (!lastOption)
			textComponent.addExtra(new TextComponent(BookComposer.center("⤛  ⤜")));
		
		return textComponent;
	}
	
	abstract void callback(String result);
}
