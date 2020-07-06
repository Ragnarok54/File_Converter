package pdfUtils;

import org.apache.log4j.Logger;

import application.main;

/*
 * User Interface class to handle text to PDF conversion and
 * also supports the addition of PDF to text and PDF to other 
 * formats
 */
public class PdfManager {
	TxtToPdf convertToPdf = null;
	PdfToTxt convertToTxt = null;

	// Logger
	static Logger log = Logger.getLogger(main.class.getName());

	// Constructor
	public PdfManager() {

	}

	// Converts all text files to a single PDF file
	public void convertToPdf(String fileToBeCreated) {
		// Use the TxtToPdf class to handle the conversion and file creation
		convertToPdf = new TxtToPdf(fileToBeCreated);

		log.info("Converted all txt files to pdf");
		//System.out.println("Converted all txt files to " + fileToBeCreated + ".pdf");
	}

	public void convertToTxt(String fileToBeRead) {
		// convertToTxt = new PdfToTxt();
	}
}
