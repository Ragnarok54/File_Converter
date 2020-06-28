package xmlUtils;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.xml.sax.*;

import txtUtils.TxtManager;
import txtUtils.TxtReader;
import utils.FileParser;
import utils.line;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.sax.*;

public class TxtToXml {
	private StreamResult xmlFile; // Holds the path of the file to be created
	private TransformerHandler convertor; // Handles the conversions from txt to xml

	// Constructor that also handles file creation
	protected TxtToXml(String fileName) {
		xmlFile = new StreamResult(Paths.get("").toAbsolutePath().toString() + "\\Files\\Output\\" + fileName + ".xml");
		convert();
	}

	/*
	 * Creates the structure of the XML file based on the txt files from the Input
	 * folder
	 */
	private void convert() {
		try {
			openXml();

			FileParser fileParser = new FileParser();
			int fileNumber = fileParser.getFileNumber();

			// For each txt file
			for (int fileIterator = 0; fileIterator < fileNumber; fileIterator++) {
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
			e.printStackTrace();
		}
	}

	// Creates the XML file base
	private void openXml() throws ParserConfigurationException, TransformerConfigurationException, SAXException {

		SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		convertor = tf.newTransformerHandler();

		// pretty XML output
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