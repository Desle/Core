package com.desle.utilities.text.formatter;


public enum LineSize {
	BOOK(58),
	CHAT(156);
	
	LineSize(int characters) {
		this.characters = characters;
	}
	
	private int characters;
	public int getCharacterLength() {
		return this.characters;
	}
}