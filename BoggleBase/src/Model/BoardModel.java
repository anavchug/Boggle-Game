package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import DataAccess.BoardDataAccess;
import DataAccess.LetterCubeDataAccess;
import DataObject.BoardDataObject;
import DataObject.LetterCubeDataObject;
import DomainObject.BoardDomainObject;
import restService.Message;




public class BoardModel {
	
	public static BoardDomainObject GetBoardDetailsById(Message message, int boardId) {
		BoardDataObject boardData = BoardDataAccess.getBoardById(boardId);
		BoardDomainObject boardDomain = new BoardDomainObject(boardData);

		return boardDomain;
	}

	public static BoardDomainObject GetBoardDetailsByGameId(Message message, int gameId) {
		BoardDataObject boardData = BoardDataAccess.getBoardByGameId(gameId);
		BoardDomainObject boardDomain = new BoardDomainObject(boardData);

		return boardDomain;
	}

	public static BoardDomainObject CreateBoard(Message message, int gameId, int gameTypeId) {
		//This needs to be implemented.
		
		//getting letter cubes from the LetterCubeDataAccess
		ArrayList<LetterCubeDataObject> letterCubes = LetterCubeDataAccess.getAllLetterCubesForGameType(gameTypeId);
		
		//Randomize the letter cubes 
		LinkedList<LetterCubeDataObject> permutation = new LinkedList<LetterCubeDataObject>();
    
        for(int i = 0; i<letterCubes.size(); i++){
			//generates a random index
            int index = (int)Math.floor(Math.random() * (permutation.size() + 1));
			
			//Add the ith letter cube at this randomly generated index
            permutation.add(index, letterCubes.get(i));
        }
		String[] randomSides = new String[16];
		for (int i = 0; i < permutation.size(); i++) {
			LetterCubeDataObject cube = permutation.get(i);
			int sideIndex = (int) Math.floor(Math.random() * 6); // generates a random index between 0 and 5
			String randomSide = "";
		
			switch (sideIndex) {
				case 0:
					randomSide = cube.side1;
					break;
				case 1:
					randomSide = cube.side2;
					break;
				case 2:
					randomSide = cube.side3;
					break;
				case 3:
					randomSide = cube.side4;
					break;
				case 4:
					randomSide = cube.side5;
					break;
				case 5:
					randomSide = cube.side6;
					break;
			}
			randomSides[i] = randomSide;
		}	
		BoardDataObject dataBoardToCreate = new BoardDataObject(1, gameId, randomSides[0], randomSides[1], randomSides[2], randomSides[3], 
		randomSides[4], randomSides[5], randomSides[6], randomSides[7], 
		randomSides[8], randomSides[9], randomSides[10], randomSides[11], 
		randomSides[12], randomSides[13], randomSides[14], randomSides[15]);
			BoardDataObject  dataBoardCreated = BoardDataAccess.createBoard(dataBoardToCreate);
			BoardDomainObject domainBoardCreated = new BoardDomainObject(dataBoardCreated);
			return domainBoardCreated;
	}

	public static Boolean ValidateWordForBoard(Message message, int gameId, String word) {
		
		//This needs to be implemented.
		return true;

	}

	

}
