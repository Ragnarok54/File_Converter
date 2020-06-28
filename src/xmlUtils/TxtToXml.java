package xmlUtils;
import java.io.*;
import java.nio.file.Paths;

import org.xml.sax.*;

import txtUtils.TxtManager;
import txtUtils.TxtReader;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.sax.*;

public class TxtToXml {
    StreamResult out;
    TransformerHandler th;

    public static void main(String args[]) {
        new TxtToXml().begin();
    }

    public void begin() {
        try {
            out = new StreamResult("files.xml"); // Output file
            openXml();
            
            File dir = new File(Paths.get("").toAbsolutePath().toString() + "\\Files\\Input\\"); // Directory path for the input files
            File[] directoryListing = dir.listFiles(); // Get all the names of the files present in the given directory 
            
            for(File child : directoryListing) { // Parse all files present in the given directory
            	TxtManager txtManager = new TxtManager(child.getName()); // Creates a text manager object for each file
            	
            	String[] fileName = child.getName().split(".txt"); // Get the name of the file without ".txt"
            	th.startElement(null, null, fileName[0], null); // First attribute of current file id the name of the file
            	
            	for(TxtReader x: txtManager.getContents()) // Get the content of the current file and parse it
            		process(x);
            	
            	th.endElement(null, null, fileName[0]); // End attribute of current file
            }

            closeXml();
        } catch (Exception e) {
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

    public void process(TxtReader s) throws SAXException {
        th.startElement(null, null, s.getTag(), null); // The start element of the current line is the tag
        
        for(String attribute : s.getAttributeList()) { // Add the attributes
        	if(attribute != s.getAttributeList().get(s.getAttributeList().size()-1))
        		attribute += ','; // Add ',' after each attribute except the last one
        	th.characters(attribute.toCharArray(), 0, attribute.length());
        }
        
        th.endElement(null, null, s.getTag()); // The end element of the current line is the tag
    }

    public void closeXml() throws SAXException {
        th.endElement(null, null, "files");
        th.endDocument();
    }
}