package txtUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TxtManager {
	private File file; // holds the path to the file that is being operated on
	private int lineNumber = 0; // Number of lines a file has
	private ArrayList<TxtReader> contents; // contains the tag and its attributes

	public TxtManager(String file) {
		this.file = new File(file);
		contents = new ArrayList<TxtReader>();
		lineNumber = lines();
		parseTxt();
	}

	// This function returns the number of lines a file has
	private int lines() {
		int lineNumber = 0;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream("Files\\Input\\" + file + ".txt");
		} catch (FileNotFoundException e) {
			System.out.println("Error while opening file " + e.getMessage());
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

	private void parseTxt() {
		for (int line = 0; line < lineNumber; line++) {
			TxtReader fileParser = new TxtReader(file.getName());
			fileParser.readLine(line);
			contents.add(fileParser);
		}

	}

	public void print() {
		for (TxtReader x : contents) {
			System.out.print(x.getTag() + ": ");
			for (String a : x.getAttributeList()) {
				System.out.print(a + " ");
			}
			System.out.println("");
		}
	}
}
