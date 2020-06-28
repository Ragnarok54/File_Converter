package jsonUtils;

import java.io.File;
import java.io.IOException;

public class JsonManager {
	TxtToJson convertToJson = null;
	JsonToTxt convertToTxt = null;
	
	public JsonManager(){
		
	}

	public void convertToJson(String fileToBeCreated){
		File tempFile = null;
		try {
			tempFile = File.createTempFile(fileToBeCreated, ".txt");
			tempFile.exists();
		}catch(IOException e){
			System.out.println("Txt file does not exist");
			e.printStackTrace();
		}finally {
			convertToJson = new TxtToJson(fileToBeCreated);
			System.out.println("Converted all txt files to " + fileToBeCreated + ".json");
		}
	}
	
	public void convertToTxt(String fileToBeRead) {
		//convertToTxt = new JsonToTxt();
	}
}
