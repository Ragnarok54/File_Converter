package txtUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import application.main;
import utils.line;

/* This class reads a single line from a .txt file with the format
 * tag: attribute1, attribute2, attribute3
 * and saves them accordingly
 */
public class TxtReader {
	private File file = null; // holds the file that is being operated on
	// private String tag = null; // holds the tag for the line
	// private ArrayList<String> attributes; // list of attributes for the line
	private line fileLine = new line();
	// Logger
	static Logger log = Logger.getLogger(main.class.getName());

	protected TxtReader(String path) {
		this.file = new File(path);
		// attributes = new ArrayList<String>();

	}

	/*
	 * This function is used to store into tag and attributes the given values for
	 * the line. It reads only one line which is given as a parameter. The lines are
	 * zero-indexed
	 */
	protected void readLine(int lineNumber) {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(Paths.get("").toAbsolutePath().toString() + "\\Files\\Input\\" + file);
			Scanner fileScanner = new Scanner(inputStream); // Scanner for the file to be read
			Scanner lineScanner = null; // Used to only read one line and not read from the next one

			// Advance the line to the required one
			while (lineNumber != -1) {
				lineScanner = new Scanner(fileScanner.nextLine());
				lineNumber--;
			}
			lineScanner.useDelimiter(": |, |,"); // Set delimiters for the txt files
			fileLine.setTag(lineScanner.next()); // Retrieve the tag for the line

			// For each attribute, save it into the array
			while (lineScanner.hasNext()) {
				String currentAttribute = lineScanner.next();
				fileLine.add(currentAttribute);
			}

			lineScanner.close();
			fileScanner.close();
		} catch (FileNotFoundException e) {
			
			log.error("Error while opening file " + e.getMessage());
			//System.out.println("Error while opening file " + e.getMessage());
			e.printStackTrace();
		} catch (NoSuchElementException e) {

			log.error("No such element " + e.getMessage());
			//System.out.println("Error no such element " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {

			log.error("Error while processing file " + e.getMessage());
			//System.out.println("Error while processing file " + e.getMessage());
			e.printStackTrace();
		}

		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				log.error("Tried to close unexistent file");
				//System.out.println("File is not open " + e.getMessage());
			}
		}

	}

	protected line getLine() {
		return this.fileLine;
	}

	protected String getTag() {
		return fileLine.getTag();
	}

	protected ArrayList<String> getAttributeList() {
		return fileLine.getAttributes();
	}
}
