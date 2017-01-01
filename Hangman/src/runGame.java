import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.TreeSet;
import java.util.Scanner;

/**
 * Creates and runs a game of hangman that 
 * @author CalebIce
 *
 */
public class runGame {
	//Allows the Scanner to be accessed across the methods
	public static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) {
		boolean replay = true; //Allows the condition for replaying

		System.out.println("Thanks for playing Evil Hangman!\n");
		while(replay){
			gameState();
			System.out.print("Would you like to play again? (Y/N): ");
			replay = checkYesNo();
		}
		System.out.println("Thank you for playing!");
	}

	/**
	 * Runs the logic of the game within it
	 * Receives all of the information about the game to play
	 * Runs the actual bulk of the code
	 */
	public static void gameState(){
		/*THE FIELDS THAT ESTABLISH THE GAME*/
		EvilHangman myGame;
		int letters, numGuess;
		char guess;
		boolean rightWord = false;
		String dashes, biggestKey;
		TreeSet<String> gameSet;
		HashMap<String, TreeSet<String>> wordMap;
		//end fields

		//Registers how many guesses
		System.out.print("How many guesses would you like?: ");
		numGuess = properNumber();
		System.out.println(numGuess+" guesses? You might want more...\n");
		myGame = new EvilHangman(numGuess);

		//Checks to see if user wants words list
		System.out.print("Would you like to see how many words I've got in mind? (Y/N): ");
		myGame.showWords=checkYesNo();
		if(myGame.showWords){
			System.out.println("You're a cheater, but I guess so am I.\n");
		}
		else
			System.out.println("Great! Make it easy for me.\n");

		//Finds out how many letters are desired
		System.out.print("How many letters would you like to play with?: ");
		letters = properNumber();
		keyboard.nextLine();

		//Checks to make sure the values exist
		while(myGame.dict.getWordSet(letters)==null){
			System.out.println("There are literally no English words with that length");
			System.out.print("How many letters would you like to play with?: ");
			letters = keyboard.nextInt();
		}
		System.out.println(letters+" letters? Good luck!\n");


		//Builds dictionary, EvilHangman state and then builds initial key
		gameSet = myGame.dict.getWordSet(letters);
		dashes = startKey(letters);

		while(myGame.guesses>0&&!rightWord){
			if(myGame.showWords)
				System.out.println("My list has "+gameSet.size()+" words in it cheater.");
			System.out.println(dashes);
			System.out.println(myGame.getLettersUsed());
			System.out.println("You have "+myGame.guesses+" guesses left.");
			guess = getLetter(myGame);

			//Makes a map of the word sets available based on the guess letter
			wordMap = myGame.availableWords(gameSet, dashes, guess);
			biggestKey = myGame.biggestSet(wordMap);
			gameSet = wordMap.get(biggestKey);

			//Runs a check to see if any letters have changed
			if(dashes.equals(biggestKey)){
				myGame.guesses--;
				System.out.println("That's not on my board!\n");
			}
			else
				System.out.println("Thats on the board!\n");

			dashes = biggestKey;
			rightWord = wonGame(gameSet, dashes);
		}

		if(rightWord){
			System.out.println(dashes+" is the word I was thinking all along!");
			System.out.println("You won! I'm legitimately surprised.\n");
		}
		else{
			System.out.println(gameSet.pollLast()+" is the word I was thinking all along!");
			System.out.println("You Lost! I expected that though\n");
		}
	}

	/**
	 * Instantiates the start key to the key length
	 * @param length
	 * @return a builts String of "_" of passed length
	 */
	public static String startKey(int length){
		StringBuilder s = new StringBuilder();
		for(int i = 0; i<length; i++)
			s.append("_");
		return s.toString();
	}

	/**
	 * Prompts the user for a letter ensures that the letter is valid
	 * @param thisGame
	 * @return the desired target letter
	 */
	public static char getLetter(EvilHangman myGame){
		String s;
		System.out.print("Please enter a letter: ");
		s = keyboard.nextLine().toLowerCase();

		while(s.length()==0||s.charAt(0)<'a'||s.charAt(0)>'z'){
			System.out.println("What kind of letter is that I've never seen it?");
			System.out.print("Please enter an actual letter: ");
			s = keyboard.nextLine();
		}
		if(myGame.updateLetterList(s.charAt(0)))
			return s.charAt(0);
		else{
			System.out.println("Your letters are getting redundant..");
			return getLetter(myGame);
		}
	}

	/**
	 * Checks to see if the player has won the game
	 * @param hSet the wordlist being used
	 * @param dashes the word checking
	 * @return whether or not the game is won
	 */
	public static boolean wonGame(TreeSet<String> hSet, String dashes){
		if(hSet.contains(dashes))
			return true;
		else
			return false;
	}

	/**
	 * Provides a way of making sure the player answers yes or no
	 * @return true if 'y' is given, false if 'n'
	 */
	public static boolean checkYesNo(){
		String s;
		char c;
		s = keyboard.nextLine();
		//Forces there to be a proper length of the read line
		if(s.length()>0)
			c = s.toLowerCase().charAt(0);
		else
			return checkYesNo();

		while(c!='n'&&c!='y'){
			System.out.println(c);
			System.out.println("Why is Y/N so hard for you? (Enter Y/N): ");
			return checkYesNo();
		}
		return c=='y';
	}

	/**
	 * Finds a proper number, and handles the exception that can be thrown
	 * @return a valid number
	 */
	public static int properNumber(){
		int i = 0;
		try{
			i = keyboard.nextInt();
		}
		catch(InputMismatchException error){
			System.out.print("That is not a number braniac! Please try again: ");
			keyboard.nextLine();
			System.out.print("Please enter a real number: ");
			i = properNumber();
		}
		return i;
	}
}


