package application;

import java.awt.Component;
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
		JFrame initialFrame = new JFrame("Start");
		
		// Create a button for generating files
		JButton generateButton = new JButton("Generate");
		generateButton.setBounds(140, 100, 95, 30);
		
		// Create a button for converting
		JButton convertButton = new JButton("Convert");
		convertButton.setBounds(140, 150, 95, 30);
	
		// Create a exit button
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(140, 200, 95, 30);

		// Action for the generate button
		generateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set the initial frame as invisible
				initialFrame.setVisible(false);
				
				FileGenerator generator = new FileGenerator();
				
				// Create a new frame
				JFrame generateFrame = new JFrame("Generate Frame");
				
				// Create a button for generating random tests
				JButton randomButton = new JButton("Random tests");
				randomButton.setBounds(125, 100, 150, 30);
				
				// Create a button for copying files from Cache folder
				JButton premadeButton = new JButton("Premade tests");
				premadeButton.setBounds(125, 150, 150, 30);
				
				// Create a button for returning to initial frame
				JButton backButton = new JButton("Back");
				backButton.setBounds(125, 200, 150, 30);

				// Actions for random button
				randomButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// clear the Input and the Output folders
						generator.clearFolders();
						// Generate ten or less text files
						generator.generateTxtFiles(10);
						// Generate an xml file with ten or less nodes
						generator.generateXmlFile(10);
					}
				});
				
				// Actions for premade button 
				premadeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Clear the Input and the Output folders
						generator.clearFolders();
						// Copy the files for Cache to Input
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
				
				XmlManager xml = new XmlManager();
				JsonManager json = new JsonManager();
				ExcelManager excel = new ExcelManager();
				PdfManager pdf = new PdfManager();
				
				// Create a new frame for convert actions
				JFrame convertFrame = new JFrame("Convert Frame");
				
				// Convert from text field
				JTextField fromText = new JTextField("Convert from:");
				fromText.setBounds(50, 50, 80, 20);
				
				// Convert to text field
				JTextField toText = new JTextField("Convert to:");
				toText.setBounds(250, 50, 80, 20);
				
				String[] from = {"Text", "XML"}; 
				// Drop down list for convert from options
				JComboBox cbFrom = new JComboBox(from);
				cbFrom.setBounds(50, 70,90,20);
				
				String[] to = {"Text", "XML", "Json", "Pdf", "Excel"};
				// Drop down list for convert to options
				JComboBox cbTo = new JComboBox(to);
				cbTo.setBounds(250, 70, 90, 20);
				
				// Convert button
				JButton convert = new JButton("Convert");
				convert.setBounds(120, 200, 150, 30);
				// Actions for convert button
				convert.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(cbFrom.getItemAt(cbFrom.getSelectedIndex()) == "Text") {
							if(cbTo.getItemAt(cbTo.getSelectedIndex()) == "Text") {
								JTextField text = new JTextField("Can not convert from Text to Text!");          
						    	text.setBounds(100, 300, 200, 30);   
						    	convertFrame.add(text);
							}
							
							if(cbTo.getItemAt(cbTo.getSelectedIndex()) == "XML") {
								xml.convertToXml("TxtToXml");
							}
							
							if(cbTo.getItemAt(cbTo.getSelectedIndex()) == "Json") {
								json.convertToJson("TxtToJson");
							}
							
							if(cbTo.getItemAt(cbTo.getSelectedIndex()) == "Pdf") {
								pdf.convertToPdf("TxtToPdf");
							}
							
							if(cbTo.getItemAt(cbTo.getSelectedIndex()) == "Excel") {
								excel.convertToExcel("TxtToExcel");
							}
						}
						
						if(cbFrom.getItemAt(cbFrom.getSelectedIndex()) == "XML") {
							if(cbTo.getItemAt(cbTo.getSelectedIndex()) == "Text") {
								xml.convertToTxt("file");
							}
							
							if(cbTo.getItemAt(cbTo.getSelectedIndex()) == "XML") {
								JTextField text = new JTextField("Can not convert from XML to XML!");          
						    	text.setBounds(100, 300, 200, 30);   
						    	convertFrame.add(text);
							}
							
							if(cbTo.getItemAt(cbTo.getSelectedIndex()) == "Json") {
								JTextField text = new JTextField("Can not convert from XML to Json!");          
						    	text.setBounds(100, 300, 200, 30);   
						    	convertFrame.add(text);
							}
							
							if(cbTo.getItemAt(cbTo.getSelectedIndex()) == "Pdf") {
								xml.convertToPdf("file");
							}
							
							if(cbTo.getItemAt(cbTo.getSelectedIndex()) == "Excel") {
								xml.convertToExcel("file");
							}
							
						}
					}
				});
				
				// Exit button
				JButton back = new JButton("Back");
				back.setBounds(120, 250, 150, 30);
				// Action for exit button
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
				
				convertFrame.setLayout(null);
				convertFrame.setSize(400, 400);
				convertFrame.setVisible(true);
			}
		});
		
		// Action for exit button
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initialFrame.setVisible(false);
			}
		});

		// Add the buttons to the frame
		initialFrame.add(generateButton);
		initialFrame.add(convertButton);
		initialFrame.add(exitButton);
		
		initialFrame.setSize(400, 400);
		initialFrame.setLayout(null);
		initialFrame.setVisible(true);
	}

}
