package jsonUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import application.main;
import txtUtils.TxtParser;
import utils.line;

public class TxtToJson {
	// Holds the name of the file to be created
	private JsonArray jsonFile = null;
	// Holds the path from program folder to the file to be created
	private String filePath = null;
	// Reference the main class to update the progress of the conversion
	private static main main = new main();
	// Logger
	static Logger log = Logger.getLogger(main.class.getName());

	// Constructor that also handles file creation
	protected TxtToJson(String fileName) {
		filePath = Paths.get("").toAbsolutePath().toString() + "\\Files\\Output\\" + fileName + ".json";
		createStructure();
		write();
	}

	/* Creates the structure of the JSON file based
	 * on the txt files from the Input folder
	 */
	private void createStructure() {
		TxtParser fileParser = new TxtParser();
		int fileNumber = fileParser.getFileNumber();

		jsonFile = new JsonArray();

		// For each txt file
		for (int fileIterator = 0; fileIterator < fileNumber; fileIterator++) {
			// Create a contents tag
			JsonObject contents = new JsonObject();
			
			// Set the progress bar value
			main.setProgress((int) ((float)(fileIterator + 1) / (float)(fileNumber + 1) * 100));
			
			// Create an attribute holder
			JsonObject atb = new JsonObject();
			
			// For each line in file
			for (line fileLine : fileParser.getAttributes()) {				
				// Sum up all the attributes
				String attributes = "";
				for (String attribute : fileLine.getAttributes()) {
					if (fileLine.getAttributes().get(fileLine.getAttributes().size() - 1) == attribute)
						attributes += attribute;
					else
						attributes += (attribute + ", ");
				}
				// Merge the tag with the attributes
				atb.put(fileLine.getTag(), attributes);
			}
			// Add the tag and the attributes as a "sub-class" for the contents of the file
			contents.put(fileParser.getFileName(), atb);
			// Add the contents of the file to the json
			jsonFile.add(contents);
			
			// Go the the next file
			fileParser.next();
		}
	}

	// Creates the JSON file base on the configuration in jsonFile
	private void write() {
		try {
			FileWriter file = new FileWriter(filePath);
			file.write(jsonFile.toJson());
			file.flush();
			file.close();
		} catch (IOException e) {
			log.error("I/O error when creating file");
			e.printStackTrace();
		}
	}
}
