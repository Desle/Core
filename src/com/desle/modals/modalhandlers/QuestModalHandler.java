package com.desle.modals.modalhandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.meta.BookMeta;

import com.desle.bookcomposer.BookComposer;

import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;

public class QuestModalHandler implements ModalHandler {

	@Override
	public void callback(String result) {}
	
	@Override
	public TextComponent getText(String key) {
		TextComponent textComponent = new TextComponent();
		
		switch (key.toUpperCase()) {
			case "HEADER":
				textComponent.setText(BookComposer.center("Quest"));
				return textComponent;
			case "QUESTION":
				textComponent.setText(BookComposer.center("Do you want to accept this quest?") + "\n\n");
				return textComponent;
		}
		
		return ModalHandler.super.getText(key);
	}
	
	@Override
	public BookMeta constructBookMeta() {
		BookMeta bookMeta = ModalHandler.super.constructBookMeta();
		
		List<IChatBaseComponent> pages = new ArrayList<IChatBaseComponent>();
		
		TextComponent page = new TextComponent();
		page.addExtra(this.getText("HEADER"));
		page.addExtra(this.getText("DIVIDER"));
		page.addExtra(this.getText("TITLE"));
		page.addExtra(this.getText("DESCRIPTION"));
		
		pages.add(BookComposer.createPage(Arrays.asList(page)));
		
		bookMeta = BookComposer.addPages(bookMeta, pages, 0);
		
		return bookMeta;
	}
	
	@Override
	public List<TextComponent> constructModal() {
		TextComponent textComponent = new TextComponent();
		
		textComponent.addExtra(this.getText("QUESTION"));
		textComponent.addExtra(this.constructOption(BookComposer.centerAndReplace("⋙ Accept ⋙", "⋙ Accept"), "ACCEPT"));
		textComponent.addExtra(this.getText("DIVIDER"));
		textComponent.addExtra(this.constructOption(BookComposer.centerAndReplace("⋙ Decline ⋙", "⋙ Decline"), "DECLINE"));
		textComponent.addExtra(this.getText("HELPTEXT"));
		
		return Arrays.asList(textComponent);
	}

}
