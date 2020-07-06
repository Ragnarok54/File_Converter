package excelUtils;

import org.apache.log4j.Logger;

import application.main;

/*
 * User Interface class to handle text to Excel conversion and
 * also supports the addition of Excel to text and Excel to other 
 * formats
 */
public class ExcelManager {
	TxtToExcel convertToExcel = null;
	ExcelToTxt convertToTxt = null;
	// Logger
	static Logger log = Logger.getLogger(main.class.getName());
	
	public ExcelManager() {

	}

	// Converts all text files to a single Excel file
	public void convertToExcel(String fileToBeCreated) {
		// Use the TxtToExcel class to handle the conversion and file creation
		convertToExcel = new TxtToExcel(fileToBeCreated);
		
		log.info("Converted all txt files to excel");
		//System.out.println("Converted all txt files to " + fileToBeCreated + ".xlsx");
	}

	public void convertToTxt(String fileToBeRead) {
		// convertToTxt = new ExcelToTxt();
	}
}
