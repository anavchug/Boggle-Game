package DataAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryDataAccess {

	private static ArrayList<String> words;

	public DictionaryDataAccess() {
		initialize();
	}

	private void initialize() {
		words = new ArrayList<String>();

		//Add code here to read in the dictionary file.
		/* 
		Path path = Paths.get("dictionary.txt");
        File file = path.toFile();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                words.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
		*/
    }
 
	public static boolean validateWordInDictionary(String word) {
		//This needs to be implemented.
		/* 
			if(words.contains(word)){
				return true;
			}
		return false;
		*/
	}

}