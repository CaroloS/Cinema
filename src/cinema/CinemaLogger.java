package cinema;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class sets up the logger for the application.
 * @author carolinesmith, daianabassi
 *
 */
public class CinemaLogger {
	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;

	/**
	 * Sets up the logger. Creates a file-handler and formatter. 
	 * Either sets this to a new file 'Logging.txt' or sets it to the
	 * logging file that already exists. 
	 * @throws IOException If problems opening the file
	 */
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