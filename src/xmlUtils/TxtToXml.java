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
	//BufferedReader in;
    StreamResult out;
    TransformerHandler th;

    public static void main(String args[]) {
        new TxtToXml().begin();
    }

    public void begin() {
        try {
            out = new StreamResult("files.xml");
            openXml();
            
            File dir = new File(Paths.get("").toAbsolutePath().toString() + "\\Files\\Input\\");
            File[] directoryListing = dir.listFiles();
            for(File child : directoryListing) {
            	TxtManager txtManager = new TxtManager(child.getName());
            	String[] fileName = child.getName().split(".txt");
            	th.startElement(null, null, fileName[0], null);
            	for(TxtReader x: txtManager.getContents())
            		process(x);
            	
            	th.endElement(null, null, fileName[0]);
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
        th.startElement(null, null, s.getTag(), null);
        
        for(String atribute : s.getAttributeList()) {
        	if(atribute != s.getAttributeList().get(s.getAttributeList().size()-1))
        		atribute += ',';
        	th.characters(atribute.toCharArray(), 0, atribute.length());
        }
        
        th.endElement(null, null, s.getTag());
    }

    public void closeXml() throws SAXException {
        th.endElement(null, null, "files");
        th.endDocument();
    }
}