package xmlUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.util.Pair;
import utils.XmlParser;

public class XmlToExcel implements ConvertFromXml {
	String xmlFile = null; // Name of the XML file

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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write() {
		// TODO Auto-generated method stub

	}

}
