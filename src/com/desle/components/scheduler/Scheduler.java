package com.desle.components.scheduler;

import org.bukkit.Bukkit;

import com.desle.Main;

public abstract class Scheduler {
	
	public abstract void onCountDown(int secondsLeft);
	
	public Scheduler(int seconds) {
		countDown(seconds, 0);	
	}
	
	public void countDown(int count, int delay) {
		
		Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(), new Runnable() {
		    @Override
		    public void run() {
		    	onCountDown(count);
		    	
		    	if (count != 0)
		    		countDown(count - 1, 20);
			    }
		}, delay);
	}
}
