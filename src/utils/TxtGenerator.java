package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

public class TxtGenerator {
	String option = null;

	public TxtGenerator(String option){
		this.option = option;
		// Clear the folder before generating txt files
		clearFolder();
		// Generate txt files
		generateTxtFiles();
	}

	private void generateTxtFiles() {
		Random rand = new Random();
		int upperBound = 0;

		if (option.toLowerCase() == "small")
			upperBound = 10;
		if (option.toLowerCase() == "medium")
			upperBound = 100;
		if (option.toLowerCase() == "big")
			upperBound = 1000;

		// Generate the number of txt files
		int filesNo = rand.nextInt(upperBound) + 1;
		// For each file
		for (int file = 0; file < filesNo; file++) {
			try {
				// Create a txt file whose name is "file" followed by the number of the iteration
				FileWriter inputFile = new FileWriter(
						Paths.get("").toAbsolutePath().toString() + "\\Files\\Input\\file" + (file + 1) + ".txt");

				// Generate the number of tags for the current file
				int tagsNo = rand.nextInt(upperBound) + 1;
				// For each tag
				for (int tag = 0; tag < tagsNo; tag++) {
					// Write the tag name in the current input file
					inputFile.write("item." + (file + 1) + "." + (tag + 1) + ": ");

					// Generate the number of attributes for the current tag
					int atbNo = rand.nextInt(upperBound) + 1;
					// For each attribute
					for (int atb = 0; atb < atbNo; atb++) {
						// Write the attribute name in the current input file
						if(atb != atbNo - 1)
							inputFile.write("item." + (file + 1) + "." + (tag + 1) + "." + (atb + 1) + ", ");
						else
							inputFile.write("item." + (file + 1) + "." + (tag + 1) + "." + (atb + 1));
							
					}

					// Add new line to the current input file
					inputFile.write("\n");
				}

				// Close the input file created
				inputFile.close();
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
	}

	// Delete all files in the Input folder
	private void clearFolder() {
		// Path to the Input folder
		String path = Paths.get("").toAbsolutePath().toString() + "\\Files\\Input";
		File file = new File(path);
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
	}
	
	
	public void copyFiles() {
		//Source directory which you want to copy to new location
        File sourceFolder = new File(Paths.get("").toAbsolutePath().toString() + "\\Files\\Cache\\");
         
        //Target directory where files should be copied
        File destinationFolder = new File(Paths.get("").toAbsolutePath().toString() + "\\Files\\Input\\");
        
        //Get all files from source directory
        String files[] = sourceFolder.list();
         
        //Iterate over all files and copy them to destinationFolder one by one
        for (String file : files) {
        	File filePath = new File(Paths.get("").toAbsolutePath().toString() + "\\Files\\Cache\\" + file);
        	try {
				Files.copy(filePath.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }
	}
}
