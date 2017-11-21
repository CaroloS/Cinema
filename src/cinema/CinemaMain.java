package cinema;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class CinemaMain extends Application {
	
	//GET THE GLOBAL LOGGER 
	public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	//DECLARE PUBLIC STAGE SO SCENES IN OTHER CLASSES CAN SET THE STAGE 
	public static Stage thestage;
	public Scene scene1;
	
	/**
	 * Starting method for the program launched from 'main'
	 * Sets the stage to scene1 - Login Screen
	 * @param primaryStage 
	 * @exception 
	 */
	@Override
	public void start(Stage primaryStage) {
		
		thestage = primaryStage;

		//LOADS 'LoginScreen.fxml' AND SETS THIS AS THE FIRST SCENE
		try {
			Parent root = FXMLLoader.load(getClass().getResource("view/LoginScreen.fxml"));

			scene1 = new Scene(root, 480, 300);
			primaryStage.setTitle("Cinema Login");
			primaryStage.setScene(scene1);
			primaryStage.show();
		} catch (Exception e) {
			LOGGER.warning("Couldn't load scene1");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		//SETS UP THE LOGGER FROM 'CinemaLogger.java'
		try {
	         CinemaLogger.setup();
	     } catch (IOException e) {
	         e.printStackTrace();
	         throw new RuntimeException("Problems with creating the log files");
	     }
		
		launch(args);
	}
}
