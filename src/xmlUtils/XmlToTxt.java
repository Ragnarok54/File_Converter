package xmlUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import application.main;
import javafx.util.Pair;

public class XmlToTxt implements ConvertFromXml {
	// Name of the XML file
	String xmlFile = null;
	// Holds the absolute path to the application folder
	String AbsPath = null;
	// Reference the main class to update the progress of the conversion
	private static main main = new main();
	// Logger
	static Logger log = Logger.getLogger(main.class.getName());

	// Constructor
	protected XmlToTxt(String fileToBeConverted) {
		xmlFile = new String(fileToBeConverted);
		AbsPath = Paths.get("").toAbsolutePath().toString();
		convert();
	}

	@Override
	// Converts the xml file into multiple txt files
	// The number of txt files is the number of nodes in the XML file
	public void convert() {
		XmlParser xmlParser = new XmlParser(xmlFile);

		int attributeIterator = 0;
		// For each node in the XML file
		for (Pair<String, Integer> node : xmlParser.getFileName()) {
			try {
				// Set the progress bar value
				main.setProgress((int) ((float)(xmlParser.getFileName().indexOf(node) + 1) / (float)(xmlParser.getFileName().size() + 1) * 100));
				
				// Create a txt file whose name is the current node name
				FileWriter outputFile = new FileWriter(AbsPath + "\\Files\\Output\\" + node.getKey() + ".txt");

				// For each line in the current node
				for (int line = 0; line < node.getValue(); line++) {
					// Write the tag of the line in the current output file
					outputFile.write(xmlParser.getAttributeList().get(line + attributeIterator).getTag() + ": ");

					// For each attribute in line
					for (String atb : xmlParser.getAttributeList().get(line + attributeIterator).getAttributes()) {
						// Write the attribute in the current output file
						outputFile.write(atb + " ");
					}

					// Write next information on a new line
					outputFile.write("\n");
				}

				// Close the output file created
				outputFile.close();
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}

			// Used to get the new node info
			attributeIterator += node.getValue();
		}
	}

	@Override
	public void write() {
		// TODO Auto-generated method stub

	}

}
