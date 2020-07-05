package txtUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import utils.line;

/* Class that parses all txt files in the input directory
 * and provides the elements for each one based on an
 * iterator template
 */
public class TxtParser {
	private String AbsPath = null; // Absolute path to program folder
	private String input = "\\Files\\Input"; // Path from program folder to Input files
	private int fileNumber = 0; // Number of files in the input folder
	private ArrayList<TxtManager> txtFiles = null; // Holds the contents of each file
	private int fileIterator = 0; // Iterator in order to implement the iterator pattern

	// Constructor that also parses the input files
	public TxtParser() {
		AbsPath = Paths.get("").toAbsolutePath().toString();
		txtFiles = new ArrayList<TxtManager>();
		this.parse();
	}

	// Used for parsing
	private void parse() {
		File dir = new File(AbsPath + input);
		File[] directoryListing = dir.listFiles();
		for (File childFile : directoryListing) {
			// Only select txt files in the input folder
			if (childFile.getName().endsWith(".txt")) {
				TxtManager parsedFile = new TxtManager(childFile.getName());
				fileNumber++;
				txtFiles.add(parsedFile);
			}
		}
	}

	public int getFileNumber() {
		return fileNumber;
	}

	public boolean next() {
		fileIterator++;
		if (fileIterator < txtFiles.size())
			return true;
		return false;
	}

	public String getFileName() {
		return txtFiles.get(fileIterator).getFileName();
	}

	public ArrayList<line> getAttributes() {
		return txtFiles.get(fileIterator).getFileContents();
	}

}
