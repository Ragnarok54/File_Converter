package txtUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/* This class reads a single line from a .txt file with the format
 * tag: attribute1, attribute2, attribute3
 * and saves them accordingly
 */
public class TxtReader {
	private File file = null; // holds the file that is being operated on
	private String tag = null; // holds the tag for the line
	private ArrayList<String> attributes; // list of attributes for the line

	public TxtReader(String path) {
		this.file = new File(path);
		attributes = new ArrayList<String>();
	}

	/*
	 * This function is used to store into tag and attributes the given values for
	 * the line. It reads only one line which is given as a parameter. The lines are
	 * zero-indexed
	 */
	public void readLine(int lineNumber) {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(
					Paths.get("").toAbsolutePath().toString() + "\\Files\\Input\\" + file + ".txt");
			Scanner fileScanner = new Scanner(inputStream); // Scanner for the file to be read
			Scanner lineScanner = null; // Used to only read one line and not read from the next one

			// Advance the line to the required one
			while (lineNumber != -1) {
				lineScanner = new Scanner(fileScanner.nextLine());
				lineNumber--;
			}
			lineScanner.useDelimiter(": |, |,"); // Set delimiters for the txt files
			tag = lineScanner.next(); // Retrieve the tag for the line

			// For each attribute, save it into the array
			while (lineScanner.hasNext()) {
				String currentAttribute = lineScanner.next();
				attributes.add(currentAttribute);
			}
			
			lineScanner.close();
			fileScanner.close();
		} catch (FileNotFoundException e) {

			System.out.println("Error while opening file " + e.getMessage());
			e.printStackTrace();
		} catch (NoSuchElementException e) {

			System.out.println("Error no such element " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("Error while processing file " + e.getMessage());
			e.printStackTrace();
		}

		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				System.out.println("File is not open " + e.getMessage());
			}
		}

	}

	public String getTag() {
		return this.tag;
	}

	public ArrayList<String> getAttributeList() {
		return this.attributes;
	}
}
