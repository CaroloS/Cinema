package cinema;

import java.io.IOException;
import java.util.logging.Logger;

import cinema.employee_controllers.EmployeeHomeController;
import cinema.shared_controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Contains the <code>main</code> method to launch the application. Defines navigation methods and sets up the logger. 
 * @author carolinesmith, daianabassi
 *@version 1.0
 */
public class CinemaMain extends Application {

	// GETS THE GLOBAL LOGGER
	public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// DECLARES PUBLIC STAGE SO SCENES IN OTHER CLASSES CAN SET THE STAGE
	public static Stage thestage;

	/**
	 * Navigates to the next page. Loads the FXML passed to it and applies the page title specified. 
	 * @param pathToFXML String relative path to FXML document to load
	 * @param pageTitle String page title for the scene
	 * @exception catches <code>javafx.fxml.LoadException</code> if unable to load FXML
	 */
	public void goToNextPage(String pathToFXML, String pageTitle) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(pathToFXML));

			Scene scene = new Scene(root, 1000, 800);
			thestage.setTitle(pageTitle);
			thestage.setScene(scene);
			thestage.show();
		} catch (Exception e) {
			LOGGER.warning("Couldn't load that page");
			e.printStackTrace();
		}
	}
	
	/**
	 * Navigates to the log-in page. Loads the FXML passed to it and applies the page title specified. 
	 * @param pathToFXML String relative path to FXML document to load
	 * @param pageTitle String page title for the scene
	 * @exception catches <code>javafx.fxml.LoadException</code> if unable to load FXML
	 */
	public void goToLoginPage(String pathToFXML, String pageTitle) {
		
		//RESETS THE LOGGED IN USER AND CORRESPONDING ID AND USERNAME TO NULL
		LoginController.userID = null; 
		LoginController.loggedInUser = null;
		LoginController.usersName = null;
		EmployeeHomeController.editEmployee = false;
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource(pathToFXML)); 

			Scene scene = new Scene(root,1000, 800);  //SETS A NEW SCENE WITH THE LOADED FXML AND DEFINES THE WDITH/HEIGHT 
			thestage.setTitle(pageTitle);
			thestage.setScene(scene);   //SETS THE SCENE TO THE STAGE 
			thestage.show();
		} catch (Exception e) {
			LOGGER.warning("Couldn't load that page");
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Method called from <code>cinema.CinemaMain.main</code>. Loads the first scene (LoginScreen.fxml).
	 * @param primaryStage the primary stage of this JavaFX application
	 */
	@Override
	public void start(Stage primaryStage) {

		//SETS THE STATIC VARIABLE 'thestage' TO THE LOCAL 'primaryStage' VARIABLE
		thestage = primaryStage;
		
		// CALLS METHOD TO LOAD LOGIN SCREEN
		goToLoginPage("shared_view/LoginScreen.fxml", "CinemaLogin");
		
	}

	/**
	 * The starting method for the application. Sets up the Logger from <code>cinema.CinemaLogger</code> and launches <code>cinema.CinemaMain.start</code>
	 * @param args the array of arguments passed on launching the program
	 * @exception catches IOException if an input or output exception occurred
	 */
	public static void main(String[] args) {

		// SETS UP THE LOGGER FROM 'CinemaLogger.java'
		try {
			CinemaLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problems with creating the log files");
		}
	
		//LAUNCHES THE JAVAFX APPLICATION
		launch(args);
	}
}
