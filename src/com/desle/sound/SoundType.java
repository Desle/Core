package com.desle.sound;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public enum SoundType {
	
	THROW(Sound.ENTITY_WITCH_THROW),
	CLICK(Sound.UI_BUTTON_CLICK),
	CANCEL(Sound.BLOCK_WOOD_BUTTON_CLICK_ON),
	MODAL_OPEN(Sound.ITEM_ARMOR_EQUIP_ELYTRA),
	NOTIFICATION_1(Sound.ENTITY_EXPERIENCE_ORB_PICKUP),
	NOTIFICATION_2(Sound.BLOCK_NOTE_BELL);
	
	private Sound sound;
	
	public Sound getSound() {
		return this.sound;
	}
	
	public void playFor(Player player) {
		player.playSound(player.getLocation(), this.getSound(), 1, 1);
	}
	
	public static void playSound(Player player, Sound sound) {
		player.playSound(player.getLocation(), sound, 1, 1);
	}
	
	SoundType(Sound sound) {
		this.sound = sound;
	}
}
