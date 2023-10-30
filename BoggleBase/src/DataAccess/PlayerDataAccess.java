package DataAccess;

import java.util.ArrayList;

import DataObject.PlayerDataObject;

public class PlayerDataAccess {

	private static ArrayList<PlayerDataObject> players;

	public PlayerDataAccess() {
		initialize();
	}

	private void initialize() {
		players = new ArrayList<PlayerDataObject>();
	
		//Default Data
		//R. Florin
		players.add(new PlayerDataObject(getNextId(), "rflorin", "pass1"));
		//K. Findley
		players.add(new PlayerDataObject(getNextId(), "kfindley", "pass2"));
		
	}

	private static int getNextId() {
		return players.size()+1;
	}

	public static ArrayList<PlayerDataObject> getAllPlayers() {
		return players;
	}

	public static PlayerDataObject getPlayerById(int id) {
		
		for (int i=0; i < players.size(); i++) {
			if (players.get(i).id == id) {
				return players.get(i);
			}
		}
		
		return null;
	}

	public static PlayerDataObject getPlayerByUsername(String username) {
		
		for (int i=0; i < players.size(); i++) {
			if (players.get(i).userName.equals(username)) {
				return players.get(i);
			}
		}
		
		return null;
	}

	public static PlayerDataObject createPlayer(PlayerDataObject data) {
		//This needs to be implemented.
		
		// Check if player with the same username already exists
		for (PlayerDataObject player : players) {
			if (player.userName.equals(data.userName)) {
				return null; // Player with the same username already exists
			}
		}
		
		// Player with the same username doesn't exist, add the new player to the list
		data.id = getNextId();
		players.add(data);
		
		return getPlayerByUsername(data.userName);
	}
}