package cinema;

import java.io.IOException;
import java.util.logging.Logger;

import cinema.shared_controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
	
	public void goToLoginPage(String pathToFXML, String pageTitle) {
		
		LoginController.userID = null;
		LoginController.loggedInUser = null;
		LoginController.usersName = null;
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource(pathToFXML));

			Scene scene = new Scene(root,800, 500);
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
		goToLoginPage("shared_view/LoginScreen.fxml", "CinemaLogin");
		
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
