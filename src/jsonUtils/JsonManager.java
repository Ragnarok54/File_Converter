package jsonUtils;

import org.apache.log4j.Logger;

import application.main;

/*
 * User Interface class to handle txt to JSON conversion and
 * also supports the addition of JSON to txt and JSON to other 
 * formats
 */
public class JsonManager {
	TxtToJson convertToJson = null;
	JsonToTxt convertToTxt = null;

	// Logger
	static Logger log = Logger.getLogger(main.class.getName());
	
	// Constructor
	public JsonManager() {

	}

	// Converts all txt files to a single JSON file
	public void convertToJson(String fileToBeCreated) {
		// Use the TxtToJson class to handle the conversion and file creation
		convertToJson = new TxtToJson(fileToBeCreated);
		
		log.info("Converted all txt files to json");
		//System.out.println("Converted all txt files to " + fileToBeCreated + ".json");
	}

	// Convert a single JSON file to multiple txt files
	public void convertToTxt(String fileToBeRead) {
		// convertToTxt = new JsonToTxt();
	}
}
