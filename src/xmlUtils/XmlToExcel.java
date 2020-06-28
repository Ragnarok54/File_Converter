package xmlUtils;

import java.nio.file.Paths;

public class XmlToExcel implements ConvertFromXml {
	String fileName = null; // Name of the XML file
	String AbsPath = null; // Absolute path to folder location
	String filePath = null; // Relative path to file from folder
	
	protected XmlToExcel(String fileToBeConverted, String folderLocation) {
		fileName = new String (fileToBeConverted);
		AbsPath = Paths.get("").toAbsolutePath().toString();
		filePath = folderLocation;
		//stuff
		
		convert();
		write();
	}
	
	@Override
	public void convert() {
		// TODO Auto-generated method stub

	}

	@Override
	public void write() {
		// TODO Auto-generated method stub

	}

}
