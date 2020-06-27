package txtUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/* This class reads a single line from a txt file with the format
 * tag: attribute1, attribute2, attribute3
 * and saves them accordingly
 */
public class TxtReader {
	private File file; // holds the file that is being operated on
	private String tag; // holds the tag for the line
	private ArrayList<String> attributes; // list of attributes for the line

	public TxtReader(String path) {
		this.file = new File(path);
		attributes = new ArrayList<String>();
	}

	private void readLine() {
		try {
			Scanner sc = new Scanner("Files//Input//" + file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
