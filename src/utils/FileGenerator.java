package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.main;

public class FileGenerator {
	// Save the absolute path to the application folder
	String AbsPath = null;
	// Logger
	static Logger log = Logger.getLogger(main.class.getName());
	// The upper bound for the random variables
	int upperBound;
	// The number of txt files and the number of nodes in the xml file
	int filesNo;
	// Used for generating random numbers
	Random rand = new Random();

	// Constructor
	public FileGenerator(int numberFiles) {
		AbsPath = Paths.get("").toAbsolutePath().toString();
		upperBound = numberFiles;
		filesNo = rand.nextInt(upperBound);
	}

	public void generateTxtFiles() {
		// For each file
		for (int file = 0; file < filesNo; file++) {
			try {
				// Create a txt file whose name is "file" followed
				// by the iteration number
				FileWriter inputFile = new FileWriter(AbsPath + "\\Files\\Input\\file" + (file + 1) + ".txt");

				// Generate the number of tags for the current file
				int tagsNo = rand.nextInt(upperBound) + 1;
				// For each tag
				for (int tag = 0; tag < tagsNo; tag++) {
					// Write the tag name in the current input file
					inputFile.write("tag" + (file + 1) + "." + (tag + 1) + ": ");

					// Generate the number of attributes for the current tag
					int atbNo = rand.nextInt(upperBound) + 1;
					// For each attribute
					for (int atb = 0; atb < atbNo; atb++) {
						// Write the attribute name in the current input file
						if (atb != atbNo - 1)
							inputFile.write("atb" + (file + 1) + "." + (tag + 1) + "." + (atb + 1) + ", ");
						else
							inputFile.write("atb" + (file + 1) + "." + (tag + 1) + "." + (atb + 1));

					}

					// Add new line to the current input file
					inputFile.write("\n");
				}
				
				log.info("Generated txt files");
				// Close the input file created
				inputFile.close();
			} catch (IOException e) {
				log.error("Error while generating files");
				//System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
	}

	public void generateXmlFile() {
		try {
			// Path to the XML file to be created
			String xmlFilePath = AbsPath + "\\Files\\Input\\file.xml";

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			// Used for generating random numbers
			Random rand = new Random();

			// Root element
			Element root = document.createElement("files");
			document.appendChild(root);

			for (int node = 0; node < filesNo; node++) {
				// Create the node and append it to the root
				Element element = document.createElement("item" + (node + 1));
				root.appendChild(element);

				// Generate the number of tags for the current node
				int tagsNo = rand.nextInt(upperBound) + 1;
				for (int tag = 0; tag < tagsNo; tag++) {
					// Create the tag
					Element elem = document.createElement("tag" + (node + 1) + "." + (tag + 1));
					// Generate the number of attributes for the current tag
					int atbNo = rand.nextInt(upperBound) + 1;
					// Create a string for the attributes
					String attribute = "";
					for (int atb = 0; atb < atbNo; atb++) {
						// Add the attributes to the string
						if (atb != atbNo - 1)
							attribute += "atb" + (node + 1) + "." + (tag + 1) + "." + (atb + 1) + ", ";
						else
							attribute += "atb" + (node + 1) + "." + (tag + 1) + "." + (atb + 1);
					}
					// Add the string of attributes to the tag
					elem.appendChild(document.createTextNode(attribute));
					// Add the tag to the node
					element.appendChild(elem);
				}
			}

			// Create the XML file
			// Transform the DOM Object to an XML File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(xmlFilePath));

			transformer.transform(domSource, streamResult);
			
			log.info("Generated XML file");
			//System.out.println("Done creating XML File");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	// Clear Input and Output folders
	public void clearFolders() {
		clearFolder("Input");
		clearFolder("Output");
	}

	// Delete all files in the Input/Output folder
	private void clearFolder(String folder) {
		// Path to the Input/Output folder
		String folderPath = AbsPath + "\\Files\\" + folder;
		File file = new File(folderPath);
		File[] files = file.listFiles();

		// For each file in the Input folder
		for (File f : files) {
			if (f.isFile() && f.exists()) {
				// Delete the file
				f.delete();
				System.out.println("Successfully deleted");
			} else {
				System.out.println("Cant delete a file due to open or error");
			}
		}
		log.info("Cleared " + folder + " folder");
	}

	public void copyFiles() {
		// Source directory which you want to copy to new location
		File sourceFolder = new File(AbsPath + "\\Files\\Cache\\");

		// Target directory where files should be copied
		File destinationFolder = new File(AbsPath + "\\Files\\Input\\");

		// Get all files from source directory
		String files[] = sourceFolder.list();

		// Iterate over all files and copy them to destinationFolder one by one
		for (String file : files) {
			File filePath = new File(AbsPath + "\\Files\\Cache\\" + file);
			try {
				File newFilePath = new File(destinationFolder.toPath() + "\\" + file.toString());
				Files.copy(filePath.toPath(), newFilePath.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		log.info("Copied cached files");
	}
}
