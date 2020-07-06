package txtUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import application.main;
import utils.line;

/*
 * Class to parse a single file and stores
 * all the tags and attributes
 */
public class TxtManager {
	// Holds the path to the file that is being operated on
	private File file; 
	// Number of lines a file has
	private int lineNumber = 0; 
	// Contains the tag and its attributes
	private ArrayList<line> contents; 
	// Logger
	static Logger log = Logger.getLogger(main.class.getName());

	// Constructor that handles the parsing of each line
	protected TxtManager(String file) {
		this.file = new File(file);
		contents = new ArrayList<line>();
		lineNumber = lines();
		parseTxt();
	}

	// This function returns the number of lines a file has
	private int lines() {
		int lineNumber = 0;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream("Files\\Input\\" + file);
		} catch (FileNotFoundException e) {
			log.error("Error while opening file " + e.getMessage());
			//System.out.println("Error while opening file " + e.getMessage());
			e.printStackTrace();
		}

		Scanner fileScanner = new Scanner(inputStream);

		while (fileScanner.hasNextLine()) {
			lineNumber++;
			fileScanner.nextLine();
		}

		fileScanner.close();
		return lineNumber;
	}

	// Makes use of TxtReader to handle line reading
	private void parseTxt() {
		for (int line = 0; line < lineNumber; line++) {
			TxtReader fileParser = new TxtReader(file.getName());
			fileParser.readLine(line);
			contents.add(fileParser.getLine());
		}

	}

	// Returns the number of lines in the file
	public int getLines() {
		return this.lineNumber;
	}

	// Returns the file name
	public String getFileName() {
		return this.file.getName();
	}

	// Returns an array with the contents of each line
	public ArrayList<line> getFileContents() {
		return this.contents;
	}

	// Used for debugging purposes
	public void print() {
		for (line x : contents) {
			System.out.print(x.getTag() + ": ");
			for (String a : x.getAttributes()) {
				System.out.print(a + " ");
			}
			System.out.println("");
		}
	}
}