package excelUtils;

/*
 * User Interface class to handle text to Excel conversion and
 * also supports the addition of Excel to text and Excel to other 
 * formats
 */
public class ExcelManager {
	TxtToExcel convertToExcel = null;
	ExcelToTxt convertToTxt = null;

	public ExcelManager() {

	}

	// Converts all text files to a single Excel file
	public void convertToExcel(String fileToBeCreated) {
		// Use the TxtToExcel class to handle the conversion and file creation
		convertToExcel = new TxtToExcel(fileToBeCreated);

		System.out.println("Converted all txt files to " + fileToBeCreated + ".xlsx");
	}

	public void convertToTxt(String fileToBeRead) {
		// convertToTxt = new ExcelToTxt();
	}
}
