import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Creates an EvilHangman object, that imports a dictionary
 * sets the value of guesses;
 *  
 * @author Caleb Ice
 *
 */
public class EvilHangman {
	public EvilDictionary dict;
	public int guesses;
	private ArrayList<Character> usedLetters;
	public TreeSet<String> myWords;
	public boolean showWords;

	/**
	 * Instantiates the EvilHangman object
	 * @param numGuess
	 */
	public EvilHangman(int numGuess){
		this.guesses = numGuess;
		this.dict = new EvilDictionary("dictionary.txt");
		this.usedLetters = new ArrayList<Character>();
		this.showWords = false;
	}

	/**
	 * Creates a HashMap of words with keys 
	 * @param wordSet
	 * @param dashes
	 * @param letter
	 * @return the created HashMap
	 */
	public HashMap<String, TreeSet<String>> availableWords(TreeSet<String> wordSet, String dashes, char letter){
		HashMap<String, TreeSet<String>> wordList = new HashMap<String, TreeSet<String>>();
		Iterator<String> iter = wordSet.iterator();
		while(iter.hasNext()){
			String addWord = iter.next();
			String key = buildKey(dashes, addWord, letter);
			if(wordList.containsKey(key)){
				wordSet = wordList.get(key);
			}
			else{
				wordSet = new TreeSet<String>();
			}
			wordSet.add(addWord);
			wordList.put(key, wordSet);
		}

		return wordList;
	}

	/**
	 * Builds a key that can be used for the map building
	 * @param dashes the current dashes 
	 * @param target
	 * @param c the target charcter (letter being used)
	 * @return the fully built key
	 */
	public String buildKey(String dashes, String target, char c){
		StringBuilder key = new StringBuilder();
		for(int i = 0; i<dashes.length(); i++){
			if(target.charAt(i)==c){
				key.append(c);
			}
			else{
				key.append(dashes.charAt(i));
			}
		}
		return key.toString();
	}

	/**
	 * Finds the key that yields the biggest sized TreeSet
	 * @param myMap
	 * @return the key to the biggest Set
	 */
	public String biggestSet(HashMap<String, TreeSet<String>> myMap){
		String testKey = "";
		String biggestKey = "";
		Iterator<Entry<String, TreeSet<String>>>
		iter= myMap.entrySet().iterator();

		TreeSet<String> largestFamily = new TreeSet<String>();
		TreeSet<String> test;

		while(iter.hasNext()){
			testKey = iter.next().getKey();
			test = myMap.get(testKey);
			if(test.size()>largestFamily.size()){
				largestFamily = test;
				biggestKey = testKey;
			}
		}
		return biggestKey;
	}

	/**
	 * checks to see if the targeted letter has already been used
	 * @param c 
	 * @return true if the letter has not been used
	 */
	public boolean updateLetterList(char c){
		if(!usedLetters.contains(c)){
			usedLetters.add(c);
			return true;
		}
		return false;
	}

	public String getLettersUsed(){
		return usedLetters.toString();
	}
}
