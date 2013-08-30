package me.rainoboy97.scrimmage;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings("unused")
public class RemoveBlock implements Runnable {

	private final JavaPlugin plugin;
	Block block;

	public RemoveBlock(JavaPlugin plugin, Block block) {
		this.plugin = plugin;
		this.block = block;
	}

	public void run() {
		if (block.getType() == Material.AIR) {
			block.setTypeId(111);
			block.setType(Material.AIR);
		}
	}
}
