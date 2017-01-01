import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Creates an object of dictionary, that reads in an array of words
 * sorted in dictionary format. Opted to use TreeSets due to the
 * aesthetic of that toString method
 * @author Caleb Ice
 *
 */
public class EvilDictionary {
	/**FIELDS */
	String fileName;
	private TreeSet<String> hSet;
	private static HashMap<Integer, TreeSet<String>> myWords;

	/**
	 * Creates the EvilDictionary object and fills it with words from a dictionary
	 * 		textfile (reads through the dictionary file
	 * @param filename 
	 */
	public EvilDictionary(String filename){
		myWords = new HashMap<Integer, TreeSet<String>>();
		this.fileName = filename;
		String line = null;
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = 
					new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = 
					new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {

				if(myWords.containsKey(line.length())){
					hSet = myWords.get(line.length());
				}
				else{
					hSet = new TreeSet<String>();
				}
				hSet.add(line);
				myWords.put(line.length(), hSet);
			}   
			// Always close files.
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println(
					"Unable to open file '" + 
							fileName + "'");                
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '" 
							+ fileName + "'");   
		}
	}

	public TreeSet<String> getWordSet(int length){
		if(!myWords.containsKey(length))
			return null;
		else
			return myWords.get(length);
	}
}

