package xmlUtils;

/*
 * User Interface class to handle txt to XML conversion and
 * also supports the addition of XML to txt and XML to other 
 * formats
 */

public class XmlManager {
	TxtToXml convertToXML = null;
	XmlToTxt convertToTxt = null;

	// Constructor
	public XmlManager() {

	}

	// Converts all txt files to a single XML file
	public void convertToXml(String fileToBeCreated) {
		// Use the TxtToXml class to handle the conversion and file creation
		convertToXML = new TxtToXml(fileToBeCreated);
		System.out.println("Converted all txt files to " + fileToBeCreated + ".xml");
	}

	public void convertToTxt(String fileToBeRead) {
		// convertToTxt = new XmlToTxt(fileToBeRead);
	}

}
