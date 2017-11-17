package com.desle.modals;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.desle.bookcomposer.BookComposer;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public interface ModalHandler {
	
	default void onOpen(Player player) {
		player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1, 1);
	}
	
	default TextComponent getIntroText() {
		TextComponent textComponent = new TextComponent();
		textComponent.addExtra(BookComposer.createTextComponent("\n\n" + BookComposer.center("Are you sure you want to continue?"), ChatColor.BLACK, false, false, false));
		
		return textComponent;
	}

	default TextComponent constructModal() {
		TextComponent textComponent = new TextComponent();
		
		textComponent.addExtra(this.getIntroText());
		textComponent.addExtra(BookComposer.createTextComponent("\n" + BookComposer.center("Click an option") + "\n", ChatColor.DARK_GRAY, false, false, false));
		
		TextComponent option = BookComposer.createTextComponent(BookComposer.centerAndReplace("⋙ Confirm ⋙", "⋙ Confirm"), ChatColor.BLACK, true, false, false);
		option.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/finishmodal CONFIRM"));
		textComponent.addExtra(option);
		textComponent.addExtra(BookComposer.createTextComponent(BookComposer.center("⥼⟝ ᚛ ⁕ ᚜ ⟞⥽"), ChatColor.BLACK, false, false, false));
		
		option = BookComposer.createTextComponent(BookComposer.centerAndReplace("⋙ Cancel ⋙", "⋙ Cancel"), ChatColor.BLACK, true, false, false);
		option.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/finishmodal CANCEL"));
		textComponent.addExtra(option);
		
		return textComponent;
	}
	
	abstract void callback(String result);
}

