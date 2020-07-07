package xmlUtils;

import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import application.main;
import txtUtils.TxtParser;
import utils.line;

public class TxtToXml {
	// Holds the path of the file to be created
	private StreamResult xmlFile;
	// Handles the conversions from txt to xml
	private TransformerHandler convertor;
	// Reference the main class to update the progress of the conversion
	private static main main = new main();
	// Logger
	static Logger log = Logger.getLogger(main.class.getName());

	
	// Constructor that also handles file creation
	protected TxtToXml(String fileName) {
		xmlFile = new StreamResult(Paths.get("").toAbsolutePath().toString() + "\\Files\\Output\\" + fileName + ".xml");
		convert();
	}

	/*
	 * Creates the structure of the XML file based
	 * on the txt files from the Input folder
	 */
	private void convert() {
		try {
			openXml();

			TxtParser fileParser = new TxtParser();
			int fileNumber = fileParser.getFileNumber();

			// For each txt file
			for (int fileIterator = 0; fileIterator < fileNumber; fileIterator++) {
				// Set the progress bar value
				main.setProgress((int) ((float)(fileIterator + 1) / (float)(fileNumber + 1) * 100));
				
				// Get the name of the file without ".txt"
				String[] fileName = fileParser.getFileName().split(".txt");
				
				// First attribute of current file is the name of the file
				convertor.startElement(null, null, fileName[0], null);

				// Sum up all the attributes
				for (line fileLine : fileParser.getAttributes()) {
					String attributes = "";
					for (String attribute : fileLine.getAttributes()) {
						if (fileLine.getAttributes().get(fileLine.getAttributes().size() - 1) == attribute)
							attributes += attribute;
						else
							// Add ',' only if the attribute is not the last one
							attributes += (attribute + ", ");
					}
					// The start element of the current line is the tag
					convertor.startElement(null, null, fileLine.getTag(), null);
					// The characters of the current line are the attributes summed up above
					convertor.characters(attributes.toCharArray(), 0, attributes.length());
					// The end element of the current line is the tag
					convertor.endElement(null, null, fileLine.getTag());
				}
				// End attribute of current file
				convertor.endElement(null, null, fileName[0]);

				// Go the the next file
				fileParser.next();
			}
			closeXml();
		} catch (Exception e) {
			log.error("Error while processing file");
			e.printStackTrace();
		}
	}

	// Creates the XML file base
	private void openXml() throws ParserConfigurationException, TransformerConfigurationException, SAXException {

		SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		convertor = tf.newTransformerHandler();

		// Indent XML output
		Transformer serializer = convertor.getTransformer();
		serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");

		convertor.setResult(xmlFile);
		convertor.startDocument();
		convertor.startElement(null, null, "files", null);
	}

	private void closeXml() throws SAXException {
		convertor.endElement(null, null, "files");
		convertor.endDocument();
	}
}