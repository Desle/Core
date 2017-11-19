package com.desle.components.gui.modals;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModalCommandHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
		if (!label.equalsIgnoreCase("finishmodal"))
			return false;
		
		if (!(sender instanceof Player))
			return false;
		
		if (arguments == null || arguments.length == 0)
			return false;
		
		String result = arguments[0];
		Player player = (Player) sender;
		
		Modal modal = Modal.openModals.get(player.getUniqueId());
		
		if (modal == null)
			return false;
		
		modal.getModalHandler().callback(result);
		
		return true;
	}

}
