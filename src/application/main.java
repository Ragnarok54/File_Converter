package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import excelUtils.ExcelManager;
import jsonUtils.JsonManager;
import pdfUtils.PdfManager;
import utils.FileGenerator;
import xmlUtils.XmlManager;

public class main {

	static Logger log = Logger.getLogger(main.class.getName());

	protected static void initLogger() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("log4j.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);
		log.info("Log properties loaded");
	}

	public static void main(String[] args) {
		initLogger();
		log.info("Program started");

		// Create the initial frame
		JFrame initialFrame = new JFrame("File Converter");

		// Text for the initial frame
		JLabel mainText = new JLabel("Main Menu");
		mainText.setBounds(110, 30, 180, 40);
		mainText.setFont(mainText.getFont().deriveFont(30.0f));

		// mainText.set
		// Create a button for generating files
		JButton generateButton = new JButton("Generator");
		generateButton.setBounds(140, 130, 95, 30);

		// Create a button for converting
		JButton convertButton = new JButton("Convertor");
		convertButton.setBounds(140, 180, 95, 30);

		// Create a exit button
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(140, 230, 95, 30);

		// Action for the generate button
		generateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set the initial frame as invisible
				initialFrame.setVisible(false);

				FileGenerator generator = new FileGenerator();

				// Create a new frame
				JFrame generateFrame = new JFrame("Generate Input Files");

				// Text for the text generation frame
				JLabel generateText = new JLabel("Choose an option");
				generateText.setBounds(115, 30, 180, 40);
				generateText.setFont(generateText.getFont().deriveFont(20.0f));

				// Create a button for generating random tests
				JButton randomButton = new JButton("Random tests");
				randomButton.setBounds(125, 130, 150, 30);

				// Create a button for copying files from Cache folder
				JButton premadeButton = new JButton("Premade tests");
				premadeButton.setBounds(125, 180, 150, 30);

				// Create a button for returning to initial frame
				JButton backButton = new JButton("Back");
				backButton.setBounds(125, 230, 150, 30);

				// Actions for random button
				randomButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// clear the Input and the Output folders
						generator.clearFolders();
						// Generate ten or less text files
						generator.generateTxtFiles(10);
						// Generate an XML file with ten or less nodes
						generator.generateXmlFile(10);
					}
				});

				// Actions for premade button
				premadeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Clear the Input and the Output folders
						generator.clearFolders();
						// Copy the files from Cache to Input
						generator.copyFiles();
					}
				});

				// Actions for back button
				backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Set the current frame as invisible
						generateFrame.setVisible(false);
						// Set the initial frame visible
						initialFrame.setVisible(true);
					}
				});

				// Add the buttons to the frame
				generateFrame.add(generateText);
				generateFrame.add(randomButton);
				generateFrame.add(premadeButton);
				generateFrame.add(backButton);

				generateFrame.setSize(400, 400);
				generateFrame.setLayout(null);
				generateFrame.setVisible(true);
			}
		});

		// Actions for convert button
		convertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set the initial frame as invisible
				initialFrame.setVisible(false);
				XmlManager xmlManager = new XmlManager();
				JsonManager jsonManager = new JsonManager();
				ExcelManager excelManager = new ExcelManager();
				PdfManager pdfManager = new PdfManager();

				// Create a new frame for convert actions
				JFrame convertFrame = new JFrame("Convert");

				// Convert from text label
				JLabel fromText = new JLabel("Convert from:");
				fromText.setBounds(45, 50, 110, 20);
				fromText.setFont(fromText.getFont().deriveFont(15.0f));

				// Convert to text label
				JLabel toText = new JLabel("Convert to:");
				toText.setBounds(250, 50, 100, 20);
				toText.setFont(toText.getFont().deriveFont(15.0f));

				String[] from = { "Text", "XML" };
				// Drop down list for convert from options
				JComboBox<String> cbFrom = new JComboBox<String>(from);
				cbFrom.setBounds(50, 70, 90, 20);

				// Convert TXT to the following
				String[] txtTo = { "XML", "Json", "Pdf", "Excel" };
				// Convert XML to the following
				String[] xmlTo = { "Text", "Pdf", "Excel" };
				// Drop down list for convert to options
				JComboBox<String> cbTo = new JComboBox<String>(txtTo);
				cbTo.setBounds(250, 70, 90, 20);

				// Label for conversion completed
				JLabel conversionText = new JLabel("Conversion completed");
				conversionText.setBounds(100, 300, 200, 30);
				// Only set it visible after a conversion
				conversionText.setVisible(false);
				
				/*
				 * Action listener for the second combo box, so 
				 * we change its contents based on the contents
				 * of the first combo box
				 */
				cbFrom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						// If XML is selected
						if (cbFrom.getItemAt(cbFrom.getSelectedIndex()) == "XML") {
							// Remove all items
							cbTo.removeAllItems();
							// Add only the possible conversions
							for (String file : xmlTo) {
								cbTo.addItem(file);
							}
							// Hide "Conversion completed" text
							conversionText.setVisible(false);
						} else {
							// If TEXT is selected
							// Make sure the list is empty
							cbTo.removeAllItems();
							// Add only the possible conversions
							for (String file : txtTo)
								cbTo.addItem(file);
							// Hide "Conversion completed" text
							conversionText.setVisible(false);
						}
					}
				});

				// Convert button
				JButton convert = new JButton("Convert");
				convert.setBounds(120, 200, 150, 30);
				
				// Actions for convert button
				convert.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (cbFrom.getItemAt(cbFrom.getSelectedIndex()) == "Text") {

							if (cbTo.getItemAt(cbTo.getSelectedIndex()) == "Text") {
								JTextField text = new JTextField("Can not convert from Text to Text!");
								text.setBounds(100, 300, 200, 30);
								convertFrame.add(text);
							}

							if (cbTo.getItemAt(cbTo.getSelectedIndex()) == "XML") {
								xmlManager.convertToXml("TxtToXml");
							}

							if (cbTo.getItemAt(cbTo.getSelectedIndex()) == "Json") {
								jsonManager.convertToJson("TxtToJson");
							}

							if (cbTo.getItemAt(cbTo.getSelectedIndex()) == "Pdf") {
								pdfManager.convertToPdf("TxtToPdf");
							}

							if (cbTo.getItemAt(cbTo.getSelectedIndex()) == "Excel") {
								excelManager.convertToExcel("TxtToExcel");
							}
						}

						if (cbFrom.getItemAt(cbFrom.getSelectedIndex()) == "XML") {
							if (cbTo.getItemAt(cbTo.getSelectedIndex()) == "Text") {
								xmlManager.convertToTxt("file");
							}

							if (cbTo.getItemAt(cbTo.getSelectedIndex()) == "XML") {
								JTextField text = new JTextField("Can not convert from XML to XML!");
								text.setBounds(100, 300, 200, 30);
								convertFrame.add(text);
							}

							if (cbTo.getItemAt(cbTo.getSelectedIndex()) == "Json") {
								JTextField text = new JTextField("Can not convert from XML to Json!");
								text.setBounds(100, 300, 200, 30);
								convertFrame.add(text);
							}

							if (cbTo.getItemAt(cbTo.getSelectedIndex()) == "Pdf") {
								xmlManager.convertToPdf("file");
							}

							if (cbTo.getItemAt(cbTo.getSelectedIndex()) == "Excel") {
								xmlManager.convertToExcel("file");
							}

						}
						// Conversion completed, set text visible
						conversionText.setVisible(true);
						
					}
				});

				// Back button
				JButton back = new JButton("Back");
				back.setBounds(120, 250, 150, 30);
				// Action for back button
				back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Set the current frame as invisible
						convertFrame.setVisible(false);
						// Set the initial frame as visible
						initialFrame.setVisible(true);
					}
				});

				// Add the components to the frame
				convertFrame.add(fromText);
				convertFrame.add(toText);
				convertFrame.add(back);
				convertFrame.add(convert);
				convertFrame.add(cbTo);
				convertFrame.add(cbFrom);
				convertFrame.add(conversionText);

				convertFrame.setLayout(null);
				convertFrame.setSize(400, 400);
				convertFrame.setVisible(true);
			}
		});

		// Action for exit button
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initialFrame.setVisible(false);
				System.exit(0);
			}
		});

		// Add the buttons to the frame
		initialFrame.add(mainText);
		initialFrame.add(generateButton);
		initialFrame.add(convertButton);
		initialFrame.add(exitButton);

		initialFrame.setSize(400, 400);
		initialFrame.setLayout(null);
		initialFrame.setVisible(true);
	}

}
