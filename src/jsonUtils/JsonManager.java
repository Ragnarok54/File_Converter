package jsonUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import utils.FileParser;
import utils.line;

public class JsonManager {
	private JsonArray jsonFile = null; // holds the name of the file to be created
	private String filePath = null;

	public JsonManager(String fileName) {
		filePath = Paths.get("").toAbsolutePath().toString() + "\\Files\\Output\\" + fileName + ".json";
		createStructure();
		write();
	}

	private void createStructure() {
		FileParser fileParser = new FileParser();
		int fileNumber = fileParser.getFileNumber();

		jsonFile = new JsonArray();

		// For each txt file
		for (int fileIterator = 0; fileIterator < fileNumber; fileIterator++) {
			// Create a contents tag
			JsonObject contents = new JsonObject();
			
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
				
				// Add the tag and the attributes as a "sub-class" for the contents of the file
				System.out.println("Added " + fileLine.getTag() + ": " + attributes);

			}
			contents.put(fileParser.getFileName(), atb);
			// Add the contents of the file to the json
			jsonFile.add(contents);
			
			// Go the the next file
			fileParser.next();
		}
	}

	private void write() {
		try {
			FileWriter file = new FileWriter(filePath);
			file.write(jsonFile.toJson());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
