
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * ImageBlur receives a user prompted file of .pgm format, and blurs all the image 
 *using nearby pixels, and then saves this file to a new .pgm
 *
 * @author Caleb Ice 
 */

public class ImageBlur {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		
		Scanner keyboard = new Scanner(System.in);
		System.out.print("What is the file name?: ");
		String myFile = keyboard.next();
		System.out.println();
		System.out.print("How many blur iterations?: ");
		int numBlur = keyboard.nextInt();
		System.out.println();
		
		
		String myOutFile = myFile.substring(0, myFile.length() - 4) + "-"
				+ "blur-" + numBlur + ".pgm";

		double[][] mySource = null;
		double[][] myDest = null;
		int row = 0;
		int col = 0;
		File fileName = new File(myFile);
		try {
			Scanner sc = new Scanner(fileName);

			while (sc.hasNext()) { // Reading in the given file, filling array with values
				if (sc.nextLine().equals("P2")) {
					col = sc.nextInt();
					row = sc.nextInt();
					mySource = new double[row][col];
					myDest = new double[row][col];
					for (int j = 0; j < row; j++) {
						for (int i = 0; i < col; i++) {
							mySource[j][i] = sc.nextInt();
						}
					}
				}
			}
			sc.close(); // finish reading
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		blur(mySource, myDest, numBlur);

		if (numBlur % 2 == 0) {
			writeImage(mySource, myOutFile);
		} else
			writeImage(myDest, myOutFile);

	}
	/**   
	 * Performs a @numBlur amount of blurs on the received 2d array
	 * 
	 * @param source creates a new array after successful blur operations -used on even increments
	 * @param dest creates a new array after successful blur operations -used on odd increments
	 * @param numBlur dictates the number of blur operations
	*/
	private static void blur(double[][] source, double[][] dest, int numBlur) {

		for (int b = 0; b < numBlur; b++) { 	//This for loop decides which array is blurred
			if (b % 2 == 0) {
				for (int r = 0; r < source.length; r++) {
					for (int c = 0; c < source[0].length; c++) {
						dest[r][c] = sum3x3(source, r, c);
					}
				}
			} else {
				for (int r = 0; r < dest.length; r++) {
					for (int c = 0; c < dest[0].length; c++)
						source[r][c] = sum3x3(dest, r, c);
				}
			}
		}

	}

	
	/** 
	 * Writes a new .pgm file using the received array img
	 * 
	 * @param img provides the writing file with information to fill the new .pgm
	 * @param fName provides the name for the output file
	 * */
	private static void writeImage(double[][] img, String fName)
			throws IOException {
		PrintWriter myOutputFile = new PrintWriter(fName);
		myOutputFile.println("P2");								//Sets up file header
		myOutputFile.println(img[0].length + " " + img.length); //
		myOutputFile.println(255);								//
		for (int r = 0; r < img.length; r++) {
			for (int c = 0; c < img[0].length; c++) {
				myOutputFile.print(Math.round(img[r][c]) + " ");
			}
			myOutputFile.println();
		}
		myOutputFile.close();
	}

	/** 
	 * Averages a 3x3 square around a given coordinate point
	 * 
	 * @param img provides the total array information for summing
	 * @param row provides the target row number
	 * @param col provides the target column number
	 * 
	 * @return average of the surrounding pixels given the target location
	 * */
	private static double sum3x3(double[][] img, int row, int col) {
		double mySum = 0;
		int rowLength = img.length;
		int colLength = img[0].length;
		int addLeftR = (row + rowLength - 1) % rowLength;
		int addRightR = (row + 1) % rowLength;
		int addDownC = (col + 1) % colLength;
		int addUpC = (col + colLength - 1) % colLength; // The shifting
														// operations to include
														// wrapping
		
		mySum += img[addLeftR][addUpC] + img[row][addUpC]
				+ img[addRightR][addUpC] + img[addLeftR][col] + img[row][col]
				+ img[addRightR][col] + img[addLeftR][addDownC]
				+ img[row][addDownC] + img[addRightR][addDownC]; // This block
																	// sums up
																	// the 3x3
																	// block 
		return mySum/9; // Allows user to return average of the sum
	}

}
