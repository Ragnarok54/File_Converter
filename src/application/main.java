package application;

import javafx.util.Pair;
import utils.XmlParser;

public class main {

	public static void main(String[] args) {
		XmlParser test = new XmlParser("files");
		int attributeIterator = 0;
		for (Pair<String, Integer> a : test.getFileName()) {
			System.out.println("file name is " + a.getKey());
			for (int it = 0; it < a.getValue(); it++) {
				System.out.print("\n" + test.getAttributeList().get(it + attributeIterator).getTag() + ": ");
				for (String atb : test.getAttributeList().get(it + attributeIterator).getAttributes()) {
					System.out.print(atb + " ");
				}
			}
			System.out.println();
			attributeIterator += a.getValue();
		}
	}

}
