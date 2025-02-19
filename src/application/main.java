package application;

import java.awt.Color;
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
import javax.swing.JProgressBar;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import utils.FileGenerator;
import utils.ThreadedConversion;

public class main {
	// Number of tests generated
	static int numberOfTests = 150;
	// Logger
	static Logger log = Logger.getLogger(main.class.getName());
	
	// Progress bar for the convert frame
	static JProgressBar progressBar = new JProgressBar(0, 100);
	// Convert button for the convert frame
	static JButton convert;
	
	// Function to modify the status of a button
	public void convertButtonStatus(boolean status) {
		convert.setEnabled(status);
	}
	
	// Function to update the progress bar
	public void setProgress(int value) {
		progressBar.setValue(value);
	}

	// Load the logger properties
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

	
	// Main function of the application
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
		generateButton.setBounds(115, 130, 150, 30);
		generateButton.setBackground(new Color(207, 200, 246));

		// Create a button for converting
		JButton convertButton = new JButton("Convertor");
		convertButton.setBounds(115, 180, 150, 30);
		convertButton.setBackground(new Color(207, 200, 246));

		// Create a exit button
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(115, 230, 150, 30);
		exitButton.setBackground(new Color(252, 215, 217));

		// Action for the generate button
		generateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set the initial frame as invisible
				initialFrame.setVisible(false);

				FileGenerator generator = new FileGenerator(numberOfTests);

				// Create a new frame
				JFrame generateFrame = new JFrame("Generate Input Files");

				// Text for the text generation frame
				JLabel generateText = new JLabel("Choose an option");
				generateText.setBounds(115, 30, 180, 40);
				generateText.setFont(generateText.getFont().deriveFont(20.0f));

				// Create a button for generating random tests
				JButton randomButton = new JButton("Random tests");
				randomButton.setBounds(125, 130, 150, 30);
				randomButton.setBackground(new Color(207, 200, 246));

				// Create a button for copying files from Cache folder
				JButton premadeButton = new JButton("Default tests");
				premadeButton.setBounds(125, 180, 150, 30);
				premadeButton.setBackground(new Color(207, 200, 246));

				// Create a button for returning to initial frame
				JButton backButton = new JButton("Back");
				backButton.setBounds(125, 230, 150, 30);
				backButton.setBackground(new Color(252, 215, 217));

				// Actions for random button
				randomButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// clear the Input and the Output folders
						generator.clearFolders();
						// Generate ten or less text files
						generator.generateTxtFiles();
						// Generate an XML file with ten or less nodes
						generator.generateXmlFile();
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
				generateFrame.getContentPane().setBackground(new Color(235, 234, 242));
				generateFrame.setLayout(null);
				generateFrame.setVisible(true);
			}
		});

		// Actions for convert button
		convertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set the initial frame as invisible
				initialFrame.setVisible(false);

				// Create a new frame for convert actions
				JFrame convertFrame = new JFrame("Convertor");

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
				cbFrom.setBackground(new Color(207, 200, 246));

				// Convert TXT to the following
				String[] txtTo = { "XML", "Json", "Pdf", "Excel" };
				// Convert XML to the following
				String[] xmlTo = { "Text", "Pdf", "Excel" };
				// Drop down list for convert to options
				JComboBox<String> cbTo = new JComboBox<String>(txtTo);
				cbTo.setBounds(250, 70, 90, 20);
				cbTo.setBackground(new Color(207, 200, 246));

				/*
				 * Action listener for the second combo box, so we change its contents based on
				 * the contents of the first combo box
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
							// Hide progress bar on new selection
							progressBar.setVisible(false);

						} else {
							// If TEXT is selected
							// Make sure the list is empty
							cbTo.removeAllItems();
							// Add only the possible conversions
							for (String file : txtTo)
								cbTo.addItem(file);
							// Hide progress bar on new selection
							progressBar.setVisible(false);
						}
					}
				});

				// Convert button
				convert = new JButton("Convert");
				convert.setBounds(120, 200, 150, 30);
				convert.setBackground(new Color(207, 200, 246));

				progressBar.setStringPainted(true);
				progressBar.setBounds(120, 160, 150, 15);
				progressBar.setStringPainted(true);
				progressBar.setForeground(new Color(142, 228, 176));

				// Actions for convert button
				convert.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						convertFrame.add(progressBar);
						progressBar.setVisible(true);
						ThreadedConversion thread = new ThreadedConversion(cbFrom.getItemAt(cbFrom.getSelectedIndex()),
								cbTo.getItemAt(cbTo.getSelectedIndex()));
						// Start new thread for conversion
						try {
							// thread.addPropertyChangeListener(this);
							thread.execute();
						} catch (Exception e1) {
							log.error("Error while starting conversion Thread");
							e1.printStackTrace();
						}
						// convertFrame.remove(progressBar);
					}
				});

				// Back button
				JButton back = new JButton("Back");
				back.setBounds(120, 250, 150, 30);
				back.setBackground(new Color(252, 215, 217));
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

				convertFrame.setLayout(null);
				convertFrame.setSize(400, 400);
				convertFrame.getContentPane().setBackground(new Color(235, 234, 242));
				convertFrame.setVisible(true);
			}
		});

		// Action for exit button
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initialFrame.setVisible(false);
				log.info("Program exit");
				System.exit(0);
			}
		});

		// Add the buttons to the frame
		initialFrame.add(mainText);
		initialFrame.add(generateButton);
		initialFrame.add(convertButton);
		initialFrame.add(exitButton);

		initialFrame.setSize(400, 400);
		initialFrame.getContentPane().setBackground(new Color(235, 234, 242));
		initialFrame.setLayout(null);
		initialFrame.setVisible(true);
		// Set the program to exit on frame close
		initialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
