package com.desle.modals.modalhandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.meta.BookMeta;

import com.desle.bookcomposer.BookComposer;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;

public abstract class QuestModalHandler implements ModalHandler {

	public abstract void callback(String result);
	
	@Override
	public String getDisplay(String key) {
		switch(key) {
			case "HEADER":
				return "Quest";
			case "QUESTION":
				return "Do you want to accept this quest?";
		}
		
		return ModalHandler.super.getDisplay(key);
	}
	
	@Override
	public TextComponent getTextComponent(String key) {
		TextComponent textComponent = new TextComponent();
		
		switch (key.toUpperCase()) {
			case "HEADER":
				textComponent.setText(BookComposer.center(BookComposer.center(this.getDisplay(key))));
				return textComponent;
			case "QUESTION":
				textComponent.setText(BookComposer.center(BookComposer.center(this.getDisplay(key))) + "\n\n");
				return textComponent;
			case "TITLE":
				textComponent.setText("\n" + BookComposer.center(this.getDisplay(key)));
				return textComponent;
			case "DESCRIPTION":
				textComponent.setText(BookComposer.divider(ChatColor.UNDERLINE, ' ') + "\n" + this.getDisplay(key));
				return textComponent;
		}
		
		return ModalHandler.super.getTextComponent(key);
	}
	
	@Override
	public BookMeta constructBookMeta() {
		BookMeta bookMeta = ModalHandler.super.constructBookMeta();
		
		List<IChatBaseComponent> pages = new ArrayList<IChatBaseComponent>();
		
		TextComponent page = new TextComponent();
		page.addExtra(this.getTextComponent("HEADER"));
		page.addExtra(this.getTextComponent("DIVIDER"));
		page.addExtra(this.getTextComponent("TITLE"));
		page.addExtra(this.getTextComponent("DESCRIPTION"));
		
		pages.add(BookComposer.createPage(Arrays.asList(page)));
		
		bookMeta = BookComposer.addPages(bookMeta, pages, 0);
		
		return bookMeta;
	}
	
	@Override
	public List<TextComponent> constructModal() {
		TextComponent textComponent = new TextComponent();
		
		textComponent.addExtra(this.getTextComponent("QUESTION"));
		textComponent.addExtra(this.constructOption(BookComposer.centerAndReplace("⋙ Accept ⋙", "⋙ Accept"), "true"));
		textComponent.addExtra(this.getTextComponent("DIVIDER"));
		textComponent.addExtra(this.constructOption(BookComposer.centerAndReplace("⋙ Decline ⋙", "⋙ Decline"), "false"));
		textComponent.addExtra(this.getTextComponent("HELPTEXT"));
		
		return Arrays.asList(textComponent);
	}

}
