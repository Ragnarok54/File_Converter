package xmlUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import application.main;
import javafx.util.Pair;
import utils.line;

public class XmlParser {
	// Absolute path to the folder location
	String AbsPath = null;
	// Relative path from the folder to the file that will be read
	String FilePath = null;
	// List with each line (contains tag and list of attributes)
	ArrayList<line> attributeList = null;
	// List with each file name and how many lines it has
	ArrayList<Pair<String, Integer>> fileTag = null;
	// Logger
	static Logger log = Logger.getLogger(main.class.getName());

	// Constructor
	public XmlParser(String fileName) {
		AbsPath = Paths.get("").toAbsolutePath().toString();
		if (fileName.contains(".xml"))
			FilePath = "\\Files\\Input\\" + fileName;
		else
			FilePath = "\\Files\\Input\\" + fileName + ".xml";
		attributeList = new ArrayList<line>();
		fileTag = new ArrayList<Pair<String, Integer>>();
		parseFile();
	}

	// Parser utility
	private void parseFile() {
		// File inputFile = new File(AbsPath + FilePath);
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(AbsPath + FilePath);
			Scanner fileScanner = new Scanner(inputStream); // Scanner for the file to be read
			Scanner lineScanner = null; // Used to only read one line and not read from the next one
			lineScanner = new Scanner(fileScanner.nextLine()); // skip first line with utc encoding

			// Save the number of lines on each file
			int linesPerFile = 0;
			
			// For each line in the XML
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();

				line = line.trim();

				if (line.charAt(1) == '/') {
					 // Finished a file, move to the next one
					fileTag.add(new Pair<String, Integer>(line.substring(2, line.length() - 1), linesPerFile));
					// Reset the lines per file
					linesPerFile = 0; 
				} else {
					if (line.indexOf('>') == line.lastIndexOf('>')) {
						// this is a file start tag
					} else {
						linesPerFile++;
						// Remove the first char
						line = line.replaceFirst("<", "");

						// Split the string by delimiters
						String[] attributes = line.split(">|</|, |,");

						// Create new attribute list for the line
						line attributeLine = new line();
						
						// The first item on the line is the tag
						attributeLine.setTag(attributes[0]);

						for (String atb : attributes) {
							// Only add the attributes, skip the tag
							if (!atb.equals(attributeLine.getTag())) {
								attributeLine.add(atb);
							}
						}
						// Add the newly created line with the attributes
						attributeList.add(attributeLine);
					}
				}
			}
			// The parsers add the file name with size 0 as last item in fileTag array
			fileTag.remove(fileTag.size() - 1);

			// Close the scanners
			lineScanner.close();
			fileScanner.close();
		} catch (FileNotFoundException e) {
			log.error("Error while opening file" + e.getMessage());
			//System.out.println("Error while opening file " + e.getMessage());
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			log.error("No such element" + e.getMessage());
			//System.out.println("Error no such element " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Error while processing file" + e.getMessage());
			//System.out.println("Error while processing file " + e.getMessage());
			e.printStackTrace();
		}

		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				log.error("File is not open" + e.getMessage());
				System.out.println("File is not open " + e.getMessage());
			}
		}

	}

	public ArrayList<Pair<String, Integer>> getFileName() {
		return fileTag;
	}

	public ArrayList<line> getAttributeList() {
		return attributeList;
	}

}
