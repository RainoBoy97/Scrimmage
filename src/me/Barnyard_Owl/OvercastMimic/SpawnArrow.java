package me.Barnyard_Owl.OvercastMimic;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

@SuppressWarnings("unused")
public class SpawnArrow implements Runnable {

	private final JavaPlugin plugin;
	Location loc;
	Vector vec;
	public SpawnArrow(JavaPlugin plugin, Location loc, Vector vec) {
		this.plugin = plugin;
		this.loc = loc;
		this.vec = vec;
	}

	public void run() {
		loc.getWorld().spawnArrow(loc, vec, 1, 0);
	}
}
