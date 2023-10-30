package Model;
import java.util.ArrayList;

import DataAccess.PlayerDataAccess;
import DataObject.PlayerDataObject;
import DomainObject.PlayerDomainObject;
import restService.Message;


public class PlayerModel {
	
	public static PlayerDomainObject GetPlayerById(Message message, int id) {
		PlayerDataObject playerData = PlayerDataAccess.getPlayerById(id);
		PlayerDomainObject playerDomain = new PlayerDomainObject(playerData);
		return playerDomain;
	}

	public static ArrayList<PlayerDomainObject> GetAllPlayers(Message messasge) {
		ArrayList<PlayerDataObject> playerDataList = PlayerDataAccess.getAllPlayers();
		ArrayList<PlayerDomainObject> playerDomainList = PlayerDomainObject.MapList(playerDataList);
		return playerDomainList;
	}



	public static boolean ValidatePlayerById(int id) {
		if (PlayerDataAccess.getPlayerById(id) == null)
			return false;
		return true;
	}
	
	public static PlayerDomainObject RegisterPlayer(Message message, PlayerDomainObject domainPlayerToCreate) {
		//This needs to be implemented.
		String usernameAlreadyTaken = "This username is already taken.";
		String invalidUsername = "Invalid username.  The username must contain between 6 and 12 characters and must only contain English letters and numbers.";
		String invalidPassword = "Invalid password.  The password must contain between 6 and 12 characters and must only contain English letters and numbers.";
		String reg = "^[a-zA-Z0-9]*$";
	
		PlayerDataObject p1 = PlayerDataAccess.getPlayerByUsername(domainPlayerToCreate.userName);
		
		//checking if the username is already taken
		if (p1 != null) {
			message.addErrorMessage(usernameAlreadyTaken);
		} 
		else {
			String userName = domainPlayerToCreate.userName;
			String password = domainPlayerToCreate.password;

			//checking if the username contains characters other than letters and numbers
			if (!userName.matches(reg)) {
				message.addErrorMessage(invalidUsername);
			} 
			
			//checking if the length of the username is less than 6 or greater than 12
			else if (userName.length() < 6 || userName.length() > 12 ) {
				message.addErrorMessage(invalidUsername);
			} 
			
			//checking if the password contains characters other than letters and numbers
			else if (!password.matches(reg)) {
				message.addErrorMessage(invalidPassword);
			}
			//checking if the length of the password is less than 6 or greater than 12
			else if (password.length() < 6 || password.length() > 12 ) {
				message.addErrorMessage(invalidPassword);
			} 
			
			//this means all the conditions are satisfied, so we can create a new player
			else {
				PlayerDataObject dataPlayerToCreate = new PlayerDataObject(domainPlayerToCreate.id, userName, password);
				PlayerDataObject dataPlayerCreated = PlayerDataAccess.createPlayer(dataPlayerToCreate);
				PlayerDomainObject domainPlayerCreated = new PlayerDomainObject(dataPlayerCreated);
				return domainPlayerCreated;
			}
		}
		return null;
				
	}


}
