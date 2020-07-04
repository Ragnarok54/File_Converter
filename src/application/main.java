package application;

import javafx.util.Pair;
import utils.XmlParser;

public class main {

	public static void main(String[] args) {
		XmlParser test = new XmlParser("files");
		int attributeIterator = 0;
		for(Pair<String, Integer> a : test.getFileName()) {
			attributeIterator ++; 
			System.out.println(a.getKey());
			for(int it = attributeIterator; it < test.getAttributeList().size(); it++) {
				System.out.println(test.getAttributeList().get(it).getTag());
				for(String atb : test.getAttributeList().get(it).getAttributes()) {
					System.out.print(atb + " ");
				}
			}
		}
	}

}
