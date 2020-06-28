package utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import txtUtils.TxtManager;

public class FileParser {
	private String AbsPath = null;
	private String input = "\\Files\\Input";
	private int fileNumber = 0;
	private ArrayList<TxtManager> txtFiles = null;
	private int fileIterator = 0;

	public FileParser() {
		AbsPath = Paths.get("").toAbsolutePath().toString();
		txtFiles = new ArrayList<TxtManager>();
		this.parse();
	}

	private void parse() {
		File dir = new File(AbsPath + input);
		File[] directoryListing = dir.listFiles();
		for (File childFile : directoryListing) {
			TxtManager parsedFile = new TxtManager(childFile.getName());
			fileNumber++;
			txtFiles.add(parsedFile);
		}
	}

	public int getFileNumber() {
		return fileNumber;
	}

	public boolean next() {
		fileIterator++;
		if (fileIterator < txtFiles.size())
			return true;
		return false;
	}

	public String getFileName() {
		return txtFiles.get(fileIterator).getFileName();
	}

	public ArrayList<line> getAttributes() {
		return txtFiles.get(fileIterator).getFileContents();
	}

}
