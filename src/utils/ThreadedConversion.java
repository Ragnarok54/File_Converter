package utils;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import application.main;
import excelUtils.ExcelManager;
import jsonUtils.JsonManager;
import pdfUtils.PdfManager;
import xmlUtils.XmlManager;

public class ThreadedConversion extends SwingWorker<Void, Void>{
	// Managers for the conversions
	XmlManager xmlManager = new XmlManager();
	JsonManager jsonManager = new JsonManager();
	ExcelManager excelManager = new ExcelManager();
	PdfManager pdfManager = new PdfManager();
	
	// Type of conversion to be made
	String convertFrom;
	String convertTo;
	
	// Reference the main class to update the progress bar
	private static main main = new main();
	
	// Logger
	static Logger log = Logger.getLogger(main.class.getName());

	public ThreadedConversion(String convertFrom, String convertTo){
		this.convertFrom = convertFrom;
		this.convertTo = convertTo;
	}

	@Override
	public Void doInBackground() throws Exception {
		// Set the start progress at 0
		main.setProgress(0);
		main.convertButtonStatus(false);
		log.info("Conversion Thread started");
		//System.out.println(convertFrom + convertTo);
				
		if (convertFrom == "Text") {

			// Convert TXT to XML
			if (convertTo == "XML") {
				xmlManager.convertToXml("TxtToXml");
			}
			
			// Convert TXT to JSON
			if (convertTo == "Json") {
				jsonManager.convertToJson("TxtToJson");
			}

			// Convert TXT to PDF
			if (convertTo == "Pdf") {
				pdfManager.convertToPdf("TxtToPdf");
				System.out.println(convertFrom + convertTo);

			}

			// Convert TXT to EXCEL
			if (convertTo == "Excel") {
				excelManager.convertToExcel("TxtToExcel");
			}
		}

		if (convertFrom == "XML") {
			// Convert XML to TXT
			if (convertTo == "Text") {
				xmlManager.convertToTxt("file");
			}

			// Convert XML to PDF
			if (convertTo == "Pdf") {
				xmlManager.convertToPdf("file");
			}

			// Convert XML to EXCEL
			if (convertTo == "Excel") {
				xmlManager.convertToExcel("file");
			}

		}
		// Conversion finished, set value to 100%
		main.setProgress(100);
		// Conversion finished, make convert button active
		main.convertButtonStatus(true);
		
		log.info("Conversion Thread finished");
		return null;
	}
}
