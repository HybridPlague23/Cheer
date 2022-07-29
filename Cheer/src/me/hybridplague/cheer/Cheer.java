package me.hybridplague.cheer;

import org.bukkit.plugin.java.JavaPlugin;

public class Cheer extends JavaPlugin {

	@Override
	public void onEnable() {
		this.getCommand("cheer").setExecutor(new CheerCommand());
	}
	
	@Override
	public void onDisable() {
	}
	
}
