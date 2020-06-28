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
    StreamResult out;
    TransformerHandler th;
    
    public TxtToXml() {
    	out =  new StreamResult(Paths.get("").toAbsolutePath().toString() + "\\Files\\Output\\" + "files.xml");
    	convert();
    }

    public void convert() {
        try {
            openXml();
            
            FileParser fileParser = new FileParser();
    		int fileNumber = fileParser.getFileNumber();
            
    		for (int fileIterator = 0; fileIterator < fileNumber; fileIterator++) { // For each txt file
            	String[] fileName = fileParser.getFileName().split(".txt"); // Get the name of the file without ".txt"
            	th.startElement(null, null, fileName[0], null); // First attribute of current file id the name of the file
            	
            	for (line fileLine : fileParser.getAttributes()) { // Sum up all the attributes
    				String attributes = ""; 
    				for (String attribute : fileLine.getAttributes()) {
    					if (fileLine.getAttributes().get(fileLine.getAttributes().size() - 1) == attribute)
    						attributes += attribute;
    					else
    						attributes += (attribute + ", "); // Add ',' only if the attribute is not the last one
    				}
    				
    				th.startElement(null, null, fileLine.getTag(), null); 
    				th.characters(attributes.toCharArray(), 0, attributes.length());
    				th.endElement(null, null, fileLine.getTag()); // The end element of the current line is the tag
            	}
            	th.endElement(null, null, fileName[0]); // End attribute of current file
            	fileParser.next();
            }
            closeXml();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openXml() throws ParserConfigurationException, TransformerConfigurationException, SAXException {

        SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        th = tf.newTransformerHandler();

        // pretty XML output
        Transformer serializer = th.getTransformer();
        serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");

        th.setResult(out);
        th.startDocument();
        th.startElement(null, null, "files", null);
    }

    public void closeXml() throws SAXException {
        th.endElement(null, null, "files");
        th.endDocument();
    }
}