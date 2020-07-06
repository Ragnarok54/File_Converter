package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import jsonUtils.JsonManager;

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

		// Code here
	}

}
