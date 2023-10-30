package Model;
import java.util.ArrayList;

import DataAccess.DictionaryDataAccess;
import DataAccess.GameDataAccess;
import DataAccess.PlayerDataAccess;
import DataObject.BoardDataObject;
import DataObject.GameDataObject;
import DataObject.PlayerDataObject;
import DomainObject.BoardDomainObject;
import DomainObject.GameDomainObject;
import restService.Message;


public class GameModel {
	

	public static ArrayList<GameDomainObject> GetAllGameSummaries(Message message) {
		ArrayList<GameDataObject> gameDataList = GameDataAccess.getAllGames();
		ArrayList<GameDomainObject> gameDomainList = GameDomainObject.MapList(gameDataList);
		return gameDomainList;
	}

	public static GameDomainObject GetGameDetailsByGameIdandPlayerId(Message message, int gameId, int playerId) throws Exception {
		//Return game details
		// if the game is setup - return the board and the current players words, and current score
		// if the game is complete - return both words, and the scores
	

		//Get the Game Details
		GameDataObject gameData = GameDataAccess.getGameById(gameId);
		GameDomainObject gameDomain = new GameDomainObject(gameData);

		boolean isPlayer1 = false;
		boolean isPlayer2 = false;
		
		if (gameDomain.player1Id == playerId) {
			isPlayer1 = true;
		} else if (gameDomain.player2Id == playerId) {
			isPlayer2 = true;
		} else {
			throw new Exception("Player " + playerId + " is not part of this game");
		}


		BoardDomainObject boardDomain = BoardModel.GetBoardDetailsByGameId(message, gameDomain.id);
		gameDomain.board = boardDomain;


		if (gameDomain.isStatusComplete) {
			//Get words
			gameDomain.player1Words = WordModel.getAllWordsForGameAndPlayer(message, gameId, gameDomain.player1Id);
			gameDomain.player2Words = WordModel.getAllWordsForGameAndPlayer(message, gameId, gameDomain.player2Id);
		} else if (gameDomain.isStatusSetup) {
			//If playerId == player1, then set player1Words.
			if (isPlayer1)
				gameDomain.player1Words = WordModel.getAllWordsForGameAndPlayer(message, gameId, gameDomain.player1Id);

			//If playerId == player2, then set player2Words.	
			if (isPlayer2)
				gameDomain.player2Words = WordModel.getAllWordsForGameAndPlayer(message, gameId, gameDomain.player2Id);
				
				
		}

		return gameDomain;
	}


	public static GameDomainObject GetGameById(Message message, int id) {
		GameDataObject gameData = GameDataAccess.getGameById(id);
		GameDomainObject gameDomain = new GameDomainObject(gameData);
		return gameDomain;
	}


	public static boolean ValidateGameById(int id) {
		if (GameDataAccess.getGameById(id) == null)
			return false;
		return true;
	}

	public static GameDomainObject CreateGame(Message message, GameDomainObject domainGameToCreate) {
		boolean isValid = true;

		if(domainGameToCreate.player1Id == 0){
			message.addErrorMessage("Service should have failed, player 1 missing");
			isValid = false;
		}

		if(domainGameToCreate.player2Id == 0){
			message.addErrorMessage("Service should have failed, player 2 missing");
			isValid = false;
		}

		if(domainGameToCreate.gameTypeId == 0){
			message.addErrorMessage("Service should have failed, game type missing");
			isValid = false;
		}

		if(isValid == false){
			message.addErrorMessage("Incorrect Error Message provided.");
			message.addErrorMessage("All parameters must be provided.");
		}

		if(PlayerModel.ValidatePlayerById(domainGameToCreate.player1Id) == false){
			message.addErrorMessage("Service should have failed, player 1 is invalid");
			message.addErrorMessage("Incorrect Error Message provided.");
			message.addErrorMessage("The Player 1 provided is an invalid player.");
			isValid = false;
		}

		if(PlayerModel.ValidatePlayerById(domainGameToCreate.player2Id) == false){
			message.addErrorMessage("Service should have failed, player 2 is invalid");
			message.addErrorMessage("Incorrect Error Message provided.");
			message.addErrorMessage("The Player 2 provided is an invalid player.");
			isValid = false;
		}

		if(domainGameToCreate.gameTypeId != 1){
			message.addErrorMessage("Service should have failed, game type is invalid");
			message.addErrorMessage("Incorrect Error Message provided.");
			message.addErrorMessage("The Game Type provided is an invalid game type.");
			isValid = false;
		}

		if(isValid == true) {

			GameDataObject data = new GameDataObject(-1, domainGameToCreate.gameTypeId, -1, "Setup", domainGameToCreate.player1Id, domainGameToCreate.player2Id);
			GameDataObject created = GameDataAccess.createGame(data);
			GameDomainObject newGame = new GameDomainObject(created);

			// Create a new board for the game
			BoardDomainObject board = BoardModel.CreateBoard(message, newGame.id, newGame.gameTypeId);
			newGame.board = board;

			return newGame;
		}	
		
		return null;

	}

	private static boolean ValidatePlayerById(int player2Id) {
		return false;
	}

	public static GameDomainObject GuessWord(Message message, int gameId, int playerId, String word) {
		//This needs to be implemented.

		boolean inDictionary = DictionaryDataAccess.validateWordInDictionary(word);
		boolean validForBoard = BoardModel.ValidateWordForBoard(message, gameId, word);

		return null;

	}


}
