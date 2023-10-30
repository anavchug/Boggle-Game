package Model;
import java.util.ArrayList;
import java.util.regex.Pattern;

import DataAccess.WordDataAccess;
import DataObject.WordDataObject;
import DomainObject.WordDomainObject;
import restService.Message;
import DataAccess.DictionaryDataAccess;


public class WordModel {
	
	public static WordDomainObject getWordById(Message message, int id) {
		WordDataObject wordData = WordDataAccess.getWordById(id);
		WordDomainObject wordDomain = new WordDomainObject(wordData);
		return wordDomain;
	}

	public static ArrayList<WordDomainObject> getAllWordsForGameAndPlayer(Message message, int gameId, int playerId) {
		ArrayList<WordDataObject> wordDataList = WordDataAccess.getAllWordsForGameAndPlayer(gameId, playerId);
		ArrayList<WordDomainObject> wordDomainList = WordDomainObject.MapList(wordDataList);
		return wordDomainList;
	}

	public static WordDomainObject GuessWord(Message message, WordDomainObject domainWordToCreate) {
		if(domainWordToCreate.playerId == 0 || domainWordToCreate.gameId == 0 || domainWordToCreate.word == null){
			message.addErrorMessage("All parameters must be provided.");
			return null;
		}
		
		if(GameModel.ValidateGameById(domainWordToCreate.gameId) == false){
			message.addErrorMessage("The Game Id provided is an invalid game id.");
			return null;
		}

		if(domainWordToCreate.playerId != GameModel.GetGameById(message, domainWordToCreate.gameId).player1Id && domainWordToCreate.playerId != GameModel.GetGameById(message, domainWordToCreate.gameId).player2Id){
			message.addErrorMessage("The player provided is not valid for the game.");
			return null;
		}

		if(GameModel.GetGameById(message, domainWordToCreate.gameId).isStatusComplete == true){
			message.addErrorMessage("This game is already completed.");
			return null;
		}
		
		if(ValidateWord(message, domainWordToCreate.word) == false){
			return null;
		}

		if(BoardModel.ValidateWordForBoard(message, domainWordToCreate.gameId, domainWordToCreate.word) == false){
			return null;
		}

		WordDataObject newWord = new WordDataObject(0, domainWordToCreate.gameId, domainWordToCreate.playerId, domainWordToCreate.word, true, ScoreWord(domainWordToCreate.word));
		WordDataAccess.createWord(newWord);
		return domainWordToCreate;
	}

	public static boolean ValidateWord(Message message, String word) {
		if(Pattern.matches("[a-zA-Z]{4,}", word) == false){
			message.addErrorMessage("The word is not valid.  It must be at least 4 characters long and include only letters.");
			return false;
		}

		if(DictionaryDataAccess.validateWordInDictionary(word) == false){
			//message.addErrorMessage("The word is not found in the dictionary.");
			
			message.addErrorMessage("Not in dictionary.");
			return false;
		}
		return true;		
	}

	public static int ScoreWord(String word){
		//WordDomainObject classicWord = new WordDomainObject(1, 1, word);
		//WordDomainObject BlitzWord = new WordDomainObject(2, 1, word);
		
		
		int size = word.length();
		int score;

		switch(size){
			case 4:
				score = 1;
				break;
			case 5:
				score = 2;
				break;
			case 6:
				score = 3;
				break;
			case 7:
				score = 5;
				break;
			default:
				score = 11;
		}

		return score;
	}
}
