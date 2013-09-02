package me.rainoboy97.scrimmage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

public class TeamHandler {

	public enum Team {
		RED, BLUE, OBSERVER;
	}

	private static Map<String, Team> players = new HashMap<String, Team>();

	public static Team getTeam(Player player) {
		return players.get(player.getName());
	}

	public static void addPlayer(Player player, Team team) {
		players.put(player.getName(), team);
	}

	private static void removePlayer(Player player) {
		players.remove(player.getName());
	}

	public static int count(Team team) {
		int res = 0;
		for (String s : players.keySet()) {
			if (players.get(s) == team) {
				res++;
			}
		}
		return res;
	}

	public static List<String> getPlayersOnTeam(Team team) {
		List<String> res = new ArrayList<String>();
		for (String s : players.keySet()) {
			if (players.get(s) == team) {
				res.add(s);
			}
		}
		return res;
	}

}
