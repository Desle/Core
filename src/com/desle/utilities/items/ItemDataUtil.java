package com.desle.utilities.items;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_12_R1.NBTTagCompound;

public class ItemDataUtil {
	
	
	public static NBTTagCompound getData(ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack craftItemStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound comp = craftItemStack.getTag();
        
        if(comp == null)
            comp = new NBTTagCompound();
        
        return comp;
	}
	
	
	public static ItemStack setData(ItemStack itemStack, NBTTagCompound comp) {
        net.minecraft.server.v1_12_R1.ItemStack craftItemStack = CraftItemStack.asNMSCopy(itemStack);
        craftItemStack.setTag(comp);
        
        return CraftItemStack.asBukkitCopy(craftItemStack);
	}
}
