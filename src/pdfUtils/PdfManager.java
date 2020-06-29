package pdfUtils;

/*
 * User Interface class to handle text to PDF conversion and
 * also supports the addition of PDF to text and PDF to other 
 * formats
 */
public class PdfManager {
	TxtToPdf convertToPdf = null;
	PdfToTxt convertToTxt = null;

	public PdfManager() {

	}

	// Converts all text files to a single PDF file
	public void convertToPdf(String fileToBeCreated) {
		// Use the TxtToPdf class to handle the conversion and file creation
		convertToPdf = new TxtToPdf(fileToBeCreated);

		System.out.println("Converted all txt files to " + fileToBeCreated + ".pdf");
	}

	public void convertToTxt(String fileToBeRead) {
		// convertToTxt = new PdfToTxt();
	}
}
