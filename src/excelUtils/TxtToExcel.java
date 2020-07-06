package excelUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import application.main;
import txtUtils.TxtParser;
import utils.line;

public class TxtToExcel {
	// Path to the output file
	private String excelFile; 
	// Logging file
	static Logger log = Logger.getLogger(main.class.getName());

	// Constructor that also handles file creation
	public TxtToExcel(String fileName) {
		excelFile = Paths.get("").toAbsolutePath().toString() + "\\Files\\Output\\" + fileName + ".xlsx";
		convert();
	}

	/* Creates the structure of the Excel file based
	 * on the text files from the Input folder
	 */
	private void convert() {
		TxtParser fileParser = new TxtParser();
		int fileNumber = fileParser.getFileNumber();

		XSSFWorkbook workbook = new XSSFWorkbook();

		// For each text file
		for (int fileIterator = 0; fileIterator < fileNumber; fileIterator++) {
			// Get the name of the current file without ".txt"
			String[] fileName = fileParser.getFileName().split(".txt");
			// Create a new sheet corresponding to the current file
			XSSFSheet sheet = workbook.createSheet(fileName[0]);

			// Matrix of all attributes in a file
			ArrayList<ArrayList<String>> dataTypes = new ArrayList<ArrayList<String>>();
			// List of attributes in a line
			ArrayList<String> dataType = null;

			// For each line in file
			for (line fileLine : fileParser.getAttributes()) {
				dataType = new ArrayList<String>();
				// Add the tag of the current line
				dataType.add(fileLine.getTag());

				// For each attribute in line
				for (String attribute : fileLine.getAttributes()) {
					// Add the attributes of the current line
					dataType.add(attribute);
				}

				// Add the list of attributes of the current line to the matrix
				dataTypes.add(dataType);
			}

			// Set the cell style for the tags
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style.setFillPattern(FillPatternType.FINE_DOTS);
			style.setBorderTop(BorderStyle.MEDIUM);
			style.setBorderBottom(BorderStyle.MEDIUM);
			style.setBorderLeft(BorderStyle.MEDIUM);
			style.setBorderRight(BorderStyle.MEDIUM);

			int rowNum = 0;
			// Create the rows based on the data stored in the matrix
			for (ArrayList<String> datatype : dataTypes) {
				Row row = sheet.createRow(rowNum++);
				int colNum = 0;
				for (String field : datatype) {
					Cell cell = row.createCell(colNum++);
					if (colNum == 1)
						cell.setCellStyle(style);
					cell.setCellValue((String) field);
				}
			}

			// Go the the next text file
			fileParser.next();
		}

		// Close the excel file
		try {
			FileOutputStream outputStream = new FileOutputStream(excelFile);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		} catch (FileNotFoundException e) {
			log.error("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("I/O error");
			e.printStackTrace();
		}
	}
}
