package me.Barnyard_Owl.OvercastMimic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
//import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

// Main Class, not much actual use. This contains the basis and calling other
// classes.
public class OvercastMimic extends JavaPlugin {
	public final Logger logger = Logger.getLogger("Minecraft");
	@SuppressWarnings("unused")
	private OvercastMimic plugin;
	// public static String[] team;
	// public static String[] enemyTeam;
	@SuppressWarnings("rawtypes")
	public static List team = new ArrayList();
	@SuppressWarnings("rawtypes")
	public static List enemyTeam = new ArrayList();
	@SuppressWarnings("rawtypes")
	public static List specs = new ArrayList();
	public static boolean gameActive = false;
	public static boolean starting = false;

	// Activates on Enabling of the plugin
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {
		// Print to console.
		PluginDescriptionFile pdfFile = this.getDescription();

		this.logger.info(pdfFile.getName() + " " + pdfFile.getVersion()
				+ " has been enabled successfully!");
		// Enable the event listener.
		new MyEventListener(this);
		// Command setup.
		getCommand("start").setExecutor(new CommandListener(this));
		getCommand("test").setExecutor(new CommandListener(this));
		getCommand("join").setExecutor(new CommandListener(this));
		getCommand("g").setExecutor(new CommandListener(this));
		getCommand("cancel").setExecutor(new CommandListener(this));
		getCommand("tp").setExecutor(new CommandListener(this));
		// Config setup.
		FileConfiguration config = getConfig();
		config.addDefault("Team1.DisplayName", "blue");
		config.addDefault("Team2.DisplayName", "red");
		config.addDefault("Team1.TechnicalColor", "9");
		config.addDefault("Team2.TechnicalColor", "c");
		config.addDefault("Team1.Wool1.DisplayName", "purple");
		config.addDefault("Team1.Wool1.TechnicalColor", "5");
		config.addDefault("Team1.Wool2.DisplayName", "yellow");
		config.addDefault("Team1.Wool2.TechnicalColor", "e");
		config.addDefault("Team2.Wool1.DisplayName", "lime");
		config.addDefault("Team2.Wool1.TechnicalColor", "a");
		config.addDefault("Team2.Wool2.DisplayName", "orange");
		config.addDefault("Team2.Wool2.TechnicalColor", "6");
		config.addDefault("Team1.Wool1.PlaceCoords", "-1|5|-64");
		config.addDefault("Team1.Wool2.PlaceCoords", "1|5|-64");
		config.addDefault("Team2.Wool1.PlaceCoords", "1|5|78");
		config.addDefault("Team2.Wool2.PlaceCoords", "-1|5|78");
		config.addDefault("Team1.Wool.WoolRoom", "-Z|-104");
		config.addDefault("Team2.Wool.WoolRoom", "+Z|119");
		config.addDefault("Team1.Spawn", "0.5|4|-54.5|0|0");
		config.addDefault("Team2.Spawn", "0.5|4|69.5|-180|0");
		config.addDefault("Observers.Spawn", "44.5|8|7.5|90|0");
		config.addDefault("Map.Name", "RFV2");
		config.addDefault("Map.Authors",
				"Plastix, IM_A_H0B0, MonsieurApple, Anxuiz");
		config.addDefault("Config.Creator", "Barnyard_Owl");
		config.addDefault("Config.Version", "0.3");
		config.addDefault("Map.Height", 30);
		config.addDefault("Kits.EnableMetadata", false);
		config.addDefault("Kits.EnableEnchantments", false);
		config.addDefault("Kits.Items.TotalAmount", 9);
		config.addDefault("Kits.Items.Item1", 267);
		config.addDefault("Kits.Items.Item2", 261);
		config.addDefault("Kits.Items.Item3", 257);
		config.addDefault("Kits.Items.Item4", 17);
		config.addDefault("Kits.Items.Item5", 350);
		config.addDefault("Kits.Items.Item6", 20);
		config.addDefault("Kits.Items.Item7", 262);
		config.addDefault("Kits.Items.Item8", 262);
		config.addDefault("Kits.Items.Item9", 65);
		config.addDefault("Kits.Items.Amounts.Item1", 1);
		config.addDefault("Kits.Items.Amounts.Item2", 1);
		config.addDefault("Kits.Items.Amounts.Item3", 1);
		config.addDefault("Kits.Items.Amounts.Item4", 64);
		config.addDefault("Kits.Items.Amounts.Item5", 64);
		config.addDefault("Kits.Items.Amounts.Item6", 64);
		config.addDefault("Kits.Items.Amounts.Item7", 64);
		config.addDefault("Kits.Items.Amounts.Item8", 64);
		config.addDefault("Kits.Items.Amounts.Item9", 64);
		config.addDefault("Kits.Armor.Helmet", 0);
		config.addDefault("Kits.Armor.Chestplate", 0);
		config.addDefault("Kits.Armor.Leggings", 0);
		config.addDefault("Kits.Armor.Boots", 0);
		config.options().copyDefaults(true);
		saveConfig();
		config.set("Kits.EnableMetadata", false);
		config.set("Kits.EnableEnchantments", false);
		saveConfig();
		// Config reading + interpretting.
		Var.helmet = config.getInt("Kits.Armor.Helmet");
		Var.chestplate = config.getInt("Kits.Armor.Chestplate");
		Var.leggings = config.getInt("Kits.Armor.Leggings");
		Var.boots = config.getInt("Kits.Armor.Boots");
		Var.itemAmount = config.getInt("Kits.Items.TotalAmount");
		for (int i = 1; i <= Var.itemAmount; i++) {
			Var.items.add(config.getInt("Kits.Items.Item" + i));
			Var.amounts.add(config.getInt("Kits.Items.Amounts.Item" + i));
		}
		Var.teamWool1Place = config.getString("Team1.Wool1.PlaceCoords").split(
				"\\|");
		Var.teamWool2Place = config.getString("Team1.Wool2.PlaceCoords").split(
				"\\|");
		Var.enemyTeamWool1Place = config.getString("Team2.Wool1.PlaceCoords")
				.split("\\|");
		Var.enemyTeamWool2Place = config.getString("Team2.Wool2.PlaceCoords")
				.split("\\|");
		Var.teamWoolRoom = config.getString("Team1.Wool.WoolRoom").split("\\|");
		Var.enemyTeamWoolRoom = config.getString("Team2.Wool.WoolRoom").split(
				"\\|");
		Var.configMaker = config.getString("Config.Creator");
		Var.mapName = config.getString("Map.Name");
		Var.mapMakers = config.getString("Map.Authors");
		Var.heightLimit = config.getInt("Map.Height") - 1;
		int counter = 0;
		double x = 0;
		double y = 0;
		double z = 0;
		float yaw = 0;
		float pitch = 0;
		for (String i : config.getString("Team1.Spawn").split("\\|")) {
			counter++;
			switch (counter) {
			case 1:
				// Bukkit.broadcastMessage("Debug: 1, " + i);
				x = Double.parseDouble(i);
			case 2:
				// Bukkit.broadcastMessage("Debug: 2, " + i);
				y = Double.parseDouble(i);
			case 3:
				// Bukkit.broadcastMessage("Debug: 3, " + i);
				z = Double.parseDouble(i);
			case 4:
				// Bukkit.broadcastMessage("Debug: 4, " + i);
				yaw = Float.parseFloat(i);
			case 5:
				// Bukkit.broadcastMessage("Debug: 5, " + i);
				pitch = Float.parseFloat(i);
			default:
			}
		}
		Var.teamSpawn = new Location(Bukkit.getWorld(Var.mapName), x, y, z,
				yaw, pitch);
		counter = 0;
		x = 0;
		y = 0;
		z = 0;
		yaw = 0;
		pitch = 0;
		for (String i : config.getString("Team2.Spawn").split("\\|")) {
			counter++;
			switch (counter) {
			case 1:
				x = Double.parseDouble(i);
			case 2:
				y = Double.parseDouble(i);
			case 3:
				z = Double.parseDouble(i);
			case 4:
				yaw = Float.parseFloat(i);
			case 5:
				pitch = Float.parseFloat(i);
			default:
			}
		}
		Var.enemyTeamSpawn = new Location(Bukkit.getWorld(Var.mapName), x, y,
				z, yaw, pitch);
		counter = 0;
		x = 0;
		y = 0;
		z = 0;
		yaw = 0;
		pitch = 0;
		for (String i : config.getString("Observers.Spawn").split("\\|")) {
			counter++;
			switch (counter) {
			case 1:
				x = Double.parseDouble(i);
			case 2:
				y = Double.parseDouble(i);
			case 3:
				z = Double.parseDouble(i);
			case 4:
				yaw = Float.parseFloat(i);
			case 5:
				pitch = Float.parseFloat(i);
			default:
			}
		}
		Var.observerSpawn = new Location(Bukkit.getWorld(Var.mapName), x, y, z,
				yaw, pitch);
		Var.teamDisplayName = config.getString("Team1.DisplayName")
				.toUpperCase();
		Var.enemyTeamDisplayName = config.getString("Team2.DisplayName")
				.toUpperCase();
		String TechnicalColor;
		TechnicalColor = config.getString("Team1.TechnicalColor");
		if (TechnicalColor.equals("0")) {
			Var.teamTechnicalColor = ChatColor.BLACK;
		} else if (TechnicalColor.equals("1")) {
			Var.teamTechnicalColor = ChatColor.DARK_BLUE;
		} else if (TechnicalColor.equals("2")) {
			Var.teamTechnicalColor = ChatColor.DARK_GREEN;
		} else if (TechnicalColor.equals("3")) {
			Var.teamTechnicalColor = ChatColor.DARK_AQUA;
		} else if (TechnicalColor.equals("4")) {
			Var.teamTechnicalColor = ChatColor.DARK_RED;
		} else if (TechnicalColor.equals("5")) {
			Var.teamTechnicalColor = ChatColor.DARK_PURPLE;
		} else if (TechnicalColor.equals("6")) {
			Var.teamTechnicalColor = ChatColor.GOLD;
		} else if (TechnicalColor.equals("7")) {
			Var.teamTechnicalColor = ChatColor.GRAY;
		} else if (TechnicalColor.equals("8")) {
			Var.teamTechnicalColor = ChatColor.DARK_GRAY;
		} else if (TechnicalColor.equals("9")) {
			Var.teamTechnicalColor = ChatColor.BLUE;
		} else if (TechnicalColor.equals("a")) {
			Var.teamTechnicalColor = ChatColor.GREEN;
		} else if (TechnicalColor.equals("b")) {
			Var.teamTechnicalColor = ChatColor.AQUA;
		} else if (TechnicalColor.equals("c")) {
			Var.teamTechnicalColor = ChatColor.RED;
		} else if (TechnicalColor.equals("d")) {
			Var.teamTechnicalColor = ChatColor.LIGHT_PURPLE;
		} else if (TechnicalColor.equals("e")) {
			Var.teamTechnicalColor = ChatColor.YELLOW;
		} else if (TechnicalColor.equals("f")) {
			Var.teamTechnicalColor = ChatColor.WHITE;
		} else {
			this.logger.log(Level.SEVERE,
					"Error, you misconfigured Team1's TechnicalColor!");
		}
		TechnicalColor = config.getString("Team2.TechnicalColor");
		if (TechnicalColor.equals("0")) {
			Var.enemyTeamTechnicalColor = ChatColor.BLACK;
		} else if (TechnicalColor.equals("1")) {
			Var.enemyTeamTechnicalColor = ChatColor.DARK_BLUE;
		} else if (TechnicalColor.equals("2")) {
			Var.enemyTeamTechnicalColor = ChatColor.DARK_GREEN;
		} else if (TechnicalColor.equals("3")) {
			Var.enemyTeamTechnicalColor = ChatColor.DARK_AQUA;
		} else if (TechnicalColor.equals("4")) {
			Var.enemyTeamTechnicalColor = ChatColor.DARK_RED;
		} else if (TechnicalColor.equals("5")) {
			Var.enemyTeamTechnicalColor = ChatColor.DARK_PURPLE;
		} else if (TechnicalColor.equals("6")) {
			Var.enemyTeamTechnicalColor = ChatColor.GOLD;
		} else if (TechnicalColor.equals("7")) {
			Var.enemyTeamTechnicalColor = ChatColor.GRAY;
		} else if (TechnicalColor.equals("8")) {
			Var.enemyTeamTechnicalColor = ChatColor.DARK_GRAY;
		} else if (TechnicalColor.equals("9")) {
			Var.enemyTeamTechnicalColor = ChatColor.BLUE;
		} else if (TechnicalColor.equals("a")) {
			Var.enemyTeamTechnicalColor = ChatColor.GREEN;
		} else if (TechnicalColor.equals("b")) {
			Var.enemyTeamTechnicalColor = ChatColor.AQUA;
		} else if (TechnicalColor.equals("c")) {
			Var.enemyTeamTechnicalColor = ChatColor.RED;
		} else if (TechnicalColor.equals("d")) {
			Var.enemyTeamTechnicalColor = ChatColor.LIGHT_PURPLE;
		} else if (TechnicalColor.equals("e")) {
			Var.enemyTeamTechnicalColor = ChatColor.YELLOW;
		} else if (TechnicalColor.equals("f")) {
			Var.enemyTeamTechnicalColor = ChatColor.WHITE;
		} else {
			this.logger.log(Level.SEVERE,
					"Error, you misconfigured Team2's TechnicalColor!");
		}
		Var.teamWool1DisplayName = config.getString("Team1.Wool1.DisplayName")
				.toUpperCase();
		TechnicalColor = config.getString("Team1.Wool1.TechnicalColor");
		if (TechnicalColor.equals("0")) {
			Var.teamWool1TechnicalColor = ChatColor.BLACK;
			Var.teamWool1TechnicalDye = DyeColor.BLACK;
		} else if (TechnicalColor.equals("1")) {
			Var.teamWool1TechnicalColor = ChatColor.DARK_BLUE;
			Var.teamWool1TechnicalDye = DyeColor.BLUE;
		} else if (TechnicalColor.equals("2")) {
			Var.teamWool1TechnicalColor = ChatColor.DARK_GREEN;
			Var.teamWool1TechnicalDye = DyeColor.GREEN;
		} else if (TechnicalColor.equals("3")) {
			Var.teamWool1TechnicalColor = ChatColor.DARK_AQUA;
			Var.teamWool1TechnicalDye = DyeColor.CYAN;
		} else if (TechnicalColor.equals("4")) {
			Var.teamWool1TechnicalColor = ChatColor.DARK_RED;
			Var.teamWool1TechnicalDye = DyeColor.RED;
		} else if (TechnicalColor.equals("5")) {
			Var.teamWool1TechnicalColor = ChatColor.DARK_PURPLE;
			Var.teamWool1TechnicalDye = DyeColor.PURPLE;
		} else if (TechnicalColor.equals("6")) {
			Var.teamWool1TechnicalColor = ChatColor.GOLD;
			Var.teamWool1TechnicalDye = DyeColor.ORANGE;
		} else if (TechnicalColor.equals("7")) {
			Var.teamWool1TechnicalColor = ChatColor.GRAY;
			Var.teamWool1TechnicalDye = DyeColor.SILVER;
		} else if (TechnicalColor.equals("8")) {
			Var.teamWool1TechnicalColor = ChatColor.DARK_GRAY;
			Var.teamWool1TechnicalDye = DyeColor.GRAY;
		} else if (TechnicalColor.equals("9")) {
			Var.teamWool1TechnicalColor = ChatColor.BLUE;
			Var.teamWool1TechnicalDye = DyeColor.LIGHT_BLUE;
		} else if (TechnicalColor.equals("a")) {
			Var.teamWool1TechnicalColor = ChatColor.GREEN;
			Var.teamWool1TechnicalDye = DyeColor.LIME;
		} else if (TechnicalColor.equals("b")) {
			Var.teamWool1TechnicalColor = ChatColor.AQUA;
			Var.teamWool1TechnicalDye = DyeColor.LIGHT_BLUE;
		} else if (TechnicalColor.equals("c")) {
			Var.teamWool1TechnicalColor = ChatColor.RED;
			Var.teamWool1TechnicalDye = DyeColor.PINK;
		} else if (TechnicalColor.equals("d")) {
			Var.teamWool1TechnicalColor = ChatColor.LIGHT_PURPLE;
			Var.teamWool1TechnicalDye = DyeColor.MAGENTA;
		} else if (TechnicalColor.equals("e")) {
			Var.teamWool1TechnicalColor = ChatColor.YELLOW;
			Var.teamWool1TechnicalDye = DyeColor.YELLOW;
		} else if (TechnicalColor.equals("f")) {
			Var.teamWool1TechnicalColor = ChatColor.WHITE;
			Var.teamWool1TechnicalDye = DyeColor.WHITE;
		} else {
			this.logger.log(Level.SEVERE,
					"Error, you misconfigured Team1: Wool1's TechnicalColor!");
		}
		Var.teamWool2DisplayName = config.getString("Team1.Wool2.DisplayName")
				.toUpperCase();
		TechnicalColor = config.getString("Team1.Wool2.TechnicalColor");
		if (TechnicalColor.equals("0")) {
			Var.teamWool2TechnicalColor = ChatColor.BLACK;
			Var.teamWool2TechnicalDye = DyeColor.BLACK;
		} else if (TechnicalColor.equals("1")) {
			Var.teamWool2TechnicalColor = ChatColor.DARK_BLUE;
			Var.teamWool2TechnicalDye = DyeColor.BLUE;
		} else if (TechnicalColor.equals("2")) {
			Var.teamWool2TechnicalColor = ChatColor.DARK_GREEN;
			Var.teamWool2TechnicalDye = DyeColor.GREEN;
		} else if (TechnicalColor.equals("3")) {
			Var.teamWool2TechnicalColor = ChatColor.DARK_AQUA;
			Var.teamWool2TechnicalDye = DyeColor.CYAN;
		} else if (TechnicalColor.equals("4")) {
			Var.teamWool2TechnicalColor = ChatColor.DARK_RED;
			Var.teamWool2TechnicalDye = DyeColor.RED;
		} else if (TechnicalColor.equals("5")) {
			Var.teamWool2TechnicalColor = ChatColor.DARK_PURPLE;
			Var.teamWool2TechnicalDye = DyeColor.PURPLE;
		} else if (TechnicalColor.equals("6")) {
			Var.teamWool2TechnicalColor = ChatColor.GOLD;
			Var.teamWool2TechnicalDye = DyeColor.ORANGE;
		} else if (TechnicalColor.equals("7")) {
			Var.teamWool2TechnicalColor = ChatColor.GRAY;
			Var.teamWool2TechnicalDye = DyeColor.SILVER;
		} else if (TechnicalColor.equals("8")) {
			Var.teamWool2TechnicalColor = ChatColor.DARK_GRAY;
			Var.teamWool2TechnicalDye = DyeColor.GRAY;
		} else if (TechnicalColor.equals("9")) {
			Var.teamWool2TechnicalColor = ChatColor.BLUE;
			Var.teamWool2TechnicalDye = DyeColor.LIGHT_BLUE;
		} else if (TechnicalColor.equals("a")) {
			Var.teamWool2TechnicalColor = ChatColor.GREEN;
			Var.teamWool2TechnicalDye = DyeColor.LIME;
		} else if (TechnicalColor.equals("b")) {
			Var.teamWool2TechnicalColor = ChatColor.AQUA;
			Var.teamWool2TechnicalDye = DyeColor.LIGHT_BLUE;
		} else if (TechnicalColor.equals("c")) {
			Var.teamWool2TechnicalColor = ChatColor.RED;
			Var.teamWool2TechnicalDye = DyeColor.PINK;
		} else if (TechnicalColor.equals("d")) {
			Var.teamWool2TechnicalColor = ChatColor.LIGHT_PURPLE;
			Var.teamWool2TechnicalDye = DyeColor.MAGENTA;
		} else if (TechnicalColor.equals("e")) {
			Var.teamWool2TechnicalColor = ChatColor.YELLOW;
			Var.teamWool2TechnicalDye = DyeColor.YELLOW;
		} else if (TechnicalColor.equals("f")) {
			Var.teamWool2TechnicalColor = ChatColor.WHITE;
			Var.teamWool2TechnicalDye = DyeColor.WHITE;
		} else {
			this.logger.log(Level.SEVERE,
					"Error, you misconfigured Team1: Wool2's TechnicalColor!");
		}
		Var.enemyTeamWool1DisplayName = config.getString(
				"Team2.Wool1.DisplayName").toUpperCase();
		TechnicalColor = config.getString("Team2.Wool1.TechnicalColor");
		if (TechnicalColor.equals("0")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.BLACK;
			Var.enemyTeamWool1TechnicalDye = DyeColor.BLACK;
		} else if (TechnicalColor.equals("1")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.DARK_BLUE;
			Var.enemyTeamWool1TechnicalDye = DyeColor.BLUE;
		} else if (TechnicalColor.equals("2")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.DARK_GREEN;
			Var.enemyTeamWool1TechnicalDye = DyeColor.GREEN;
		} else if (TechnicalColor.equals("3")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.DARK_AQUA;
			Var.enemyTeamWool1TechnicalDye = DyeColor.CYAN;
		} else if (TechnicalColor.equals("4")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.DARK_RED;
			Var.enemyTeamWool1TechnicalDye = DyeColor.RED;
		} else if (TechnicalColor.equals("5")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.DARK_PURPLE;
			Var.enemyTeamWool1TechnicalDye = DyeColor.PURPLE;
		} else if (TechnicalColor.equals("6")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.GOLD;
			Var.enemyTeamWool1TechnicalDye = DyeColor.ORANGE;
		} else if (TechnicalColor.equals("7")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.GRAY;
			Var.enemyTeamWool1TechnicalDye = DyeColor.SILVER;
		} else if (TechnicalColor.equals("8")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.DARK_GRAY;
			Var.enemyTeamWool1TechnicalDye = DyeColor.GRAY;
		} else if (TechnicalColor.equals("9")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.BLUE;
			Var.enemyTeamWool1TechnicalDye = DyeColor.LIGHT_BLUE;
		} else if (TechnicalColor.equals("a")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.GREEN;
			Var.enemyTeamWool1TechnicalDye = DyeColor.LIME;
		} else if (TechnicalColor.equals("b")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.AQUA;
			Var.enemyTeamWool1TechnicalDye = DyeColor.LIGHT_BLUE;
		} else if (TechnicalColor.equals("c")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.RED;
			Var.enemyTeamWool1TechnicalDye = DyeColor.PINK;
		} else if (TechnicalColor.equals("d")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.LIGHT_PURPLE;
			Var.enemyTeamWool1TechnicalDye = DyeColor.MAGENTA;
		} else if (TechnicalColor.equals("e")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.YELLOW;
			Var.enemyTeamWool1TechnicalDye = DyeColor.YELLOW;
		} else if (TechnicalColor.equals("f")) {
			Var.enemyTeamWool1TechnicalColor = ChatColor.WHITE;
			Var.enemyTeamWool1TechnicalDye = DyeColor.WHITE;
		} else {
			this.logger.log(Level.SEVERE,
					"Error, you misconfigured Team2: Wool1's TechnicalColor!");
		}
		Var.enemyTeamWool2DisplayName = config.getString(
				"Team2.Wool2.DisplayName").toUpperCase();
		TechnicalColor = config.getString("Team2.Wool2.TechnicalColor");
		if (TechnicalColor.equals("0")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.BLACK;
			Var.enemyTeamWool2TechnicalDye = DyeColor.BLACK;
		} else if (TechnicalColor.equals("1")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.DARK_BLUE;
			Var.enemyTeamWool2TechnicalDye = DyeColor.BLUE;
		} else if (TechnicalColor.equals("2")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.DARK_GREEN;
			Var.enemyTeamWool2TechnicalDye = DyeColor.GREEN;
		} else if (TechnicalColor.equals("3")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.DARK_AQUA;
			Var.enemyTeamWool2TechnicalDye = DyeColor.CYAN;
		} else if (TechnicalColor.equals("4")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.DARK_RED;
			Var.enemyTeamWool2TechnicalDye = DyeColor.RED;
		} else if (TechnicalColor.equals("5")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.DARK_PURPLE;
			Var.enemyTeamWool2TechnicalDye = DyeColor.PURPLE;
		} else if (TechnicalColor.equals("6")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.GOLD;
			Var.enemyTeamWool2TechnicalDye = DyeColor.ORANGE;
		} else if (TechnicalColor.equals("7")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.GRAY;
			Var.enemyTeamWool2TechnicalDye = DyeColor.SILVER;
		} else if (TechnicalColor.equals("8")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.DARK_GRAY;
			Var.enemyTeamWool2TechnicalDye = DyeColor.GRAY;
		} else if (TechnicalColor.equals("9")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.BLUE;
			Var.enemyTeamWool2TechnicalDye = DyeColor.LIGHT_BLUE;
		} else if (TechnicalColor.equals("a")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.GREEN;
			Var.enemyTeamWool2TechnicalDye = DyeColor.LIME;
		} else if (TechnicalColor.equals("b")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.AQUA;
			Var.enemyTeamWool2TechnicalDye = DyeColor.LIGHT_BLUE;
		} else if (TechnicalColor.equals("c")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.RED;
			Var.enemyTeamWool2TechnicalDye = DyeColor.PINK;
		} else if (TechnicalColor.equals("d")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.LIGHT_PURPLE;
			Var.enemyTeamWool2TechnicalDye = DyeColor.MAGENTA;
		} else if (TechnicalColor.equals("e")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.YELLOW;
			Var.enemyTeamWool2TechnicalDye = DyeColor.YELLOW;
		} else if (TechnicalColor.equals("f")) {
			Var.enemyTeamWool2TechnicalColor = ChatColor.WHITE;
			Var.enemyTeamWool2TechnicalDye = DyeColor.WHITE;
		} else {
			this.logger.log(Level.SEVERE,
					"Error, you misconfigured Team2: Wool2's TechnicalColor!");
		}
		// Setup scoreboards.
		ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = scoreboardManager.getMainScoreboard();
		try {
			scoreboard.registerNewTeam(Var.teamDisplayName);
		} catch (Exception e) { // If the team was already created
		}
		try {
			scoreboard.registerNewTeam(Var.enemyTeamDisplayName);
		} catch (Exception e) { // If the team was already created
		}
		try {
			scoreboard.registerNewTeam("spec");
		} catch (Exception e) { // If the team was already created
		}
		Team blue = scoreboard.getTeam(Var.teamDisplayName);
		Team red = scoreboard.getTeam(Var.enemyTeamDisplayName);
		Team spec = scoreboard.getTeam("spec");

		blue.setAllowFriendlyFire(false);
		blue.setCanSeeFriendlyInvisibles(false);
		blue.setPrefix(Var.teamTechnicalColor + "➜ " + Var.teamTechnicalColor);

		red.setAllowFriendlyFire(false);
		red.setCanSeeFriendlyInvisibles(false);
		red.setPrefix(Var.enemyTeamTechnicalColor + "➜ "
				+ Var.enemyTeamTechnicalColor);

		spec.setAllowFriendlyFire(false);
		spec.setCanSeeFriendlyInvisibles(true);
		spec.setPrefix(ChatColor.AQUA + "➜ " + ChatColor.AQUA);
		// Teleport all players to obs spawn and clear the inv. (Assuming
		// reload)
		for (Player player : Bukkit.getOnlinePlayers()) {
			specs.add(player.getDisplayName());
			spec.addPlayer(Bukkit.getOfflinePlayer(player.getDisplayName()));
			player.getInventory().clear();
			player.getInventory().setHelmet(null);
			player.getInventory().setChestplate(null);
			player.getInventory().setLeggings(null);
			player.getInventory().setBoots(null);
			player.setGameMode(GameMode.CREATIVE);
			player.addPotionEffect(new PotionEffect(
					PotionEffectType.INVISIBILITY, 1000000, 1));
			player.addPotionEffect(new PotionEffect(
					PotionEffectType.SATURATION, 1000000, 1));
			player.teleport(Var.observerSpawn);
		}
	}

	// Activates on disabling of the plugin
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();

		this.logger.info(pdfFile.getName() + " " + pdfFile.getVersion()
				+ " has been disabled successfully!");
	}

	// Returns which team a player is currently on.
	public static String team(Player player) {
		String team = "";
		for (Object e : OvercastMimic.team) {
			String i = (String) e;
			if (player.getDisplayName().equalsIgnoreCase(i)) {
				team = Var.teamDisplayName;
			}
		}
		for (Object e : enemyTeam) {
			String i = (String) e;
			if (player.getDisplayName().equalsIgnoreCase(i)) {
				team = Var.enemyTeamDisplayName;
			}
		}
		for (Object e : specs) {
			String i = (String) e;
			if (player.getDisplayName().equalsIgnoreCase(i)) {
				team = "spec";
			}
		}
		return team;
	}
}