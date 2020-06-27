package txtUtils;

import java.util.ArrayList;

public class TxtManager {
	private String file; // holds the path to the file that is being operated on 
	private ArrayList<TxtReader> contents; // contains the tag and its attributes
	
	public TxtManager(String file) {
		this.file = file;
		contents = new ArrayList<TxtReader>();
	}
	
}
