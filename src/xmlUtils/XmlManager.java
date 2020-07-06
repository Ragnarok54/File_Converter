package xmlUtils;

import org.apache.log4j.Logger;

import application.main;

/*
 * User Interface class to handle txt to XML conversion and
 * also supports the addition of XML to txt and XML to other 
 * formats
 */

public class XmlManager {
	TxtToXml convertToXML = null;
	XmlToTxt convertToTxt = null;
	XmlToPdf convertToPdf = null;
	XmlToExcel convertToExcel = null;
	// Logger
	static Logger log = Logger.getLogger(main.class.getName());

	// Constructor
	public XmlManager() {

	}

	// Converts all txt files to a single XML file
	public void convertToXml(String fileToBeCreated) {
		// Use the TxtToXml class to handle the conversion and file creation
		convertToXML = new TxtToXml(fileToBeCreated);
		log.info("Converted all txt files to xml");
		//System.out.println("Converted all txt files to " + fileToBeCreated + ".xml");
	}

	// Converts an xml file to the respective amounts of txt files
	public void convertToTxt(String fileToBeRead) {
		 convertToTxt = new XmlToTxt(fileToBeRead);
		 log.info("Converted the XML file into multiple txt files");
		 //System.out.println("Converted the XML file into multiple txt files");
	}
	
	// Convert an XML file to an excel spreadsheet
	public void convertToExcel(String fileToBeRead) {
		convertToExcel = new XmlToExcel(fileToBeRead);
		log.info("Converted the XML file into excel");
		//System.out.println("Converted the XML file into " + fileToBeRead + ".xlsx");
	}
	
	// Convert an XML file to a pdf
	public void convertToPdf(String fileToBeRead) {
		convertToPdf = new XmlToPdf(fileToBeRead);
		log.info("Converted the XML file into pdf");
		//System.out.println("Converted the XML file into " + fileToBeRead + ".pdf");
	}
}
