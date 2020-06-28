package utils;

import java.util.ArrayList;

public class line {

	private String tag = null;
	private ArrayList<String> attributes = null;

	public void setTag(String tag) {
		this.tag = tag;
		attributes = new ArrayList<String>();
	}

	public void add(String attribute) {
		attributes.add(attribute);
	}

	public String getTag() {
		return this.tag;
	}

	public ArrayList<String> getAttributes() {
		return this.attributes;
	}
}
