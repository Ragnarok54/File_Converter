package xmlUtils;

import java.io.File;
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
import javafx.util.Pair;

public class XmlToExcel implements ConvertFromXml {
	// Name of the XML file
	String xmlFile = null; 
	// Reference the main class to update the progress of the conversion
	private static main main = new main();
	// Logger
	static Logger log = Logger.getLogger(main.class.getName());

	
	protected XmlToExcel(String fileToBeConverted) {
		xmlFile = new String(fileToBeConverted);
		convert();
	}

	@Override
	// Creates the structure of the Excel file based on the xml file from the Input folder
	public void convert() {
		XmlParser xmlParser = new XmlParser(xmlFile);

		XSSFWorkbook workbook = new XSSFWorkbook();

		int attributeIterator = 0;
		for (Pair<String, Integer> node : xmlParser.getFileName()) {
			// Create a new sheet corresponding to the current node
			XSSFSheet sheet = workbook.createSheet(node.getKey());
			
			// Set the progress bar value
			main.setProgress((int) ((float)(xmlParser.getFileName().indexOf(node) + 1) / (float)(xmlParser.getFileName().size() + 1) * 100));
			
			// Matrix of all attributes in a node
			ArrayList<ArrayList<String>> dataTypes = new ArrayList<ArrayList<String>>();
			// List of attributes in a line
			ArrayList<String> dataType = null;

			// For each line in current node
			for (int line = 0; line < node.getValue(); line++) {
				dataType = new ArrayList<String>();
				// Add the tag of the current line
				dataType.add(xmlParser.getAttributeList().get(line + attributeIterator).getTag());

				// For each attribute in line
				for (String atb : xmlParser.getAttributeList().get(line + attributeIterator).getAttributes()) {
					// Add the attributes of the current line
					dataType.add(atb);
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

			// Go the the next node
			attributeIterator += node.getValue();
		}

		// Close the excel file
		try {
			// The name of the output file is the name of the xml file and the path is to the Output folder
			File outputFile = new File(Paths.get("").toAbsolutePath().toString() + "\\Files\\Output\\" + xmlFile + ".xlsx");
			
			// If the name already exists in the Output folder, add (1) to the name
			if (outputFile.exists())
				outputFile = new File(Paths.get("").toAbsolutePath().toString() + "\\Files\\Output\\" + xmlFile + "(1).xlsx");
			
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		} catch (FileNotFoundException e) {
			log.error("File not found" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("I/O error" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void write() {
		// TODO Auto-generated method stub

	}

}
