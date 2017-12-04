package cinema;

import java.io.IOException;
import java.util.logging.Logger;

import cinema.XML.CreateUsersXML;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CinemaMain extends Application {

	// GET THE GLOBAL LOGGER
	public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// DECLARE PUBLIC STAGE SO SCENES IN OTHER CLASSES CAN SET THE STAGE
	public static Stage thestage;

	public void goToNextPage(String pathToFXML, String pageTitle) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(pathToFXML));

			Scene scene = new Scene(root, 760, 810);
			thestage.setTitle(pageTitle);
			thestage.setScene(scene);
			thestage.show();
		} catch (Exception e) {
			LOGGER.warning("Couldn't load that page");
			e.printStackTrace();
		}
	}

	/**
	 * Starting method for the program launched from 'main' Sets the stage to
	 * scene1 - Login Screen 
	 * @param primaryStage 
	 */
	@Override
	public void start(Stage primaryStage) {

		thestage = primaryStage;

		// LOADS 'LoginScreen.fxml' AND SETS THIS AS THE FIRST SCENE

		try {
			Parent root = FXMLLoader.load(getClass().getResource("shared_view/LoginScreen.fxml"));

			Scene scene = new Scene(root, 480, 300);
			primaryStage.setTitle("Cinema Login");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			LOGGER.warning("Couldn't load scene1");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// SETS UP THE LOGGER FROM 'CinemaLogger.java'
		try {
			CinemaLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problems with creating the log files");
		}

		launch(args);
	}
}
