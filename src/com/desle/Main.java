package com.desle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.desle.components.gui.modals.ModalCommandHandler;


public class Main extends JavaPlugin implements Listener {
	
	
	public static Main getPlugin() {
		return (Main) Bukkit.getPluginManager().getPlugin("MCore");
	}
	
	
	@Override
	public void onEnable() {
		initializeCommands();
		
		
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			World world = Bukkit.createWorld(new WorldCreator("empty_world"));
			
			
			Location from = player.getLocation();
			Location to = world.getSpawnLocation();
			
			List<Chunk> chunksFrom = getSurroundingChunks(from);
			chunksFrom.add(from.getChunk());
			
			List<Chunk> chunksTo = getSurroundingChunks(to);
			chunksTo.add(to.getChunk());
			
			int maxY = (int) (from.getY() + 10);
			int minY = (int) (from.getY() - 10);
			
			for (int x = 0; x < chunksFrom.size(); x++) {
				Chunk chunkFrom = chunksFrom.get(x);
				Chunk chunkTo = chunksTo.get(x);
				
				copyChunk(chunkFrom, chunkTo, minY, maxY);
			}
			
			to.setY(player.getLocation().getY());
			player.teleport(to);
		}
	}
	
	
	@Override
	public void onDisable() {
	}
	
	
	public void initializeCommands() {
		getCommand("finishmodal").setExecutor(new ModalCommandHandler());
	}
	
	
	public List<Chunk> getSurroundingChunks(Location location) {
        int[] offset = {-1,0,1};

        World world = location.getWorld();
        int baseX = location.getChunk().getX();
        int baseZ = location.getChunk().getZ();

        List<Chunk> chunksAroundPlayer = new ArrayList<>();
        for(int x : offset) {
            for(int z : offset) {
                Chunk chunk = world.getChunkAt(baseX + x, baseZ + z);
                chunksAroundPlayer.add(chunk);
            }
        } return chunksAroundPlayer;
    }
	

	private void copyChunk(Chunk chunk1, Chunk chunk2, int minY, int maxY){
	    for (int x = 0; x < 16; x++) {
	        for (int y = minY; y < maxY; y++) {
	            for (int z = 0; z < 16; z++) {
	            	Block from = chunk1.getBlock(x, y, z);
	            	Block to = chunk2.getBlock(x, y, z);
	            	
	            	to.setBiome(from.getBiome());
	            	to.setTypeIdAndData(from.getTypeId(), from.getData(), true);
	            }
	        }
	    }
	}
	
	
	
	
	
	
}

























