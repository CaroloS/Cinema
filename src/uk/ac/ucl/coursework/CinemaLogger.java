package uk.ac.ucl.coursework;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CinemaLogger {
	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;

	static public void setup() throws IOException {

		//GETS THE GLOBAL LOGGER
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

		//SETS THE LOGGER LEVEL TO 'INFO' FOR THE PROJECT
		logger.setLevel(Level.INFO);

		//CREATES NEW FILEHANDLER TO HANDLE THE LOG FILES
		if (fileTxt == null) {
			fileTxt = new FileHandler("Logging.txt");
			formatterTxt = new SimpleFormatter();
			fileTxt.setFormatter(formatterTxt);
			logger.addHandler(fileTxt);
		}
		else {
			formatterTxt = new SimpleFormatter();
			fileTxt.setFormatter(formatterTxt);
			logger.addHandler(fileTxt);
		}

	}
}