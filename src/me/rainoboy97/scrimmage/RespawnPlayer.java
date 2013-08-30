package me.rainoboy97.scrimmage;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings("unused")
public class RespawnPlayer implements Runnable {

	private final JavaPlugin plugin;
	Player deadPlayer;
	Location respawnLoc;
	Inventory inv;

	public RespawnPlayer(JavaPlugin plugin, Player player, Location loc) {
		this.plugin = plugin;
		deadPlayer = player;
		respawnLoc = loc;
		inv = deadPlayer.getInventory();
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public void run() {
		if (deadPlayer.getGameMode() == GameMode.CREATIVE) {
			deadPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 1));
			deadPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1000000, 1));
		}
		if (deadPlayer.getGameMode() == GameMode.SURVIVAL) {
			deadPlayer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0));
		}
		deadPlayer.teleport(respawnLoc);
		if (deadPlayer.getGameMode() != GameMode.CREATIVE) {
			inv.clear();
			ItemStack item;
			Iterator iterItems = Var.items.iterator();
			Iterator iterAmounts = Var.amounts.iterator();
			for (int i = 1; i <= Var.itemAmount; i++) {
				if (iterItems.hasNext() && iterAmounts.hasNext()) {
					Object idEntry = iterItems.next();
					Object amountEntry = iterAmounts.next();
					item = new ItemStack(Integer.parseInt(idEntry.toString()), Integer.parseInt(amountEntry.toString()));
					inv.setItem(i - 1, item);
				}
			}
			if (Var.helmet != 0) {
				ItemStack helmet = new ItemStack(Var.helmet, 1);
				deadPlayer.getInventory().setHelmet(helmet);
			} else {
				deadPlayer.getInventory().setHelmet(null);
			}
			if (Var.chestplate != 0) {
				ItemStack chestplate = new ItemStack(Var.chestplate, 1);
				deadPlayer.getInventory().setChestplate(chestplate);
			} else {
				deadPlayer.getInventory().setChestplate(null);
			}
			if (Var.leggings != 0) {
				ItemStack leggings = new ItemStack(Var.leggings, 1);
				deadPlayer.getInventory().setLeggings(leggings);
			} else {
				deadPlayer.getInventory().setLeggings(null);
			}
			if (Var.boots != 0) {
				ItemStack boots = new ItemStack(Var.boots, 1);
				deadPlayer.getInventory().setBoots(boots);
			} else {
				deadPlayer.getInventory().setBoots(null);
			}
			deadPlayer.updateInventory();
		}
	}
}